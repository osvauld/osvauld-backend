package com.shadowsafe.secretsmanagerbackend.shared.exception

enum class GenericErrorCodes(val defaultMessage: String, val messageProperty: String) {
    USER_NOT_FOUND("User not found", ""),
    ADMIN_ALREADY_CREATED("Admin already created", ""),
    SECRET_NOT_FOUND("Secret details not found", ""),
    FOLDER_NOT_FOUND("Folder not found", ""),
    GROUP_NOT_FOUND("Group not found", ""),
    USER_ALREADY_EXISTS("User already exits", ""),
    AUTHENTICATION_EXCEPTION("Authentication Exception", ""),
}
