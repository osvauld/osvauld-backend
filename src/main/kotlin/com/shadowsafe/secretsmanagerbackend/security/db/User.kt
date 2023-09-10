package com.shadowsafe.secretsmanagerbackend.security.db

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class User(
    @Id
    var _id: ObjectId = ObjectId.get(),
    var userId: String,
    var userName: String,
    var password: String,
    var name: String,
    var role: Role,
)
