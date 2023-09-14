package com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.dto

data class AddUserToUserGroupRequestDTO(
    val groupId: String,
    val userIds: List<String>
)
