package com.shadowsafe.secretsmanagerbackend.secret.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("secrets")
data class SecretsEntity(
    @Id
    val _id: ObjectId = ObjectId.get(),
    var name: String,
    var description: String,
    var credentials: Map<String, String>,
    var parents: List<String>
)
