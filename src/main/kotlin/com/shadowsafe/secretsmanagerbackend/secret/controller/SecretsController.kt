package com.shadowsafe.secretsmanagerbackend.secret.controller

import com.shadowsafe.secretsmanagerbackend.secret.service.SecretsService
import com.shadowsafe.secretsmanagerbackend.shared.rest.ResponseDTO
import com.shadowsafe.secretsmanagerbackend.shared.rest.createSuccessResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SecretsController(
    private val secretsService: SecretsService,
) {

    @GetMapping("/secrets")
    fun getAllSecrets(@RequestParam(name = "pageNo") pageNo: Int, @RequestParam(name = "pageSize") pageSize: Int, @RequestParam search: String): ResponseEntity<ResponseDTO> {
        return createSuccessResponse(
            "Success",
            secretsService.getAllSecrets(pageNo, pageSize, search),
        )
    }

    @GetMapping("/secrets")
    fun getSecretsDetails(@RequestParam(name = "id") id: String): ResponseEntity<ResponseDTO> {
        return createSuccessResponse(
            "Success",
            secretsService.getSecretDetails(id),
        )
    }
}
