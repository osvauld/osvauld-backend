package com.shadowsafe.secretsmanagerbackend.secret.dto

import com.shadowsafe.secretsmanagerbackend.shared.IDTO
import java.time.LocalDateTime

data class SecretsResponseDTO(
    val id: String,
    val name: String?,
    var credentials: Map<String, String>?,
    var parent: String,
    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime
) : IDTO
