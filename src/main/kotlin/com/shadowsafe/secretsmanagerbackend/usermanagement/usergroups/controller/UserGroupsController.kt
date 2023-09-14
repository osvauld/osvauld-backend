package com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.controller

import com.shadowsafe.secretsmanagerbackend.shared.aop.AppController
import com.shadowsafe.secretsmanagerbackend.shared.rest.ResponseDTO
import com.shadowsafe.secretsmanagerbackend.shared.rest.createSuccessResponse
import com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.dto.AddUserGroupRequestDTO
import com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.dto.AddUserToUserGroupRequestDTO
import com.shadowsafe.secretsmanagerbackend.usermanagement.usergroups.service.UserGroupsService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserGroupsController(
    private val userGroupsService: UserGroupsService,
): AppController() {
    @PostMapping("/groups")
    fun addUserGroup(@RequestBody request: AddUserGroupRequestDTO): ResponseEntity<ResponseDTO> {
        val userId = getCurrentAuthenticatedUserId()
        userGroupsService.addUserGroup(request, userId)
        return createSuccessResponse("Successfully created user group", null)
    }

    @PostMapping("/groups/user")
    fun addUserToUserGroups(@RequestBody request: AddUserToUserGroupRequestDTO): ResponseEntity<ResponseDTO> {
        val userId = getCurrentAuthenticatedUserId()
        userGroupsService.addUserToUserGroup(request, userId)
        return createSuccessResponse(
            "Successfully added user to user group",
            null,
        )
    }

    @GetMapping("/groups")
    fun getAllGroups(): ResponseEntity<ResponseDTO> {
        return createSuccessResponse(
            "Successfully fetched user groups",
            null
        )
    }
}
