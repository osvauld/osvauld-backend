package com.shadowsafe.secretsmanagerbackend.accessmanagement.service.impl

import com.shadowsafe.secretsmanagerbackend.accessmanagement.dto.AddAccessForFoldersRequestDTO
import com.shadowsafe.secretsmanagerbackend.accessmanagement.model.GroupFolderAccessEntity
import com.shadowsafe.secretsmanagerbackend.accessmanagement.model.UserFolderAccessEntity
import com.shadowsafe.secretsmanagerbackend.accessmanagement.service.AccessManagementService
import com.shadowsafe.secretsmanagerbackend.folders.repo.FoldersRepository
import com.shadowsafe.secretsmanagerbackend.shared.exception.GenericErrorCodes
import com.shadowsafe.secretsmanagerbackend.shared.exception.GenericException
import com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.service.UserGroupsService
import com.shadowsafe.secretsmanagerbackend.usermanagement.users.service.UsersService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class AccessManagementServiceImpl(
    private val usersService: UsersService,
    private val foldersRepository: FoldersRepository,
    private val userGroupsService: UserGroupsService,
) : AccessManagementService {
    override fun saveAccessForFolders(request: AddAccessForFoldersRequestDTO, userId: String) {
        val user = usersService.getUserById(userId) ?: throw GenericException(GenericErrorCodes.USER_NOT_FOUND)
        val folder = foldersRepository.findByIdOrNull(request.folderId) ?: throw GenericException(GenericErrorCodes.FOLDER_NOT_FOUND)
        if (!request.groupAccess.isNullOrEmpty()) {
            request.groupAccess.forEach { item ->
                if (!folder.groupAccessList.isNullOrEmpty()) {
                    if (folder.groupAccessList!!.find { f -> f.groupId == item.groupId } == null) {
                        folder.groupAccessList = folder.groupAccessList?.plus(item)
                    }
                } else {
                    folder.groupAccessList = listOf(item)
                }
                userGroupsService.addFolderAccess(item.groupId, GroupFolderAccessEntity(request.folderId, item.type))
            }
        }
        if (!request.userAccess.isNullOrEmpty()) {
            request.userAccess.forEach { item ->
                if (!folder.userAccessList.isNullOrEmpty()) {
                    if (folder.userAccessList!!.find { f -> f.userId == item.userId } == null) {
                        folder.userAccessList = folder.userAccessList?.plus(item)
                    }
                } else {
                    folder.userAccessList = listOf(item)
                }
                usersService.addFolderToUser(item.userId, UserFolderAccessEntity(request.folderId, item.type))
            }
        }

        foldersRepository.save(folder)
    }
}
