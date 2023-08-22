package com.shadowsafe.secretsmanagerbackend.secret.dto

data class SaveSecretsRequestDTO(
    val username: String,
    val password: String,
    val description: String,
    val tags: List<String>
)
