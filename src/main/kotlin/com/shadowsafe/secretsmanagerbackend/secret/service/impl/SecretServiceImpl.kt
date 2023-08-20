package com.shadowsafe.secretsmanagerbackend.secret.service.impl

import com.shadowsafe.secretsmanagerbackend.secret.dto.GetSecretsResponseDTO
import com.shadowsafe.secretsmanagerbackend.secret.dto.SaveSecretsRequestDTO
import com.shadowsafe.secretsmanagerbackend.secret.dto.SecretsResponseDTO
import com.shadowsafe.secretsmanagerbackend.secret.model.SecretsEntity
import com.shadowsafe.secretsmanagerbackend.secret.repository.SecretsRepository
import com.shadowsafe.secretsmanagerbackend.secret.service.SecretsService
import com.shadowsafe.secretsmanagerbackend.shared.exception.GenericErrorCodes
import com.shadowsafe.secretsmanagerbackend.shared.exception.GenericException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class SecretServiceImpl(
    private val secretsRepository: SecretsRepository,
) : SecretsService {
    override fun getAllSecrets(pageNo: Int, pageSize: Int, search: String): GetSecretsResponseDTO {
        val pageable: Pageable = PageRequest.of(pageNo, pageSize)
        var secretsList = listOf<SecretsResponseDTO>()
        var secrets: Page<SecretsEntity>
        if (search.isNullOrEmpty()) {
            secrets = secretsRepository.findAll(pageable)
            secretsList = secrets.content.map { item ->
                SecretsResponseDTO(
                    item._id.toHexString(),
                    item.username,
                    "*******",
                    item.description,
                    item.tags,
                )
            }
        } else {
            secrets = secretsRepository.findBySearchKeyContainingIgnoreCase(search, pageable)
            secretsList = secrets.content.map { item ->
                SecretsResponseDTO(
                    item._id.toHexString(),
                    item.username,
                    "*******",
                    item.description,
                    item.tags,
                )
            }
        }
        return GetSecretsResponseDTO(secretsList, pageNo, pageSize, secrets.totalPages)
    }

    override fun getSecretDetails(id: String): SecretsResponseDTO {
        val secret = secretsRepository.findById(id)
        if (secret.isEmpty) {
            throw GenericException(GenericErrorCodes.SECRET_NOT_FOUND)
        } else {
            val secretEntity = secret.get()
            return SecretsResponseDTO(
                secretEntity._id.toHexString(),
                secretEntity.username,
                secretEntity.password,
                secretEntity.description,
                secretEntity.tags,
            )
        }
    }

    override fun saveSecrets(request: SaveSecretsRequestDTO) {
        if (request.id.isNullOrEmpty()) {
            secretsRepository.save(
                SecretsEntity(
                    username = request.username,
                    password = request.password,
                    description = request.description,
                    tags = request.tags,
                    searchKey = "${request.username} ${request.description} ${request.tags.joinToString(" ")}",
                ),
            )
        } else {
            val secretOptional = secretsRepository.findById(request.id)
            if (secretOptional.isEmpty) throw GenericException(GenericErrorCodes.SECRET_NOT_FOUND)
            val secret = secretOptional.get()
            secret.username = request.username
            secret.password = request.password
            secret.description = request.description
            secret.tags = request.tags
            secret.searchKey = "${request.username} ${request.description} ${request.tags.joinToString(" ")}"
            secretsRepository.save(secret)
        }
    }
}
