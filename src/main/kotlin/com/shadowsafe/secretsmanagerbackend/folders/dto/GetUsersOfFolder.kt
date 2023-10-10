package com.shadowsafe.secretsmanagerbackend.folders.dto

import com.shadowsafe.secretsmanagerbackend.shared.IDTO

data class GetUsersOfFolder(
    val folderId: String,
    val users: List<FolderUserDTO> = mutableListOf(),
) : IDTO
