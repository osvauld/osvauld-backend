package com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.service.impl

import com.shadowsafe.secretsmanagerbackend.shared.exception.GenericErrorCodes
import com.shadowsafe.secretsmanagerbackend.shared.exception.GenericException
import com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.dto.AddUserGroupRequestDTO
import com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.dto.AddUserToUserGroupRequestDTO
import com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.model.UserGroupsEntity
import com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.repository.UserGroupsRepository
import com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.service.UserGroupsService

class UserGroupsServiceImpl(
    private val userGroupsRepository: UserGroupsRepository,
) : UserGroupsService {
    override fun addUserGroup(request: AddUserGroupRequestDTO, userId: String) {
        userGroupsRepository.save(
            UserGroupsEntity(
                name = request.name,
                createdBy = "",
                userIds = emptyList(),
            ),
        )
    }

    override fun addUserToUserGroup(request: AddUserToUserGroupRequestDTO, userId: String) {
        val optionalGroup = userGroupsRepository.findById(request.groupId)
        if (optionalGroup.isPresent) {
            val group = optionalGroup.get()
            group.userIds = group.userIds + request.userIds
            userGroupsRepository.save(group)
        } else {
            throw GenericException(GenericErrorCodes.GROUP_NOT_FOUND)
        }
    }


}
