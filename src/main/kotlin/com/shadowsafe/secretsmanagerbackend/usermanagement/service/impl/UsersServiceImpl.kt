package com.shadowsafe.secretsmanagerbackend.usermanagement.service.impl

import com.shadowsafe.secretsmanagerbackend.usermanagement.dto.CreateUserRequestDTO
import com.shadowsafe.secretsmanagerbackend.usermanagement.dto.GetUsersResponseDTO
import com.shadowsafe.secretsmanagerbackend.usermanagement.dto.UsersResponseDTO
import com.shadowsafe.secretsmanagerbackend.usermanagement.model.UsersEntity
import com.shadowsafe.secretsmanagerbackend.usermanagement.repository.UsersRepository
import com.shadowsafe.secretsmanagerbackend.usermanagement.service.UsersService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class UsersServiceImpl(
    private val usersRepository: UsersRepository,
) : UsersService {
    override fun createUser(request: CreateUserRequestDTO) {
        usersRepository.save(
            UsersEntity(
                email = request.username,
                password = request.password,
                isActive = true,
                isAdmin = request.isAdmin,
                name = "",
                tags = emptyList(),
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
                    item.isActive,
                    item.isAdmin,
                    item.tags,
                )
            },
            pageNo,
            pageSize,
            users.numberOfElements,
        )
    }
}
