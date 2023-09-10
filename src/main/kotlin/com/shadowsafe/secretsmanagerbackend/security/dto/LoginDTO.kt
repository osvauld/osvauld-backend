package com.shadowsafe.secretsmanagerbackend.security.dto

data class LoginDTO(
    val userName: String,
    val password: String,
)
