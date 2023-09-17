package com.shadowsafe.secretsmanagerbackend.usermanagement.users.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("users")
data class UsersEntity(
    @Id
    val _id: ObjectId = ObjectId.get(),
    val email: String,
    val name: String,
    val password: String,
    val role: List<String>,
    val isAdmin: Boolean,
)
