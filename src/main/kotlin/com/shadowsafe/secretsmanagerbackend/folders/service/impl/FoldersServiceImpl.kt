package com.shadowsafe.secretsmanagerbackend.folders.service.impl

import com.shadowsafe.secretsmanagerbackend.folders.dto.FolderDTO
import com.shadowsafe.secretsmanagerbackend.folders.dto.FolderRequestDTO
import com.shadowsafe.secretsmanagerbackend.folders.model.FolderEntity
import com.shadowsafe.secretsmanagerbackend.folders.repo.FoldersRepository
import com.shadowsafe.secretsmanagerbackend.folders.service.FoldersService
import com.shadowsafe.secretsmanagerbackend.shared.exception.GenericErrorCodes
import com.shadowsafe.secretsmanagerbackend.shared.exception.GenericException
import org.springframework.stereotype.Service
import java.time.LocalDateTime

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
                folderEntity.createdAt,
                folderEntity.updatedAt
            )
        }
    }

    override fun saveFolders(folderRequest: FolderRequestDTO): FolderDTO {

        var folderEntity: FolderEntity

        if (!folderRequest.parent.isNullOrEmpty()) {

            var parent = foldersRepository.findById(folderRequest.parent).get()

            var parentsList: ArrayList<String> = arrayListOf()
            parentsList += parent.parents!!
            parentsList += parent._id.toHexString()

            folderEntity = foldersRepository.save(
                FolderEntity(
                    name = folderRequest.name!!,
                    parents = parentsList,
                    children = arrayListOf<String>(),
                    secrets = arrayListOf<String>(),
                    createdAt = LocalDateTime.now(),
                    updatedAt = LocalDateTime.now()
                )
            )

            parent.children += folderEntity._id.toHexString()
            foldersRepository.save(parent)
        } else {
            folderEntity = foldersRepository.save(
                FolderEntity(
                    name = folderRequest.name!!,
                    parents = arrayListOf<String>(),
                    children = arrayListOf<String>(),
                    secrets = arrayListOf<String>(),
                    createdAt = LocalDateTime.now(),
                    updatedAt = LocalDateTime.now()
                )
            )
        }

        return FolderDTO(
            folderEntity._id.toHexString(),
            folderEntity.name,
            folderEntity.parents,
            folderEntity.children,
            folderEntity.secrets,
            folderEntity.createdAt,
            folderEntity.updatedAt
        )
    }
}
