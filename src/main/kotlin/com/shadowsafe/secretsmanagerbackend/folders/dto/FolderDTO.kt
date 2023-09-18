package com.shadowsafe.secretsmanagerbackend.folders.dto

import com.shadowsafe.secretsmanagerbackend.shared.IDTO
import java.time.LocalDateTime

data class FolderDTO(
    var id: String?,
    var label: String?,
    var parents: List<String>?,
    var children: List<String>?,
    var secrets: List<String>?,
    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime
) : IDTO
