package com.shadowsafe.secretsmanagerbackend.secret.controller

import com.shadowsafe.secretsmanagerbackend.secret.dto.SaveSecretsRequestDTO
import com.shadowsafe.secretsmanagerbackend.secret.service.SecretsService
import com.shadowsafe.secretsmanagerbackend.shared.rest.ResponseDTO
import com.shadowsafe.secretsmanagerbackend.shared.rest.createSuccessResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SecretsController(
    private val secretsService: SecretsService,
) {

    @GetMapping("/secrets")
    fun getAllSecrets(@RequestParam(name = "_page") pageNo: Int, @RequestParam(name = "_limit") pageSize: Int, @RequestParam search: String): ResponseEntity<ResponseDTO> {
        return createSuccessResponse(
            "Success",
            secretsService.getAllSecrets(pageNo, pageSize, search),
        )
    }

    @GetMapping("/secrets/{id}")
    fun getSecretsDetails(@PathVariable(name = "id") id: String): ResponseEntity<ResponseDTO> {
        return createSuccessResponse(
            "Success",
            secretsService.getSecretDetails(id),
        )
    }

    @PostMapping("/secrets")
    fun saveSecrets(@RequestBody request: SaveSecretsRequestDTO): ResponseEntity<ResponseDTO> {
        secretsService.saveSecrets(request)
        return createSuccessResponse(
            "Success",
            null,
        )
    }

    @PutMapping("/secrets/{id}")
    fun editSecrets(@PathVariable(name = "id") id: String, @RequestBody request: SaveSecretsRequestDTO): ResponseEntity<ResponseDTO> {
        secretsService.editSecrets(request, id)
        return createSuccessResponse(
            "Success",
            null,
        )
    }
}
