package com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.dto

import com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.model.UserGroupAccessEntity

data class AddUserGroupRequestDTO(
    val name: String,
    var userAccess: List<UserGroupAccessEntity>? = emptyList(),
)
