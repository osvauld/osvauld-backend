package com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.service.impl

import com.shadowsafe.secretsmanagerbackend.shared.exception.GenericErrorCodes
import com.shadowsafe.secretsmanagerbackend.shared.exception.GenericException
import com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.dto.AddUserGroupRequestDTO
import com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.dto.AddUserToUserGroupRequestDTO
import com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.dto.GetGroupStructureDTO
import com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.dto.GetUsersInGroupsResponseDTO
import com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.model.UserGroupsEntity
import com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.repository.UserGroupsRepository
import com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.service.UserGroupsService
import com.shadowsafe.secretsmanagerbackend.usermanagement.users.dto.UsersResponseDTO
import com.shadowsafe.secretsmanagerbackend.usermanagement.users.repository.UsersRepository
import org.bson.types.ObjectId
import org.springframework.stereotype.Service

@Service
class UserGroupsServiceImpl(
    private val userGroupsRepository: UserGroupsRepository,
    private val usersRepository: UsersRepository,
) : UserGroupsService {
    override fun addUserGroup(request: AddUserGroupRequestDTO, userId: String) {
        val parentIds = mutableListOf<String>()
        if (!request.parentId.isNullOrEmpty()) {
            val groupParentOptional = userGroupsRepository.findById(request.parentId)
            if (groupParentOptional.isPresent) {
                val groupParent = groupParentOptional.get()
                parentIds.addAll(groupParent.parentIds)
                parentIds.add(request.parentId)
            } else {
                throw GenericException(GenericErrorCodes.GROUP_NOT_FOUND)
            }
            val groupParent = groupParentOptional.get()
            val userGroup = userGroupsRepository.save(
                UserGroupsEntity(
                    name = request.name,
                    createdBy = userId,
                    userIds = groupParent.childrenIds,
                    parentIds = parentIds,
                ),
            )
            groupParent.childrenIds = groupParent.childrenIds + userGroup._id.toHexString()
            userGroupsRepository.save(groupParent)
        } else {
            userGroupsRepository.save(
                UserGroupsEntity(
                    name = request.name,
                    createdBy = userId,
                    userIds = emptyList(),
                    childrenIds = emptyList(),
                ),
            )
        }
    }

    override fun addUserToUserGroup(request: AddUserToUserGroupRequestDTO, userId: String) {
        val optionalGroup = userGroupsRepository.findById(request.groupId)

        if (optionalGroup.isPresent) {
            val group = optionalGroup.get()

            if (group.userIds.containsAll(request.userIds)) throw GenericException(GenericErrorCodes.USER_ALREADY_EXISTS)
            group.userIds = group.userIds + request.userIds
            userGroupsRepository.save(group)
        } else {
            throw GenericException(GenericErrorCodes.GROUP_NOT_FOUND)
        }
    }

    override fun removeUserFromGroup(userId: String, groupId: String) {
        val optionalGroup = userGroupsRepository.findById(groupId)
        if (optionalGroup.isPresent) {
            var group = optionalGroup.get()
            group.userIds = group.userIds.minus(userId)
            userGroupsRepository.save(group)
        } else {
            throw GenericException(GenericErrorCodes.GROUP_NOT_FOUND)
        }
    }

    override fun getAllGroupFolderStructure(): GetGroupStructureDTO {
        var response = GetGroupStructureDTO()
        val allGroups = userGroupsRepository.findAll()
        if (allGroups.isNotEmpty()) {
            val rootGroup = allGroups.find { item -> item.parentIds.isNullOrEmpty() }
            response = getGroupFolderStructure(rootGroup!!._id.toHexString(), allGroups)
        }
        return response
    }

    override fun getUsersInGroup(groupId: String): GetUsersInGroupsResponseDTO {
        val optionalGroup = userGroupsRepository.findById(groupId)
        if (optionalGroup.isPresent) {
            val users = usersRepository.findAllById(optionalGroup.get().userIds)
            return GetUsersInGroupsResponseDTO(
                users.map { item ->
                    UsersResponseDTO(
                        _id = item._id.toHexString(),
                        username = item.email,
                        isActive = true,
                        isAdmin = item.isAdmin,
                        name = item.name
                    )
                }
            )
        } else {
            throw GenericException(GenericErrorCodes.GROUP_NOT_FOUND)
        }
    }

    fun getGroupFolderStructure(groupId: String, groupList: List<UserGroupsEntity>): GetGroupStructureDTO {
        val group = groupList.find { item -> item._id == ObjectId(groupId) }

        group!!.let {
            val groupStructure = GetGroupStructureDTO(
                id = it._id.toHexString(),
                label = it.name,
                parentId = it.parentIds.lastOrNull(),
                children = arrayListOf(),
            )
            it.childrenIds.forEach { item ->
                groupStructure.children = groupStructure.children?.plus(getGroupFolderStructure(item, groupList))
            }
            return groupStructure
        }
    }
}
