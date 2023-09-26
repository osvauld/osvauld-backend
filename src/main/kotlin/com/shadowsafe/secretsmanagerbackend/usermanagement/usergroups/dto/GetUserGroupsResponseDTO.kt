package com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.dto

data class GetUserGroupsResponseDTO(
    val id: String,
    val label: String,
    val children: String,
)
