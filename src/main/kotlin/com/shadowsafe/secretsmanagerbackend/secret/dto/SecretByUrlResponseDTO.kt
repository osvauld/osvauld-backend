package com.shadowsafe.secretsmanagerbackend.secret.dto

import com.shadowsafe.secretsmanagerbackend.shared.IDTO

data class SecretByUrlResponseDTO(
    var secretId: String,
    var username: String,
) : IDTO
