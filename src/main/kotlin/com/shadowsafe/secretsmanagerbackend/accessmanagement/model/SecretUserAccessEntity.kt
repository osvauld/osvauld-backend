package com.shadowsafe.secretsmanagerbackend.accessmanagement.model

import com.shadowsafe.secretsmanagerbackend.accessmanagement.enums.SecretUserAccessType

data class SecretUserAccessEntity(
    val userId: String,
    val type: SecretUserAccessType,
)
