package com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.model

import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document

@Document("group_access")
data class GroupAccessEntity(
    val _id: ObjectId = ObjectId.get(),
    val groupId: String,
    val name: String,
    var accessList: List<UserGroupAccessEntity>,
)
