package com.shadowsafe.secretsmanagerbackend.secret.dto

import com.shadowsafe.secretsmanagerbackend.shared.IDTO

data class SecretByUrlResponseDTO(
    var username: String,
) : IDTO
