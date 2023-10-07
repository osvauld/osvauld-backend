package com.shadowsafe.secretsmanagerbackend.accessmanagement.model

import com.shadowsafe.secretsmanagerbackend.accessmanagement.enums.SecretGroupAccessType

data class SecretGroupAccessEntity(
    val groupId: String,
    val name: String,
    val type: SecretGroupAccessType,
)
