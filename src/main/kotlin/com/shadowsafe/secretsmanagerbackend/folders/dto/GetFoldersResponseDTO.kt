package com.shadowsafe.secretsmanagerbackend.folders.dto

import com.shadowsafe.secretsmanagerbackend.shared.IDTO

data class GetFoldersResponseDTO(
    val userId: String,
    var folders: List<FolderStructureDTO>? = emptyList(),
) : IDTO
