package com.shadowsafe.secretsmanagerbackend.folders.service

import com.shadowsafe.secretsmanagerbackend.folders.dto.FolderDTO
import com.shadowsafe.secretsmanagerbackend.folders.dto.FolderRequestDTO
import com.shadowsafe.secretsmanagerbackend.folders.dto.FolderStructureDTO
import com.shadowsafe.secretsmanagerbackend.folders.dto.FolderTreeDTO
import org.springframework.stereotype.Service

@Service
interface FoldersService {
    fun getFolder(id: String): FolderDTO
    fun saveFolders(request: FolderRequestDTO): FolderDTO
    fun createNewFolderStructureForOrganisation(): FolderTreeDTO
    fun getFolderStructureForOrganisation(organisationId: String): FolderStructureDTO
}
