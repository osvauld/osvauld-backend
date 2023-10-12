package com.shadowsafe.secretsmanagerbackend.folders.dto

import com.shadowsafe.secretsmanagerbackend.usermanagement.users.dto.GroupResponseDTO
import java.util.*

data class FolderUserDTO(
    val userId: String,
    val name: String,
    val username: String,
    val accessType: String,
    val group: List<GroupResponseDTO>?,

) {

    override fun equals(other: Any?): Boolean {
        if (other!is FolderUserDTO) return false
        return other.userId == userId
    }

    override fun hashCode(): Int {
        return Objects.hash(userId)
    }
}
