package com.shadowsafe.secretsmanagerbackend.folders.dto

import com.shadowsafe.secretsmanagerbackend.shared.IDTO

data class FolderRequestDTO(
    val id: String?,
    val label: String?,
    val parent: String?,
    val children: List<String>?,
    val secrets: List<String>?
) : IDTO
