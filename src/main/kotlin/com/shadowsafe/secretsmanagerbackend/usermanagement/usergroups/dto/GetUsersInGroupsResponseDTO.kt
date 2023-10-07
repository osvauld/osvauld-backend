package com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.dto

import com.shadowsafe.secretsmanagerbackend.shared.IDTO

data class GetUsersInGroupsResponseDTO(
    val users: List<UserInGroupDTO>,
) : IDTO
