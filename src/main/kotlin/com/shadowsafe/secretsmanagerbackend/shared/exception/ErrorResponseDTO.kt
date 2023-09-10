package com.shadowsafe.secretsmanagerbackend.shared.exception

import com.shadowsafe.secretsmanagerbackend.shared.IDTO
import com.shadowsafe.secretsmanagerbackend.shared.rest.ResponseDTO

data class ErrorResponseDTO(
    override val statusCode: Int,
    override val status: String,
    override val message: String?,
    override val data: IDTO?,
) : ResponseDTO
