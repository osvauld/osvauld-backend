package com.shadowsafe.secretsmanagerbackend.usermanagement.repository

import com.shadowsafe.secretsmanagerbackend.usermanagement.model.UsersEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UsersRepository: MongoRepository<UsersEntity, String> {
}