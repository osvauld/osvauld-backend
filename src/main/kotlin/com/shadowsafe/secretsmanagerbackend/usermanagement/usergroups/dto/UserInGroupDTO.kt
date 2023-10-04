package com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.dto

data class UserInGroupDTO (
    val userId: String,
    val name: String,
    val accessType: String
) {
}