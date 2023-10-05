package com.shadowsafe.secretsmanagerbackend.usermanagement.users.dto

import com.shadowsafe.secretsmanagerbackend.shared.IDTO

data class GetGroupsOfUserResponseDTO(
    val name: String,
    val username: String,
    val createdBy: String? = "",
    var groups: List<GroupResponseDTO>? = emptyList(),
) : IDTO
