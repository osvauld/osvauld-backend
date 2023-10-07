package com.shadowsafe.secretsmanagerbackend.accessmanagement.service

import com.shadowsafe.secretsmanagerbackend.accessmanagement.dto.AddAccessForFoldersRequestDTO
import org.springframework.stereotype.Service

@Service
interface AccessManagementService {

    fun saveAccessForFolders(request: AddAccessForFoldersRequestDTO, userId: String)
}
