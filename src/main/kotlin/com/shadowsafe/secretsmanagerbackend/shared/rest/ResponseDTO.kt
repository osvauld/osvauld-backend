package com.shadowsafe.secretsmanagerbackend.shared.rest

import com.shadowsafe.secretsmanagerbackend.shared.IDTO

/**
 * High level contract for all REST Controllers - Agreed Upon.
 */
interface ResponseDTO : IDTO {
    val statusCode: Int
    val status: String
    val message: String?
    val data: IDTO?
}
