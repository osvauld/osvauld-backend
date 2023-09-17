package com.shadowsafe.secretsmanagerbackend.usermanagement.users.service

import com.shadowsafe.secretsmanagerbackend.usermanagement.users.dto.CheckIfAdminResponseDTO
import com.shadowsafe.secretsmanagerbackend.usermanagement.users.dto.CreateUserRequestDTO
import com.shadowsafe.secretsmanagerbackend.usermanagement.users.dto.GetUsersResponseDTO

interface UsersService {
    fun createUser(request: CreateUserRequestDTO)

    fun getAllUsers(pageNo: Int, pageSize: Int): GetUsersResponseDTO
    fun checkIfAdminPresent(): CheckIfAdminResponseDTO
}
