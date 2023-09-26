package com.shadowsafe.secretsmanagerbackend.usermanagement.users.dto

import com.shadowsafe.secretsmanagerbackend.shared.IDTO

data class GetUsersResponseDTO(
    val users: List<UsersResponseDTO>,
) : IDTO
