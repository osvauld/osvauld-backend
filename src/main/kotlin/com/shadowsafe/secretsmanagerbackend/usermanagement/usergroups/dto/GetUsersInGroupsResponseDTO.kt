package com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.dto

import com.shadowsafe.secretsmanagerbackend.shared.IDTO
import com.shadowsafe.secretsmanagerbackend.usermanagement.users.model.UsersEntity

data class GetUsersInGroupsResponseDTO(
    val users: List<UsersEntity>,
) : IDTO
