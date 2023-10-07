package com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.model

import com.shadowsafe.secretsmanagerbackend.accessmanagement.model.GroupFolderAccessEntity
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("user_groups")
data class UserGroupsEntity(
    @Id
    val _id: ObjectId = ObjectId.get(),
    val name: String,
    var userAccessListId: String? = "",
    var folderAccessList: List<GroupFolderAccessEntity>? = emptyList(),
    val createdBy: String,
)
