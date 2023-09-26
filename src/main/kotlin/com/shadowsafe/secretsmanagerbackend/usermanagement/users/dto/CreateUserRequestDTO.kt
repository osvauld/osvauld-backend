package com.shadowsafe.secretsmanagerbackend.usermanagement.users.dto

data class CreateUserRequestDTO(
    val username: String,
    val password: String,
    val isAdmin: Boolean,
    val name: String?,
)
