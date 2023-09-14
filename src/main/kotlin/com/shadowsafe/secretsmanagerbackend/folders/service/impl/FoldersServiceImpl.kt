package com.shadowsafe.secretsmanagerbackend.folders.service.impl

import com.shadowsafe.secretsmanagerbackend.folders.dto.FolderDTO
import com.shadowsafe.secretsmanagerbackend.folders.model.FolderEntity
import com.shadowsafe.secretsmanagerbackend.folders.repo.FoldersRepository
import com.shadowsafe.secretsmanagerbackend.folders.service.FoldersService
import com.shadowsafe.secretsmanagerbackend.shared.exception.GenericErrorCodes
import com.shadowsafe.secretsmanagerbackend.shared.exception.GenericException
import org.springframework.stereotype.Service

@Service
class FoldersServiceImpl(
    private val foldersRepository: FoldersRepository,
) : FoldersService {

    override fun getFolder(id: String): FolderDTO {

        val folder = foldersRepository.findById(id)
        if (folder.isEmpty) {
            throw GenericException(GenericErrorCodes.FOLDER_NOT_FOUND)
        } else {
            val folderEntity = folder.get()
            return FolderDTO(
                folderEntity._id.toHexString(),
                folderEntity.name,
                folderEntity.parents,
                folderEntity.children,
                folderEntity.secrets,
            )
        }
    }

    override fun saveFolders(request: FolderDTO): FolderDTO {
        val folderEntity = foldersRepository.save(
            FolderEntity(
                name = request.name,
                parents = request.parents,
                children = request.children,
                secrets = request.secrets
            )
        )
        return FolderDTO(
            folderEntity._id.toHexString(),
            folderEntity.name,
            folderEntity.parents,
            folderEntity.children,
            folderEntity.secrets,
        )
    }
}
