package com.shadowsafe.secretsmanagerbackend.folders.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document("folder_tree")
data class FolderTree(
    @Id
    val _id: ObjectId = ObjectId.get(),
    var organisationId: ObjectId = ObjectId.get(),
    var rootFolderId: String,
    var createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
