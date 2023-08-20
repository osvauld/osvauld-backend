package com.shadowsafe.secretsmanagerbackend.secret.dto

import com.shadowsafe.secretsmanagerbackend.shared.IDTO

data class SecretsResponseDTO(
    val id: String,
    val username: String,
    val password: String,
    val description: String,
    val tags: List<String>
): IDTO
