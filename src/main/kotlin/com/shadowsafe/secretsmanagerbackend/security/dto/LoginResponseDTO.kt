package com.shadowsafe.secretsmanagerbackend.security.dto

import com.shadowsafe.secretsmanagerbackend.shared.IDTO

data class LoginResponseDTO(
    var userId: String,
    var name: String,
    var email: String,
    var role: String,
    val token: String
) : IDTO
