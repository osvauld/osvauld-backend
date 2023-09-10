package com.shadowsafe.secretsmanagerbackend.usermanagement.service

import com.shadowsafe.secretsmanagerbackend.usermanagement.dto.CheckIfAdminResponseDTO
import com.shadowsafe.secretsmanagerbackend.usermanagement.dto.CreateUserRequestDTO
import com.shadowsafe.secretsmanagerbackend.usermanagement.dto.GetUsersResponseDTO

interface UsersService {
    fun createUser(request: CreateUserRequestDTO)

    fun getAllUsers(pageNo: Int, pageSize: Int): GetUsersResponseDTO
    fun checkIfAdminPresent(): CheckIfAdminResponseDTO
}
