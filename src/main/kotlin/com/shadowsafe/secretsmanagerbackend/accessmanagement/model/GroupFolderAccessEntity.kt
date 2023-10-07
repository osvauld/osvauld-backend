package com.shadowsafe.secretsmanagerbackend.accessmanagement.model

import com.shadowsafe.secretsmanagerbackend.accessmanagement.enums.SecretGroupAccessType

data class GroupFolderAccessEntity(
    val folderId: String,
    val type: SecretGroupAccessType,
)
