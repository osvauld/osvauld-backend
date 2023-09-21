package com.shadowsafe.secretsmanagerbackend.secret.model

import com.shadowsafe.secretsmanagerbackend.shared.IDTO

data class CredentialsEntity (

    val fieldKey: String,
    val fieldValue: String,
    val sensitive: Boolean
) : IDTO