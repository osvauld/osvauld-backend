package com.shadowsafe.secretsmanagerbackend.secret.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class SecretsEntity(
    @Id
    val _id: ObjectId = ObjectId.get(),
    val username: String,
    val password: String,
    val description: String,
    val tags: List<String>,
    val searchKey: String
)
