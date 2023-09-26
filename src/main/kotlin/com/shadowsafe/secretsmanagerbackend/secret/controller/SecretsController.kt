package com.shadowsafe.secretsmanagerbackend.secret.controller

import com.shadowsafe.secretsmanagerbackend.secret.dto.SaveSecretsRequestDTO
import com.shadowsafe.secretsmanagerbackend.secret.service.SecretsService
import com.shadowsafe.secretsmanagerbackend.shared.rest.ResponseDTO
import com.shadowsafe.secretsmanagerbackend.shared.rest.createSuccessResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class SecretsController(
    private val secretsService: SecretsService,
) {

    @PostMapping("/secrets")
    fun saveSecrets(@RequestBody request: SaveSecretsRequestDTO): ResponseEntity<ResponseDTO> {

        return createSuccessResponse(
            "Success",
            secretsService.saveSecrets(request),
        )
    }
}
