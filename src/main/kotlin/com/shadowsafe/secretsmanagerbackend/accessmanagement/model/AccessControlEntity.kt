package com.shadowsafe.secretsmanagerbackend.accessmanagement.model

import org.bson.types.ObjectId

data class AccessControlEntity(
    val _id: ObjectId = ObjectId.get(),
    val folderId: String,
    val groupIds: List<String>,
)
