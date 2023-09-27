package com.shadowsafe.secretsmanagerbackend.usermanagement.users.service.impl

import com.shadowsafe.secretsmanagerbackend.shared.exception.GenericErrorCodes
import com.shadowsafe.secretsmanagerbackend.shared.exception.GenericException
import com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.repository.UserGroupsRepository
import com.shadowsafe.secretsmanagerbackend.usermanagement.users.dto.*
import com.shadowsafe.secretsmanagerbackend.usermanagement.users.model.UsersEntity
import com.shadowsafe.secretsmanagerbackend.usermanagement.users.repository.UsersRepository
import com.shadowsafe.secretsmanagerbackend.usermanagement.users.service.UsersService
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UsersServiceImpl(
    private val usersRepository: UsersRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
    private val userGroupsRepository: UserGroupsRepository,
) : UsersService {

    @Value("\${createAdmin.token}")
    lateinit var createAdminToken: String

    override fun createUser(request: CreateUserRequestDTO) {
        usersRepository.save(
            UsersEntity(
                email = request.username,
                password = bCryptPasswordEncoder.encode(request.password),
                isAdmin = request.isAdmin,
                name = request.name ?: "",
                role = emptyList(),
            ),
        )
    }

    override fun getAllUsers(): GetUsersResponseDTO {
        val users = usersRepository.findAll()
        var usersResponseDTOList = arrayListOf<UsersResponseDTO>()
        if (users.isNotEmpty()) {
            run {
                users.map {
                    usersResponseDTOList.add(
                        UsersResponseDTO(
                            _id = it._id.toHexString(),
                            username = it.email,
                            isAdmin = it.isAdmin,
                            name = it.name,
                            isActive = null,
                        ),
                    )
                }
            }
        } else {
            return GetUsersResponseDTO(users = emptyList())
        }
        return GetUsersResponseDTO(users = usersResponseDTOList)
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

    override fun getGroupsOfUser(userId: String): GetGroupsOfUserResponseDTO {
        val user = usersRepository.findById(userId)
        if (user.isEmpty) throw GenericException(GenericErrorCodes.USER_NOT_FOUND)
        val userGroups = userGroupsRepository.getGroupsOfUser(listOf(userId))
        if (userGroups.isNullOrEmpty()) return GetGroupsOfUserResponseDTO(user.get().name, user.get().email)
        return GetGroupsOfUserResponseDTO(
            user.get().name,
            user.get().email,
            "",
            userGroups.map { item -> GroupResponseDTO(item._id.toHexString(), item.name) },
        )
    }
}
