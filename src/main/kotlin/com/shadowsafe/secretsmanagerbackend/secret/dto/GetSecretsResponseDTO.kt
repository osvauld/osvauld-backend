package com.shadowsafe.secretsmanagerbackend.secret.dto

import com.shadowsafe.secretsmanagerbackend.shared.IDTO

data class GetSecretsResponseDTO(
    val secrets: List<SecretsResponseDTO>,
    val pageNo: Int,
    val pageSize: Int,
    val totalNoPages: Int,
) : IDTO
