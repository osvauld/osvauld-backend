package com.shadowsafe.secretsmanagerbackend.usermanagement.users.model

import com.shadowsafe.secretsmanagerbackend.accessmanagement.model.UserFolderAccessEntity
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document

@Document("user_folder_access")
data class UserFolderAccessManagementEntity(
    val _id: ObjectId = ObjectId.get(),
    val userId: String,
    var folderAccess: List<UserFolderAccessEntity>,
)
