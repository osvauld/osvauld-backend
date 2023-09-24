package com.shadowsafe.secretsmanagerbackend.secret.dto

import com.shadowsafe.secretsmanagerbackend.secret.model.CredentialsEntity
import com.shadowsafe.secretsmanagerbackend.shared.IDTO

data class SaveSecretsRequestDTO(

    val name: String?,
    var credentials: List<CredentialsEntity>?,
    var parent: String,
    var description: String,
) : IDTO
