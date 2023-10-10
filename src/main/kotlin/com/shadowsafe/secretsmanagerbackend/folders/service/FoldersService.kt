package com.shadowsafe.secretsmanagerbackend.folders.service

import com.shadowsafe.secretsmanagerbackend.folders.dto.*
import org.springframework.stereotype.Service

@Service
interface FoldersService {
    fun getFolder(id: String, userId: String): FolderResponseDTO
    fun saveFolders(request: FolderRequestDTO, userId: String)
    fun createNewFolderStructureForOrganisation(): FolderTreeDTO
    fun getFolderOfUser(userId: String): GetFoldersResponseDTO
    fun getUsersInFolder(folderId: String, userId: String): GetUsersOfFolder
}
