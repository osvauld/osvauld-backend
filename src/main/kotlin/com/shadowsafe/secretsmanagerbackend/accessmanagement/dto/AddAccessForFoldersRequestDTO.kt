package com.shadowsafe.secretsmanagerbackend.accessmanagement.dto

import com.shadowsafe.secretsmanagerbackend.accessmanagement.model.SecretGroupAccessEntity
import com.shadowsafe.secretsmanagerbackend.accessmanagement.model.SecretUserAccessEntity

data class AddAccessForFoldersRequestDTO(
    val folderId: String,
    val userAccess: List<SecretUserAccessEntity>? = emptyList(),
    val groupAccess: List<FolderGroupAccessDTO>? = emptyList(),
)
