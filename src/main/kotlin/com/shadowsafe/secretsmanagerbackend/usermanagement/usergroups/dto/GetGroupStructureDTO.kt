package com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.dto

import com.shadowsafe.secretsmanagerbackend.shared.IDTO

data class GetGroupStructureDTO(
    var id: String = "",
    var label: String = "",
    var parentId: String? = "",
    var children: List<GetGroupStructureDTO>? = null
): IDTO {
}