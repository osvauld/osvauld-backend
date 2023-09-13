package com.shadowsafe.secretsmanagerbackend.secret.dto

data class GetFoldersResponseDTO(
    val id: String,
    val label: String,
    val children: List<GetFolderDTO>
)

data class GetFolderDTO(
    val id: String,
    val label: String,
    val parentId: String,
    val children: List<GetFolderDTO>
)

