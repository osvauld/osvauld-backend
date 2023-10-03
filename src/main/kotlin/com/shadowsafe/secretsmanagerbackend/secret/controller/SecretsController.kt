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
        return "OK" ;
    }

    @PostMapping("/secrets")
    fun saveSecrets(@RequestBody request: SaveSecretsRequestDTO): ResponseEntity<ResponseDTO> {
        return createSuccessResponse(
            "Success",
            secretsService.saveSecrets(request)
        )
    }

    @GetMapping("/secrets/url/")
    fun getSecretsByUrl(@RequestParam request: String): ResponseEntity<ResponseDTO> {
        return createSuccessResponse(
                "Success",
                secretsService.getSecretsByUrl(request)
        )
    }

//    @GetMapping("/secrets/url/")
//    fun getAllUrls(): ResponseEntity<ResponseDTO> {
//        return createSuccessResponse(
//                "Success",
//                secretsService.getAllUrls(),
//        )
//    }
}
