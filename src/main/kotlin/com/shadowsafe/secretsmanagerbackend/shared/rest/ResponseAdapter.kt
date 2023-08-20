package com.shadowsafe.secretsmanagerbackend.shared.rest

import com.shadowsafe.secretsmanagerbackend.shared.IDTO
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity

fun createSuccessResponse(message: String, dto: IDTO?): ResponseEntity<ResponseDTO> = ResponseEntity
    .ok()
    .body(GenericSuccessResponseDTO(HttpStatus.OK.value(), "Success", message, dto))

fun createErrorResponse(httpStatus: HttpStatus, message: String, status: String): ResponseEntity<ResponseDTO> = ResponseEntity
    .ok()
    .body(ErrorResponseDTO(httpStatus.value(), status, message, null))

fun createSuccessResourceResponse(resource: Resource?): ResponseEntity<Resource> = ResponseEntity.ok()
    .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=${resource?.filename}")
    .body(resource)
