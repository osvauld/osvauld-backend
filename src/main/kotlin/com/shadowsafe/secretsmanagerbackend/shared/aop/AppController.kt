package com.shadowsafe.secretsmanagerbackend.shared.aop

import com.shadowsafe.secretsmanagerbackend.shared.rest.ResponseDTO
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import javax.naming.AuthenticationException

abstract class AppController {
    val logger: Logger = LogManager.getLogger(javaClass)

    fun logRequest(message: String) {
        logger.log(Level.INFO, message)
    }

    fun logResponse(response: ResponseEntity<ResponseDTO>, message: String) {
        logger.log(
            if (response.statusCode == HttpStatus.OK || response.statusCode == HttpStatus.CREATED) Level.INFO else Level.ERROR,
            message,
        )
    }

    fun getCurrentAuthenticatedUserId(): String {
        try {
            val authToken = SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken
            if (authToken != null) {
                return authToken.principal as String
            } else {
                throw AuthenticationException()
            }
        } catch (e: Exception) {
            throw AuthenticationException()
        }
    }
}
