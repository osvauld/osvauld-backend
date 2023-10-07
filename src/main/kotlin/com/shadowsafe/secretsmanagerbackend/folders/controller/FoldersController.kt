package com.shadowsafe.secretsmanagerbackend.folders.controller

import com.shadowsafe.secretsmanagerbackend.folders.dto.FolderRequestDTO
import com.shadowsafe.secretsmanagerbackend.folders.service.FoldersService
import com.shadowsafe.secretsmanagerbackend.shared.aop.AppController
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
) : AppController() {

    @GetMapping("/folders/{id}")
    fun getFolderDetails(@PathVariable(name = "id") id: String): ResponseEntity<ResponseDTO> {
        return createSuccessResponse(
            "Success",
            foldersService.getFolder(id),
        )
    }

    @PostMapping("/folders")
    fun saveFolder(@RequestBody request: FolderRequestDTO): ResponseEntity<ResponseDTO> {
        val userId = getCurrentAuthenticatedUserId()
        return createSuccessResponse(
            "Success",
            foldersService.saveFolders(request, userId),
        )
    }

    @PostMapping("/folders/structure")
    fun createNewFolderStructureForOrganisation(): ResponseEntity<ResponseDTO> {
        return createSuccessResponse(
            "Success",
            foldersService.createNewFolderStructureForOrganisation(),
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
