package com.shadowsafe.secretsmanagerbackend.folders.service

import com.shadowsafe.secretsmanagerbackend.folders.dto.*
import org.springframework.stereotype.Service

@Service
interface FoldersService {
    fun getFolder(id: String): FolderResponseDTO
    fun saveFolders(request: FolderRequestDTO): FolderStructureDTO
    fun createNewFolderStructureForOrganisation(): FolderTreeDTO
    fun getRootFolderAndStructure(): FolderStructureDTO
}
