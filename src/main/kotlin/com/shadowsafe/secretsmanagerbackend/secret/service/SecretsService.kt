package com.shadowsafe.secretsmanagerbackend.secret.service

import com.shadowsafe.secretsmanagerbackend.secret.dto.GetSecretsResponseDTO
import com.shadowsafe.secretsmanagerbackend.secret.dto.SecretsResponseDTO
import org.springframework.stereotype.Service

@Service
interface SecretsService {
    fun getAllSecrets(pageNo: Int, pageSize: Int, search: String): GetSecretsResponseDTO

    fun getSecretDetails(id: String): SecretsResponseDTO
}