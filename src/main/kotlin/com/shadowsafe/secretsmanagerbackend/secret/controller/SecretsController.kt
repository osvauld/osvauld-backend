package com.shadowsafe.secretsmanagerbackend.secret.controller

import com.shadowsafe.secretsmanagerbackend.secret.dto.SaveSecretsRequestDTO
import com.shadowsafe.secretsmanagerbackend.secret.service.SecretsService
import com.shadowsafe.secretsmanagerbackend.shared.rest.ResponseDTO
import com.shadowsafe.secretsmanagerbackend.shared.rest.createSuccessResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class SecretsController(
    private val secretsService: SecretsService,
) {
    @GetMapping("/secrets/health")
    fun healthCheck(): String {
        return "OK"
    }

    @PostMapping("/secrets")
    fun saveSecrets(@RequestBody request: SaveSecretsRequestDTO): ResponseEntity<ResponseDTO> {
        return createSuccessResponse(
            "Success",
            secretsService.saveSecrets(request),
        )
    }

    @GetMapping("/secrets")
    fun getSecretsByUrl(@RequestParam url: String): ResponseEntity<ResponseDTO> {
        return createSuccessResponse(
            "Success",
            secretsService.getSecretsByUrl(url),
        )
    }

    @GetMapping("/secrets/urls")
    fun getAllUrls(): ResponseEntity<ResponseDTO> {
        return createSuccessResponse(
            "Success",
            secretsService.getAllUrls(),
        )
    }
}
