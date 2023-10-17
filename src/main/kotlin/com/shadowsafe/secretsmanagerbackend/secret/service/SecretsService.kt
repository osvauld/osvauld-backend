package com.shadowsafe.secretsmanagerbackend.secret.service

import SecretByUrlResponseDTOList
import com.shadowsafe.secretsmanagerbackend.secret.dto.SaveSecretsRequestDTO
import com.shadowsafe.secretsmanagerbackend.secret.dto.SecretByIdResponseDTO
import com.shadowsafe.secretsmanagerbackend.secret.dto.SecretsResponseDTO
import com.shadowsafe.secretsmanagerbackend.secret.dto.URLsListDTO
import org.springframework.stereotype.Service

@Service
interface SecretsService {
    fun saveSecrets(request: SaveSecretsRequestDTO, userId: String): SecretsResponseDTO
    fun getSecretsByUrl(request: String, userId: String): SecretByUrlResponseDTOList
    fun getSecretById(request: String): SecretByIdResponseDTO
    fun getAllUrls(): URLsListDTO
}
