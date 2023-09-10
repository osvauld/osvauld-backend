package com.shadowsafe.secretsmanagerbackend.security.db

data class Role(
    val roleId: String,
    val name: String,
    val description: String,
)
