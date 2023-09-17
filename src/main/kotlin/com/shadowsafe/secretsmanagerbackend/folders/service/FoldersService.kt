package com.shadowsafe.secretsmanagerbackend.folders.service

import com.shadowsafe.secretsmanagerbackend.folders.dto.FolderDTO
import com.shadowsafe.secretsmanagerbackend.folders.dto.FolderRequestDTO
import org.springframework.stereotype.Service

@Service
interface FoldersService {
    fun getFolder(id: String): FolderDTO
    fun saveFolders(request: FolderRequestDTO): FolderDTO
}
