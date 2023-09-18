package com.shadowsafe.secretsmanagerbackend.folders.dto

import com.shadowsafe.secretsmanagerbackend.shared.IDTO

data class FolderStructureDTO(
    var id: String,
    var label: String,
    var parentId: String?,
    var children: List<FolderStructureDTO>?,
) : IDTO
