package com.shadowsafe.secretsmanagerbackend.usermanagement.dto

import com.shadowsafe.secretsmanagerbackend.shared.IDTO

data class CheckIfAdminResponseDTO(
    val token: String,
    val isPresent: Boolean
): IDTO
