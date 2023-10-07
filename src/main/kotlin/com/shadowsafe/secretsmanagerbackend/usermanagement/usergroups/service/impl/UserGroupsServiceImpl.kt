package com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.service.impl

import com.shadowsafe.secretsmanagerbackend.accessmanagement.model.GroupFolderAccessEntity
import com.shadowsafe.secretsmanagerbackend.shared.exception.GenericErrorCodes
import com.shadowsafe.secretsmanagerbackend.shared.exception.GenericException
import com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.dto.*
import com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.enums.GroupAccessType
import com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.model.GroupAccessEntity
import com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.model.UserGroupAccessEntity
import com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.model.UserGroupsEntity
import com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.repository.GroupAccessRepository
import com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.repository.UserGroupsRepository
import com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.service.UserGroupsService
import com.shadowsafe.secretsmanagerbackend.usermanagement.users.repository.UsersRepository
import com.shadowsafe.secretsmanagerbackend.usermanagement.users.service.UsersService
import org.bson.types.ObjectId
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserGroupsServiceImpl(
    private val userGroupsRepository: UserGroupsRepository,
    private val usersRepository: UsersRepository,
    private val usersService: UsersService,
    private val groupAccessRepository: GroupAccessRepository,
) : UserGroupsService {
    override fun addUserGroup(request: AddUserGroupRequestDTO, userId: String) {
        val userGroupsEntity = UserGroupsEntity(_id = ObjectId.get(), name = request.name, createdBy = userId)
        request.userAccess?.forEach { item ->
            if (!usersService.checkIfUserPresent(item.userId)) throw GenericException(GenericErrorCodes.USER_NOT_FOUND)
        }
        request.userAccess = request.userAccess?.plus(UserGroupAccessEntity(userId, GroupAccessType.OWNER))
        var groupAccess = groupAccessRepository.save(
            GroupAccessEntity(
                groupId = userGroupsEntity._id.toHexString(),
                name = request.name,
                accessList = request.userAccess ?: emptyList(),
            ),
        )
        userGroupsEntity.userAccessListId = groupAccess._id.toHexString()
        userGroupsRepository.save(userGroupsEntity)
    }

    override fun addUserToUserGroup(request: AddUserToUserGroupRequestDTO, userId: String) {
        val optionalGroup = userGroupsRepository.findById(request.groupId)

        if (optionalGroup.isPresent) {
            val group = optionalGroup.get()
            val groupAccess = groupAccessRepository.findById(group.userAccessListId!!).get()
            request.userAccessList.forEach { item ->
                if (groupAccess.accessList.any { access -> access.userId == item.userId }) throw GenericException(GenericErrorCodes.USER_ALREADY_EXISTS)
                groupAccess.accessList = groupAccess.accessList.plus(UserGroupAccessEntity(item.userId, item.accessType))
            }
            groupAccessRepository.save(groupAccess)
        } else {
            throw GenericException(GenericErrorCodes.GROUP_NOT_FOUND)
        }
    }

    override fun removeUserFromGroup(userId: String, groupId: String) {
        val optionalGroup = userGroupsRepository.findById(groupId)

        if (optionalGroup.isPresent) {
            val group = optionalGroup.get()
            val groupAccess = groupAccessRepository.findById(group.userAccessListId!!).get()
            groupAccess.accessList = groupAccess.accessList.filter { item -> item.userId != userId }
            groupAccessRepository.save(groupAccess)
        } else {
            throw GenericException(GenericErrorCodes.GROUP_NOT_FOUND)
        }
    }

    override fun getUsersInGroup(groupId: String): GetUsersInGroupsResponseDTO {
        val optionalGroup = userGroupsRepository.findById(groupId)
        if (optionalGroup.isPresent) {
            val group = optionalGroup.get()
            val groupAccess = groupAccessRepository.findById(group.userAccessListId!!).get()
            return GetUsersInGroupsResponseDTO(
                users = groupAccess.accessList.map { item ->
                    UserInGroupDTO(
                        userId = item.userId,
                        name = usersService.getUserById(item.userId)!!.name,
                        accessType = item.accessType.name,
                    )
                },
            )
        } else {
            throw GenericException(GenericErrorCodes.GROUP_NOT_FOUND)
        }
    }

    override fun addFolderAccess(groupId: String, access: GroupFolderAccessEntity) {
        val group = userGroupsRepository.findByIdOrNull(groupId) ?: throw GenericException(GenericErrorCodes.GROUP_NOT_FOUND)
        group.folderAccessList = if (group.folderAccessList.isNullOrEmpty()) listOf(access) else group.folderAccessList!!.plus(access)
        userGroupsRepository.save(group)
    }
}
