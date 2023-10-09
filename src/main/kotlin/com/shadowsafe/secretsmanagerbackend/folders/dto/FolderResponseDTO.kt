package com.shadowsafe.secretsmanagerbackend.folders.dto

import com.shadowsafe.secretsmanagerbackend.secret.dto.SecretsResponseDTO
import com.shadowsafe.secretsmanagerbackend.shared.IDTO
import java.time.LocalDateTime

data class FolderResponseDTO(
    var id: String?,
    var label: String?,
    var parents: List<String>?,
    var children: List<String>?,
    var secrets: List<SecretsResponseDTO>?,
    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime,
) : IDTO
