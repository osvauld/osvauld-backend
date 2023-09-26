package com.shadowsafe.secretsmanagerbackend.usermanagement.users.dto

data class UsersResponseDTO(
    val _id: String,
    val username: String,
    val name: String,
    val isActive: Boolean?,
    val isAdmin: Boolean,
    val tags: List<String>? = emptyList(),
)
