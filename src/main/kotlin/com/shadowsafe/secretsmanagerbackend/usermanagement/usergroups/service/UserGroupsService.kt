package com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.service

import com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.dto.AddUserGroupRequestDTO
import com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.dto.AddUserToUserGroupRequestDTO
import com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.dto.GetGroupStructureDTO
import org.springframework.stereotype.Service

@Service
interface UserGroupsService {
    fun addUserGroup(request: AddUserGroupRequestDTO, userId: String)
    fun addUserToUserGroup(request: AddUserToUserGroupRequestDTO, userId: String)

    fun getAllGroupFolderStructure(): GetGroupStructureDTO
}