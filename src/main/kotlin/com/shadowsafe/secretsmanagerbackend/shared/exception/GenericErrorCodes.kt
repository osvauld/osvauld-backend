package com.shadowsafe.secretsmanagerbackend.shared.exception

enum class GenericErrorCodes(val defaultMessage: String, val messageProperty: String) {
    ADMIN_ALREADY_CREATED("Admin already created", ""),
    SECRET_NOT_FOUND("Secret details not found", ""),
    FOLDER_NOT_FOUND("Folder not found", "")
}
