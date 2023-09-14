 
package com.shadowsafe.secretsmanagerbackend.folders.controller

import com.shadowsafe.secretsmanagerbackend.folders.dto.FolderDTO
import com.shadowsafe.secretsmanagerbackend.folders.service.FoldersService
import com.shadowsafe.secretsmanagerbackend.shared.rest.ResponseDTO
import com.shadowsafe.secretsmanagerbackend.shared.rest.createSuccessResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class FoldersController(
    private val foldersService: FoldersService,
) {

    @GetMapping("/folders/{id}")
    fun getFolderDetails(@PathVariable(name = "id") id: String): ResponseEntity<ResponseDTO> {
        return createSuccessResponse(
            "Success",
            foldersService.getFolder(id),
        )
    }

    @PostMapping("/folders")
    fun saveSecrets(@RequestBody request: FolderDTO): ResponseEntity<ResponseDTO> {
        return createSuccessResponse(
            "Success",
            foldersService.saveFolders(request),
        )
    }
}
