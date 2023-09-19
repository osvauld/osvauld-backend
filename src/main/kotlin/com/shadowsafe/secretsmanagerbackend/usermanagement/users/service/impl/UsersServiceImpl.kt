package com.shadowsafe.secretsmanagerbackend.usermanagement.users.service.impl

import com.shadowsafe.secretsmanagerbackend.usermanagement.users.dto.CheckIfAdminResponseDTO
import com.shadowsafe.secretsmanagerbackend.usermanagement.users.dto.CreateUserRequestDTO
import com.shadowsafe.secretsmanagerbackend.usermanagement.users.dto.GetUsersResponseDTO
import com.shadowsafe.secretsmanagerbackend.usermanagement.users.dto.UsersResponseDTO
import com.shadowsafe.secretsmanagerbackend.usermanagement.users.model.UsersEntity
import com.shadowsafe.secretsmanagerbackend.usermanagement.users.repository.UsersRepository
import com.shadowsafe.secretsmanagerbackend.usermanagement.users.service.UsersService
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UsersServiceImpl(
    private val usersRepository: UsersRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
) : UsersService {

    @Value("\${createAdmin.token}")
    lateinit var createAdminToken: String

    override fun createUser(request: CreateUserRequestDTO) {
        usersRepository.save(
            UsersEntity(
                email = request.username,
                password = bCryptPasswordEncoder.encode(request.password),
//                isActive = true,
                isAdmin = request.isAdmin,
                name = request.name ?: "",
//                tags = emptyList(),
                    role = emptyList()
            ),
        )
    }

    override fun getAllUsers(pageNo: Int, pageSize: Int): GetUsersResponseDTO {
        val pageable: Pageable = PageRequest.of(pageNo, pageSize)
        val users = usersRepository.findAll(pageable)
        return GetUsersResponseDTO(
            users.content.map { item ->
                UsersResponseDTO(
                    item.email,
                    item.name,
//                    item.isActive,
                    item.isAdmin,
//                    item.tags,
                        true
                )
            },
            pageNo,
            pageSize,
            users.numberOfElements,
        )
    }

    override fun checkIfAdminPresent(): CheckIfAdminResponseDTO {
        val adminUser = usersRepository.findAdminUsers()
        if (adminUser.isNullOrEmpty()) {
            return CheckIfAdminResponseDTO(
                token = createAdminToken,
                isPresent = false,
            )
        } else {
            return CheckIfAdminResponseDTO(
                token = "",
                isPresent = true,
            )
        }
    }
}
