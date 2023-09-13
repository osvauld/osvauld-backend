package com.shadowsafe.secretsmanagerbackend.security.service

import com.shadowsafe.secretsmanagerbackend.security.config.SecurityProperties
import com.shadowsafe.secretsmanagerbackend.security.dto.LoginDTO
import com.shadowsafe.secretsmanagerbackend.security.dto.LoginResponseDTO
import com.shadowsafe.secretsmanagerbackend.shared.rest.GenericSuccessResponseDTO
import com.shadowsafe.secretsmanagerbackend.shared.rest.ResponseDTO
import com.shadowsafe.secretsmanagerbackend.usermanagement.users.repository.UsersRepository
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service
import java.io.IOException
import java.util.Date

@Service
class AuthenticationService(
    @Autowired val authenticationManager: AuthenticationManager,
    @Autowired val userRepository: UsersRepository,
    @Autowired val securityProperties: SecurityProperties,
) {

    fun authenticateUser(request: LoginDTO): ResponseEntity<ResponseDTO> {
        try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    request.userName,
                    request.password,
                ),
            )
            val user = userRepository.findUserByEmail(request.userName).get()
            val authClaims = listOf(if (user.isAdmin) "Admin" else "User")

            val token = Jwts.builder()
                .setSubject(user.email)
                .claim("auth", authClaims)
                .setExpiration(
                    Date(Date().time + 60 * 60 * 60 * 24 * securityProperties.expirationTime * 1000),
                )
                .signWith(
                    Keys.hmacShaKeyFor(securityProperties.secret.toByteArray()),
                    SignatureAlgorithm.HS512,
                )
                .compact()

            return ResponseEntity.ok()
                .body(
                    GenericSuccessResponseDTO(
                        status = "Success",
                        statusCode = 200,
                        message = "Authenticated",
                        data = LoginResponseDTO(
                            userId = user._id.toHexString(),
                            name = user.name,
                            role = if (user.isAdmin) "Admin" else "User",
                            token = token,
                            email = user.email,
                        ),
                    ),
                )
        } catch (e: IOException) {
            throw AuthenticationServiceException(e.message)
        } catch (e: DisabledException) {
            throw Exception("USER_DISABLED", e)
        }
    }
}
