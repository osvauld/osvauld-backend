package com.shadowsafe.secretsmanagerbackend.folders.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document("folders")
data class FolderEntity(
    @Id
    val _id: ObjectId = ObjectId.get(),
    var name: String,
    var parents: ArrayList<String>,
    var children: ArrayList<String>,
    var secrets: ArrayList<String>,
    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime
)
