package com.shadowsafe.secretsmanagerbackend.folders.model

import com.shadowsafe.secretsmanagerbackend.accessmanagement.model.SecretGroupAccessEntity
import com.shadowsafe.secretsmanagerbackend.accessmanagement.model.SecretUserAccessEntity
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document("folders")
data class FolderEntity(
    @Id
    val _id: ObjectId = ObjectId.get(),
    var label: String,
    var parents: List<String>,
    var children: List<String>,
    var secrets: List<String> = emptyList(),
    var groupAccessList: List<SecretGroupAccessEntity>? = emptyList(),
    var userAccessList: List<SecretUserAccessEntity>? = emptyList(),
    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime,
)
