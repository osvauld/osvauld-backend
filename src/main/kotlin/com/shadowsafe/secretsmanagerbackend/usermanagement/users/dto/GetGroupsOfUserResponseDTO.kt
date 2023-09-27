package com.shadowsafe.secretsmanagerbackend.usermanagement.users.dto

import com.shadowsafe.secretsmanagerbackend.shared.IDTO

data class GetGroupsOfUserResponseDTO(
    val name: String,
    val username: String,
    val createdBy: String? = "",
    val groups: List<GroupResponseDTO>? = emptyList(),
) : IDTO
