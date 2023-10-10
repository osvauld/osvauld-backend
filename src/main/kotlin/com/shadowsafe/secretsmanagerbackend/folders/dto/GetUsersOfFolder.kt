package com.shadowsafe.secretsmanagerbackend.folders.dto

import com.shadowsafe.secretsmanagerbackend.shared.IDTO

data class GetUsersOfFolder(
    val folderId: String,
    var users: List<FolderUserDTO> = mutableListOf(),
) : IDTO
