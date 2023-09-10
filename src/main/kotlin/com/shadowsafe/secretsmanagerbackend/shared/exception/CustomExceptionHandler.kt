package com.shadowsafe.secretsmanagerbackend.shared.exception

import com.shadowsafe.secretsmanagerbackend.shared.rest.ResponseDTO
import com.shadowsafe.secretsmanagerbackend.shared.rest.createErrorResponse
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.client.HttpServerErrorException
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice(basePackages = ["com.shadowsafe.secretsmanagerbackend"])
class CustomExceptionHandler : ResponseEntityExceptionHandler() {

    @Override
    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest,
    ): ResponseEntity<Any> {
        return ResponseEntity.ok().body(
            ErrorResponseDTO(
                status.value(),
                "Validation Error",
                "Http Message not readable",
                null,
            ),
        )
    }

    @Override
    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest,
    ): ResponseEntity<Any> {
        val errors = ex.bindingResult.allErrors.map { error ->
            "Field Error ${error.defaultMessage} in ${(error as FieldError).field}"
        }.toList()
        return ResponseEntity.ok().body(
            ErrorResponseDTO(
                status.value(),
                "Validation Error",
                errors.toString(),
                null,
            ),
        )
    }

    @Override
    override fun handleBindException(
        ex: BindException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest,
    ): ResponseEntity<Any> {
        val errors = ex.bindingResult.allErrors.map { error ->
            "Field Error ${error.defaultMessage} in ${(error as FieldError).field}"
        }.toList()
        return ResponseEntity.ok().body(
            ErrorResponseDTO(
                status.value(),
                "Validation Error",
                errors.toString(),
                null,
            ),
        )
    }

    @ExceptionHandler(GenericException::class)
    fun handleAllExceptions(
        ex: GenericException,
        request: WebRequest,
    ): ResponseEntity<ResponseDTO> {
        return createErrorResponse(
            HttpStatus.CONFLICT,
            ex.errorCode.defaultMessage,
            "Application Error",
        )
    }

    @ExceptionHandler(HttpServerErrorException.InternalServerError::class)
    fun handleInternalServerExceptions(
        ex: HttpServerErrorException.InternalServerError,
        request: WebRequest,
    ): ResponseEntity<ResponseDTO> {
        return createErrorResponse(
            HttpStatus.CONFLICT,
            ex.localizedMessage,
            "FinServe Application Error",
        )
    }

    @ExceptionHandler(EmptyResultDataAccessException::class)
    fun handleEmptyResultExceptions(
        ex: EmptyResultDataAccessException,
        request: WebRequest,
    ): ResponseEntity<ResponseDTO> {
        return createErrorResponse(
            HttpStatus.CONFLICT,
            ex.localizedMessage,
            "FinServe Application Error",
        )
    }
}
