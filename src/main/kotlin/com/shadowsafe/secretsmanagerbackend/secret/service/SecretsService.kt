package com.shadowsafe.secretsmanagerbackend.secret.service

import com.shadowsafe.secretsmanagerbackend.secret.dto.SaveSecretsRequestDTO
import com.shadowsafe.secretsmanagerbackend.secret.dto.SecretsResponseDTO
import org.springframework.stereotype.Service

@Service
interface SecretsService {
    fun saveSecrets(request: SaveSecretsRequestDTO) : SecretsResponseDTO

}
