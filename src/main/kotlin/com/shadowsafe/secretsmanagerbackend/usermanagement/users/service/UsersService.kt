package com.shadowsafe.secretsmanagerbackend.usermanagement.users.service

import com.shadowsafe.secretsmanagerbackend.accessmanagement.model.UserFolderAccessEntity
import com.shadowsafe.secretsmanagerbackend.usermanagement.users.dto.CheckIfAdminResponseDTO
import com.shadowsafe.secretsmanagerbackend.usermanagement.users.dto.CreateUserRequestDTO
import com.shadowsafe.secretsmanagerbackend.usermanagement.users.dto.GetGroupsOfUserResponseDTO
import com.shadowsafe.secretsmanagerbackend.usermanagement.users.dto.GetUsersResponseDTO
import com.shadowsafe.secretsmanagerbackend.usermanagement.users.model.UsersEntity

interface UsersService {
    fun createUser(request: CreateUserRequestDTO)

    fun getAllUsers(): GetUsersResponseDTO
    fun checkIfAdminPresent(): CheckIfAdminResponseDTO

    fun getGroupsOfUser(userId: String): GetGroupsOfUserResponseDTO
    fun checkIfUserPresent(userId: String): Boolean

    fun getUserById(userId: String): UsersEntity?

    fun addFolderToUser(userId: String, folderAccessEntity: UserFolderAccessEntity)
}
