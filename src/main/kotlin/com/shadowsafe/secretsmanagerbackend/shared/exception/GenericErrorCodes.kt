package com.shadowsafe.secretsmanagerbackend.shared.exception

enum class GenericErrorCodes(val defaultMessage: String, val messageProperty: String) {
    SECRET_NOT_FOUND("Secret details not found", ""),
}
