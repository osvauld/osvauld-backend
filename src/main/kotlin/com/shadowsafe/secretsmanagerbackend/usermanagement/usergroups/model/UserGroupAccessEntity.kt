package com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.model

import com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.enums.GroupAccessType

data class UserGroupAccessEntity(
    val userId: String,
    val accessType: GroupAccessType,
)
