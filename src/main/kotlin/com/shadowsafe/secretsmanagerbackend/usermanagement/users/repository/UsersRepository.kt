package com.shadowsafe.secretsmanagerbackend.usermanagement.users.repository

import com.shadowsafe.secretsmanagerbackend.usermanagement.users.model.UsersEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UsersRepository : MongoRepository<UsersEntity, String> {

    fun findUserByEmail(email: String): Optional<UsersEntity>

    @Query("{isAdmin: true}")
    fun findAdminUsers(): List<UsersEntity>
}
