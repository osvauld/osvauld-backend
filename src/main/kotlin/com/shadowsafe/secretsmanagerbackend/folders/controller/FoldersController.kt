package com.shadowsafe.secretsmanagerbackend.folders.controller

import com.shadowsafe.secretsmanagerbackend.folders.dto.FolderRequestDTO
import com.shadowsafe.secretsmanagerbackend.folders.service.FoldersService
import com.shadowsafe.secretsmanagerbackend.shared.aop.AppController
import com.shadowsafe.secretsmanagerbackend.shared.rest.ResponseDTO
import com.shadowsafe.secretsmanagerbackend.shared.rest.createSuccessResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class FoldersController(
    private val foldersService: FoldersService,
) : AppController() {

    @GetMapping("/folders/{id}")
    fun getFolderDetails(@PathVariable(name = "id") id: String): ResponseEntity<ResponseDTO> {
        val userId = getCurrentAuthenticatedUserId()
        return createSuccessResponse(
            "Success",
            foldersService.getFolder(id, userId),
        )
    }

    @PostMapping("/folders")
    fun saveFolder(@RequestBody request: FolderRequestDTO): ResponseEntity<ResponseDTO> {
        val userId = getCurrentAuthenticatedUserId()
        foldersService.saveFolders(request, userId)
        return createSuccessResponse(
            "Success",
            null,
        )
    }

    @GetMapping("/folder/user")
    fun getFolderUser(@RequestParam id: String): ResponseEntity<ResponseDTO> {
        val userId = getCurrentAuthenticatedUserId()
        return createSuccessResponse(
            "Successfully fetched user in group",
            foldersService.getUsersInFolder(id, userId),
        )
    }

    @GetMapping("/folders/structure")
    fun getFolderStructure(): ResponseEntity<ResponseDTO> {
        val userId = getCurrentAuthenticatedUserId()
        return createSuccessResponse(
            "Success",
            foldersService.getFolderOfUser(userId),
        )
    }
}
