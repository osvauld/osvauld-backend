package com.shadowsafe.secretsmanagerbackend.shared.exception

enum class GenericErrorCodes(val defaultMessage: String, val messageProperty: String) {
    ADMIN_ALREADY_CREATED("Admin already created", ""),
    SECRET_NOT_FOUND("Secret details not found", ""),
<<<<<<< Updated upstream
    FOLDER_NOT_FOUND("Folder not found", "")
=======
    GROUP_NOT_FOUND("Group not found", ""),
    AUTHENTICATION_EXCEPTION("Authentication Exception", ""),
>>>>>>> Stashed changes
}
