package com.shadowsafe.secretsmanagerbackend.accessmanagement.dto

import com.shadowsafe.secretsmanagerbackend.accessmanagement.enums.SecretGroupAccessType

data class FolderGroupAccessDTO(
    val groupId: String,
    val type: SecretGroupAccessType,
) {
}