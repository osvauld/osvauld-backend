package com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.service

import com.shadowsafe.secretsmanagerbackend.accessmanagement.model.GroupFolderAccessEntity
import com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.dto.AddUserGroupRequestDTO
import com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.dto.AddUserToUserGroupRequestDTO
import com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.dto.GetUsersInGroupsResponseDTO
import com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.model.UserGroupsEntity
import org.springframework.stereotype.Service

@Service
interface UserGroupsService {
    fun addUserGroup(request: AddUserGroupRequestDTO, userId: String)

    fun addUserToUserGroup(request: AddUserToUserGroupRequestDTO, userId: String)

    fun getUsersInGroup(groupId: String): GetUsersInGroupsResponseDTO

    fun removeUserFromGroup(userId: String, groupId: String)

    fun addFolderAccess(groupId: String, access: GroupFolderAccessEntity)

    fun checkIfUserPresentInGroups(userId: String, groupId: List<String>): Boolean

    fun getGroupById(id: String): UserGroupsEntity
}
