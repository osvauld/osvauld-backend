package com.shadowsafe.secretsmanagerbackend.folders.dto

import com.shadowsafe.secretsmanagerbackend.shared.IDTO

data class FolderDTO(
    val id: String?,
    val name: String?,
    val parents: List<String>?,
    val children: List<String>?,
    val secrets: List<String>?
) : IDTO
