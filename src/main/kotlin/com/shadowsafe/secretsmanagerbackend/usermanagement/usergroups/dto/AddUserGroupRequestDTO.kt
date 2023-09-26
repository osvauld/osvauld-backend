package com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.dto

data class AddUserGroupRequestDTO(
    val name: String,
    val parentId: String? = "",
)
