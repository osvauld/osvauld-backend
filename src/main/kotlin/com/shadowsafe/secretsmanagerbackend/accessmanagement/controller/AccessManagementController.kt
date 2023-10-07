package com.shadowsafe.secretsmanagerbackend.accessmanagement.controller

import com.shadowsafe.secretsmanagerbackend.accessmanagement.dto.AddAccessForFoldersRequestDTO
import com.shadowsafe.secretsmanagerbackend.accessmanagement.service.AccessManagementService
import com.shadowsafe.secretsmanagerbackend.shared.aop.AppController
import com.shadowsafe.secretsmanagerbackend.shared.rest.ResponseDTO
import com.shadowsafe.secretsmanagerbackend.shared.rest.createSuccessResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AccessManagementController(
    private val accessManagementService: AccessManagementService,
) : AppController() {

    @PostMapping("/access")
    fun saveFolder(@RequestBody request: AddAccessForFoldersRequestDTO): ResponseEntity<ResponseDTO> {
        val userId = getCurrentAuthenticatedUserId()
        accessManagementService.saveAccessForFolders(request, userId)
        return createSuccessResponse(
            "Successfully added access",
            null,
        )
    }
}
