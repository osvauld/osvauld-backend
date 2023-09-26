package com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.dto

import com.shadowsafe.secretsmanagerbackend.shared.IDTO
import com.shadowsafe.secretsmanagerbackend.usermanagement.users.dto.UsersResponseDTO
import com.shadowsafe.secretsmanagerbackend.usermanagement.users.model.UsersEntity

data class GetUsersInGroupsResponseDTO(
    val users: List<UsersResponseDTO>,
) : IDTO
