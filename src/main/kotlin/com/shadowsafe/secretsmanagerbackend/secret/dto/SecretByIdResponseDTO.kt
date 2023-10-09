package com.shadowsafe.secretsmanagerbackend.secret.dto

import com.shadowsafe.secretsmanagerbackend.shared.IDTO

data class SecretByIdResponseDTO(
        var username : String,
        var password : String
) : IDTO
