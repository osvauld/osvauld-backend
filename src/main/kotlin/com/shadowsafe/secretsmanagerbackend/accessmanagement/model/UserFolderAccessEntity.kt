package com.shadowsafe.secretsmanagerbackend.accessmanagement.model

import com.shadowsafe.secretsmanagerbackend.accessmanagement.enums.SecretUserAccessType

data class UserFolderAccessEntity(
    val folderId: String,
    val type: SecretUserAccessType,
)
