package com.shadowsafe.secretsmanagerbackend.folders.dto

import com.shadowsafe.secretsmanagerbackend.shared.IDTO
import java.time.LocalDateTime

data class FolderTreeDTO(
    val id: String,
    var organisationId: String,
    var rootFolderId: String,
    var createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) : IDTO
