package com.shadowsafe.secretsmanagerbackend.usermanagement.controller

import com.shadowsafe.secretsmanagerbackend.shared.rest.ResponseDTO
import com.shadowsafe.secretsmanagerbackend.shared.rest.createSuccessResponse
import com.shadowsafe.secretsmanagerbackend.usermanagement.dto.CreateUserRequestDTO
import com.shadowsafe.secretsmanagerbackend.usermanagement.service.UsersService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class UsersController(
    private val usersService: UsersService,
) {
    @GetMapping("/users")
    fun getAllSecrets(@RequestParam(name = "_page") pageNo: Int, @RequestParam(name = "_limit") pageSize: Int): ResponseEntity<ResponseDTO> {
        return createSuccessResponse(
            "Success",
            usersService.getAllUsers(pageNo, pageSize),
        )
    }

    @PostMapping("/users")
    fun saveSecrets(@RequestBody request: CreateUserRequestDTO): ResponseEntity<ResponseDTO> {
        usersService.createUser(request)
        return createSuccessResponse(
            "Success",
            null,
        )
    }
}
