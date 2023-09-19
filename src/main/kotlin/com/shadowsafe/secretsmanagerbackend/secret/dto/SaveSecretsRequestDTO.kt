package com.shadowsafe.secretsmanagerbackend.secret.dto

import com.shadowsafe.secretsmanagerbackend.shared.IDTO

data class SaveSecretsRequestDTO(

    val id : String?,
    val name: String?,
    var credentials: Map<String, String>?,
    var parent: String,
) : IDTO
