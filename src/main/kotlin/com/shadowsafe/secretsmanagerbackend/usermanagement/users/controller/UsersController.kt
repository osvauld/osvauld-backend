package com.shadowsafe.secretsmanagerbackend.usermanagement.users.controller

import com.shadowsafe.secretsmanagerbackend.shared.exception.GenericErrorCodes
import com.shadowsafe.secretsmanagerbackend.shared.exception.GenericException
import com.shadowsafe.secretsmanagerbackend.shared.rest.ResponseDTO
import com.shadowsafe.secretsmanagerbackend.shared.rest.createSuccessResponse
import com.shadowsafe.secretsmanagerbackend.usermanagement.users.dto.CreateUserRequestDTO
import com.shadowsafe.secretsmanagerbackend.usermanagement.users.service.UsersService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
class UsersController(
    private val usersService: UsersService,
) {
    @GetMapping("/users")
    fun getAllSecrets(): ResponseEntity<ResponseDTO> {
        return createSuccessResponse(
            "Success",
            usersService.getAllUsers(),
        )
    }

    @PostMapping("/users", "/public/users")
    fun saveSecrets(@RequestBody request: CreateUserRequestDTO, httpRequest: HttpServletRequest): ResponseEntity<ResponseDTO> {
        if (httpRequest.requestURI == "/public/users") {
            if (usersService.checkIfAdminPresent().isPresent) throw GenericException(GenericErrorCodes.ADMIN_ALREADY_CREATED)
        }
        usersService.createUser(request)
        return createSuccessResponse(
            "Success",
            null,
        )
    }
    @GetMapping("/public/admin-check")
    fun checkIsAdminPresent(): ResponseEntity<ResponseDTO> {
        return createSuccessResponse(
            "Success",
            usersService.checkIfAdminPresent(),
        )
    }
}
