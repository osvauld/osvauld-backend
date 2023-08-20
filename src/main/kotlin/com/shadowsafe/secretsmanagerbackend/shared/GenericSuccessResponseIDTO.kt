package com.shadowsafe.secretsmanagerbackend.shared

data class GenericSuccessResponseIDTO(
    val successCode: String,
    val message: String,
) : IDTO
