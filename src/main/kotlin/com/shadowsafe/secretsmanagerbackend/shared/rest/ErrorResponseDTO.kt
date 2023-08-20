package com.shadowsafe.secretsmanagerbackend.shared.rest

import com.shadowsafe.secretsmanagerbackend.shared.IDTO

data class ErrorResponseDTO(
    override val statusCode: Int,
    override val status: String,
    override val message: String?,
    override val data: IDTO?,
) : ResponseDTO
