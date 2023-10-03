package com.shadowsafe.secretsmanagerbackend.secret.service

import SecretByUrlResponseDTOList
import com.shadowsafe.secretsmanagerbackend.secret.dto.SaveSecretsRequestDTO
import com.shadowsafe.secretsmanagerbackend.secret.dto.SecretsResponseDTO
import com.shadowsafe.secretsmanagerbackend.secret.dto.URLsListDTO
import org.springframework.stereotype.Service

@Service
interface SecretsService {
    fun saveSecrets(request: SaveSecretsRequestDTO): SecretsResponseDTO
    fun getSecretsByUrl(request: String): SecretByUrlResponseDTOList
    fun getAllUrls(): URLsListDTO
}
