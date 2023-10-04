package com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.dto

import com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.model.UserGroupAccessEntity

data class AddUserToUserGroupRequestDTO(
    val groupId: String,
    val userAccessList: List<UserGroupAccessEntity>,
)
