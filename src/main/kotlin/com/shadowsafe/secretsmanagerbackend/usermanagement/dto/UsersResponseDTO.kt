package com.shadowsafe.secretsmanagerbackend.usermanagement.dto

data class UsersResponseDTO(
    val email: String,
    val name: String,
    val isActive: Boolean,
    val isAdmin: Boolean,
    val tags: List<String>? = emptyList()
)