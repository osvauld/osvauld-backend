package com.shadowsafe.secretsmanagerbackend.folders.dto

import com.shadowsafe.secretsmanagerbackend.usermanagement.users.dto.GroupResponseDTO

data class FolderUserDTO(
    val userId: String,
    val accessType: String,
    val group: List<GroupResponseDTO>?,
)
