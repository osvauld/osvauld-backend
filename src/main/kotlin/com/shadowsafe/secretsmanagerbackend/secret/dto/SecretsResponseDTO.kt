package com.shadowsafe.secretsmanagerbackend.secret.dto

import com.shadowsafe.secretsmanagerbackend.secret.model.CredentialsEntity
import com.shadowsafe.secretsmanagerbackend.shared.IDTO
import java.time.LocalDateTime

data class SecretsResponseDTO(
    val name: String?,
    var credentials: List<CredentialsEntity>?,
    var parent: String,
    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime
) : IDTO
