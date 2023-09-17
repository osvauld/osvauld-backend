package com.shadowsafe.secretsmanagerbackend.secret.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("secret_folders")
data class SecretFolderEntity(
    @Id
    val _id: ObjectId = ObjectId.get(),
    val name: String,
    val parents: List<String>,
    val children: List<String>,
    val secrets: List<String>
)
