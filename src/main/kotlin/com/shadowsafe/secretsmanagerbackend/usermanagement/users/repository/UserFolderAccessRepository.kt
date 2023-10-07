package com.shadowsafe.secretsmanagerbackend.usermanagement.users.repository

import com.shadowsafe.secretsmanagerbackend.usermanagement.users.model.UserFolderAccessManagementEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserFolderAccessRepository : MongoRepository<UserFolderAccessManagementEntity, String> {
    fun findByUserId(userId: String): UserFolderAccessManagementEntity?
}
