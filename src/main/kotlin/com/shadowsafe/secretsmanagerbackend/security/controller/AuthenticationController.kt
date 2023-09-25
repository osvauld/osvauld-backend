package com.shadowsafe.secretsmanagerbackend.security.controller

import com.shadowsafe.secretsmanagerbackend.security.dto.LoginDTO
import com.shadowsafe.secretsmanagerbackend.security.service.AuthenticationService
import com.shadowsafe.secretsmanagerbackend.shared.rest.ResponseDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(produces = ["application/json"])
@CrossOrigin(maxAge = 3600)
class AuthenticationController(
    @Autowired val authenticationService: AuthenticationService,
) {

    @PostMapping("/authenticate")
    fun authenticateUser(@RequestBody loginDTO: LoginDTO): ResponseEntity<ResponseDTO> {
        return authenticationService.authenticateUser(loginDTO)
    }
}
