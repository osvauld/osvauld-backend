package com.shadowsafe.secretsmanagerbackend.secret.service.impl

import SecretByUrlResponseDTOList
import com.shadowsafe.secretsmanagerbackend.folders.repo.FoldersRepository
import com.shadowsafe.secretsmanagerbackend.secret.dto.*
import com.shadowsafe.secretsmanagerbackend.secret.model.SecretsEntity
import com.shadowsafe.secretsmanagerbackend.secret.model.UrlSecretMappingEntity
import com.shadowsafe.secretsmanagerbackend.secret.repository.SecretsRepository
import com.shadowsafe.secretsmanagerbackend.secret.repository.UrlSecretMappingRepository
import com.shadowsafe.secretsmanagerbackend.secret.service.SecretsService
import com.shadowsafe.secretsmanagerbackend.shared.exception.GenericErrorCodes
import com.shadowsafe.secretsmanagerbackend.shared.exception.GenericException
import com.shadowsafe.secretsmanagerbackend.shared.util.SECRET_TYPE_AUTOFILL
import com.shadowsafe.secretsmanagerbackend.shared.util.SECRET_TYPE_OTHERS
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class SecretServiceImpl(

    private val secretsRepository: SecretsRepository,
    private val foldersRepository: FoldersRepository,
    private val urlSecretMappingRepository: UrlSecretMappingRepository,

) : SecretsService {

    override fun saveSecrets(request: SaveSecretsRequestDTO, userId: String): SecretsResponseDTO {
        if (!foldersRepository.existsById(request.parent)) {
            throw GenericException(GenericErrorCodes.FOLDER_NOT_FOUND)
        }

        var parentFolder = foldersRepository.findById(request.parent).get()
        var secretKeysList = arrayListOf<String>()
        val autofillKeyValues = listOf("username", "password", "url")
        var urlVal = "none"

        request.credentials?.forEach { cred ->
            secretKeysList.add(cred.fieldKey.lowercase())
            if (cred.fieldKey.lowercase() == "url") urlVal = cred.fieldValue.replace(Regex("/\$"), "")
        }

        val secretType = if (secretKeysList.containsAll(autofillKeyValues)) {
            SECRET_TYPE_AUTOFILL
        } else {
            SECRET_TYPE_OTHERS
        }

        val secretsEntity = secretsRepository.save(
            SecretsEntity(
                name = request.name,
                credentials = request.credentials!!,
                parent = parentFolder.parents.plus(request.parent),
                createdAt = LocalDateTime.now(),
                description = request.description,
                updatedAt = LocalDateTime.now(),
                type = secretType,
            ),
        )

        if (secretType == SECRET_TYPE_AUTOFILL) {
            if (urlSecretMappingRepository.findByUrlAndUserId(urlVal, userId).isEmpty) {
                urlSecretMappingRepository.save(
                    UrlSecretMappingEntity(
                        userId = userId,
                        secretIds = listOf(secretsEntity._id.toHexString()),
                        url = urlVal,
                    ),
                )
            } else {
                val urlEntity = urlSecretMappingRepository.findByUrlAndUserId(urlVal, userId).get()
                urlEntity.secretIds += secretsEntity._id.toHexString()
                urlSecretMappingRepository.save(urlEntity)
            }
        }

        parentFolder.secrets = parentFolder.secrets.plus(secretsEntity._id.toHexString())
        foldersRepository.save(parentFolder)

        return SecretsResponseDTO(
            id = secretsEntity._id.toHexString(),
            name = secretsEntity.name,
            credentials = secretsEntity.credentials,
            parent = secretsEntity.parent.last(),
            description = secretsEntity.description,
            createdAt = secretsEntity.createdAt,
            updatedAt = secretsEntity.updatedAt,
        )
    }

    override fun getSecretsByUrl(request: String, userId: String): SecretByUrlResponseDTOList {
        val secretsByUrlResponseDTO = SecretByUrlResponseDTOList()
        val urlEntity = urlSecretMappingRepository.findByUrlAndUserId(request.replace(Regex("/\$"), ""), userId)
        if (urlEntity.isEmpty) return SecretByUrlResponseDTOList()
        urlEntity.get().secretIds.forEach { secretId ->

            val secret = secretsRepository.findById(secretId).get()

            val secretByUrlResponseDTO = SecretByUrlResponseDTO(
                secretId = secret._id.toHexString(),
                username = secret.credentials.single {
                    it.fieldKey.lowercase() == "username"
                }.fieldValue,

            )
            secretsByUrlResponseDTO.secrets += secretByUrlResponseDTO
        }
        return secretsByUrlResponseDTO
    }

    override fun getSecretById(request: String): SecretByIdResponseDTO {
        if (secretsRepository.findById(request).isEmpty) {
            throw GenericException(GenericErrorCodes.SECRET_NOT_FOUND)
        }
        val secret = secretsRepository.findById(request).get()

        val secretByIdResponseDTO = SecretByIdResponseDTO(
            username = secret.credentials.filter {
                it.fieldKey.lowercase() == "username"
            }.map { res -> res.fieldValue }.first().toString(),
            password = secret.credentials.filter {
                it.fieldKey.lowercase() == "password"
            }.map { res -> res.fieldValue }.first().toString(),
        )
        return secretByIdResponseDTO
    }

    override fun getAllUrls(): URLsListDTO {
        var urlSecrets = urlSecretMappingRepository.findAll()

        var urls = urlSecrets.map {
            it.url
        }
        return URLsListDTO(
            urls = urls,
        )
    }
}
