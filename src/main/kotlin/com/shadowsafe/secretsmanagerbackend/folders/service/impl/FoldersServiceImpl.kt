package com.shadowsafe.secretsmanagerbackend.folders.service.impl

import com.shadowsafe.secretsmanagerbackend.accessmanagement.enums.SecretUserAccessType
import com.shadowsafe.secretsmanagerbackend.accessmanagement.model.UserFolderAccessEntity
import com.shadowsafe.secretsmanagerbackend.folders.dto.*
import com.shadowsafe.secretsmanagerbackend.folders.model.FolderEntity
import com.shadowsafe.secretsmanagerbackend.folders.model.FolderTree
import com.shadowsafe.secretsmanagerbackend.folders.repo.FolderTreeRepository
import com.shadowsafe.secretsmanagerbackend.folders.repo.FoldersRepository
import com.shadowsafe.secretsmanagerbackend.folders.service.FoldersService
import com.shadowsafe.secretsmanagerbackend.secret.dto.SecretsResponseDTO
import com.shadowsafe.secretsmanagerbackend.secret.model.SecretsEntity
import com.shadowsafe.secretsmanagerbackend.secret.repository.SecretsRepository
import com.shadowsafe.secretsmanagerbackend.shared.exception.GenericErrorCodes
import com.shadowsafe.secretsmanagerbackend.shared.exception.GenericException
import com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.repository.GroupAccessRepository
import com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.repository.UserGroupsRepository
import com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.service.UserGroupsService
import com.shadowsafe.secretsmanagerbackend.usermanagement.users.dto.GroupResponseDTO
import com.shadowsafe.secretsmanagerbackend.usermanagement.users.repository.UserFolderAccessRepository
import com.shadowsafe.secretsmanagerbackend.usermanagement.users.service.UsersService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.HashSet

@Service
class FoldersServiceImpl(
    private val foldersRepo: FoldersRepository,
    private val folderTreeRepo: FolderTreeRepository,
    private val secretsRepo: SecretsRepository,
    private val groupAccessRepository: GroupAccessRepository,
    private val userGroupsRepository: UserGroupsRepository,
    private val userFolderAccessRepository: UserFolderAccessRepository,
    private val usersService: UsersService,
    private val userGroupsService: UserGroupsService,
) : FoldersService {

    override fun getFolder(id: String, userId: String): FolderResponseDTO {
        val folder = foldersRepo.findById(id)
        if (folder.isEmpty) {
            throw GenericException(GenericErrorCodes.FOLDER_NOT_FOUND)
        } else {
            // TODO: Fix user access on folders and uncomment these lines
//            if (!(
//                    userGroupsService.checkIfUserPresentInGroups(
//                        userId,
//                        folder.get().groupAccessList!!.map { it.groupId },
//                    ) || folder.get().userAccessList!!.map { it.userId }.contains(userId)
//                    )
//            ) {
//                throw GenericException(GenericErrorCodes.USER_HAS_NO_ACCESS)
//            }
            val folderEntity = folder.get()
            val secretsList = mutableListOf<SecretsEntity>()

            folderEntity.secrets.forEach { secretId ->
                val secretEntity = secretsRepo.findById(secretId)
                if (secretEntity != null) {
                    secretsList.add(secretEntity.get())
                } else {
                    throw GenericException(GenericErrorCodes.SECRET_NOT_FOUND)
                }
            }

            val resultList: List<SecretsEntity> = secretsList.toList()

            return FolderResponseDTO(
                folderEntity._id.toHexString(),
                folderEntity.label,
                folderEntity.parents,
                folderEntity.children,
                resultList.map {
                    SecretsResponseDTO(
                        id = it._id.toHexString(),
                        name = it.name,
                        credentials = it.credentials,
                        parent = it.parent.last(),
                        description = it.description,
                        createdAt = it.createdAt,
                        updatedAt = it.updatedAt,
                    )
                },
                folderEntity.createdAt,
                folderEntity.updatedAt,
            )
        }
    }

    override fun saveFolders(folderRequest: FolderRequestDTO, userId: String) {
        var folderEntity: FolderEntity

        if (!folderRequest.parent.isNullOrEmpty()) {
            var parent = foldersRepo.findById(folderRequest.parent).get()

            var parentsList: ArrayList<String> = arrayListOf()
            parentsList += parent.parents!!
            parentsList += parent._id.toHexString()
            folderEntity = foldersRepo.save(
                FolderEntity(
                    label = folderRequest.label!!,
                    parents = parentsList,
                    children = arrayListOf<String>(),
                    secrets = arrayListOf<String>(),
                    createdAt = LocalDateTime.now(),
                    updatedAt = LocalDateTime.now(),
                    groupAccessList = parent.groupAccessList,
                    userAccessList = parent.userAccessList,
                ),
            )

            parent.children += folderEntity._id.toHexString()
            foldersRepo.save(parent)
        } else {
            folderEntity = foldersRepo.save(
                FolderEntity(
                    label = folderRequest.label!!,
                    parents = arrayListOf<String>(),
                    children = arrayListOf<String>(),
                    secrets = arrayListOf<String>(),
                    createdAt = LocalDateTime.now(),
                    updatedAt = LocalDateTime.now(),
                ),
            )
        }
        usersService.addFolderToUser(
            userId,
            UserFolderAccessEntity(
                folderId = folderEntity._id.toHexString(),
                SecretUserAccessType.OWNER,
            ),
        )
    }

    override fun createNewFolderStructureForOrganisation(): FolderTreeDTO {
        var folderEntity = foldersRepo.save(
            FolderEntity(
                label = "Vault",
                parents = arrayListOf(),
                children = arrayListOf(),
                secrets = arrayListOf(),
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
            ),
        )

        val folderTree = folderTreeRepo.save(
            FolderTree(
                type = "root",
                rootFolderId = folderEntity._id.toHexString(),
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
            ),
        )

        return FolderTreeDTO(
            id = folderTree._id.toHexString(),
            organisationId = folderTree.organisationId.toHexString(),
            rootFolderId = folderTree.rootFolderId,
            createdAt = folderTree.createdAt,
            updatedAt = folderTree.updatedAt,
        )
    }

    override fun getFolderOfUser(userId: String): GetFoldersResponseDTO {
        val user = usersService.getUserById(userId) ?: throw GenericException(GenericErrorCodes.USER_NOT_FOUND)
        val folderList = mutableListOf<String>()
        val groupAccessList = groupAccessRepository.findAccessContainingUser(userId)
        if (!groupAccessList.isNullOrEmpty()) {
            groupAccessList.forEach { item ->
                val group = userGroupsRepository.findByIdOrNull(item.groupId)!!
                group.folderAccessList?.map { t -> t.folderId }?.let { folderList.addAll(it) }
            }
        }
        val userAccess = userFolderAccessRepository.findByUserId(userId)
        if (userAccess != null) {
            userAccess.folderAccess.map { t -> t.folderId }?.let { folderList.addAll(it) }
        }
        val folders = foldersRepo.findAllById(folderList)
        val response = GetFoldersResponseDTO(
            userId,
            mutableListOf(),
        )
        organiseFolderTrees(folders.toList()).forEach { item ->
            response.folders = response.folders?.plus(getFolderStructure(item._id.toHexString()))
        }
        return response
    }

    override fun getUsersInFolder(folderId: String, userId: String): GetUsersOfFolder {
        val folder = foldersRepo.findById(folderId)
        if (folder.isEmpty) {
            throw GenericException(GenericErrorCodes.FOLDER_NOT_FOUND)
        } else {
            val folder = folder.get()
            if (userGroupsService.checkIfUserPresentInGroups(
                    userId,
                    folder.groupAccessList!!.map { it.groupId },
                ) || folder.userAccessList!!.map { it.userId }.contains(userId)
            ) {
                throw GenericException(GenericErrorCodes.USER_HAS_NO_ACCESS)
            }

            return GetUsersOfFolder(
                folderId,
                getUsersWithAccessToFolderAndParents(folder).toList(),
            )
        }
    }

    fun getUsersWithAccessToFolderAndParents(folder: FolderEntity): Set<FolderUserDTO> {
        var usersOfFolder: HashSet<FolderUserDTO> = hashSetOf<FolderUserDTO>()
        if (folder.userAccessList != null) {
            usersOfFolder = usersOfFolder.plus(
                folder.userAccessList!!.map { item ->
                    val user = usersService.getUserById(item.userId) ?: throw GenericException(GenericErrorCodes.USER_NOT_FOUND)
                    FolderUserDTO(
                        userId = item.userId,
                        name = user.name,
                        username = user.email,
                        accessType = item.type.name,
                        group = null,
                    )
                },
            ) as HashSet<FolderUserDTO>
        }

        if (folder.groupAccessList != null) {
            folder.groupAccessList!!.forEach { item ->
                val groupAccess = groupAccessRepository.findByGroupId(item.groupId) ?: throw GenericException(GenericErrorCodes.GROUP_NOT_FOUND)

                usersOfFolder = usersOfFolder.plus(
                    groupAccess.accessList.map { k ->
                        val user = usersService.getUserById(k.userId) ?: throw GenericException(GenericErrorCodes.USER_NOT_FOUND)
                        FolderUserDTO(
                            userId = k.userId,
                            name = user.name,
                            username = user.email,
                            accessType = item.type.name,
                            group = listOf(
                                GroupResponseDTO(item.groupId, item.name),
                            ),
                        )
                    },
                ) as HashSet<FolderUserDTO>
            }
        }

        return if (folder.parents.isNotEmpty()) {
            usersOfFolder.plus(getUsersWithAccessToFolderAndParents(foldersRepo.findByIdOrNull(folder.parents.last())!!))
        } else {
            usersOfFolder
        }
    }

    fun getFolderStructure(folderId: String): FolderStructureDTO {
        val folder = foldersRepo.findById(folderId).get()

        val folderStructureDTO = FolderStructureDTO(
            id = folderId,
            label = folder.label,
            parentId = folder.parents.lastOrNull(),
            children = arrayListOf(),
        )

        folder.children.forEach {
            folderStructureDTO.children = folderStructureDTO.children?.plus(getFolderStructure(it))
        }

        return folderStructureDTO
    }

    fun organiseFolderTrees(folderList: List<FolderEntity>): List<FolderEntity> {
        var organisedList = folderList
        folderList.forEach { item ->
            var isChild = false
            item.parents.forEach { p ->
                if (folderList.find { c -> c._id.toHexString() == p } != null) isChild = true
            }
            if (isChild) organisedList = organisedList.filter { o -> o != item }
        }
        return organisedList
    }
}
