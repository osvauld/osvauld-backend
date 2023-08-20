package com.shadowsafe.secretsmanagerbackend.secret.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("secrets")
data class SecretsEntity(
    @Id
    val _id: ObjectId = ObjectId.get(),
    var username: String,
    var password: String,
    var description: String,
    var tags: List<String>,
    var searchKey: String
)
