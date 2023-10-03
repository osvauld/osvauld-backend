package com.shadowsafe.secretsmanagerbackend.secret.service.impl

import SecretByUrlResponseDTOList
import com.shadowsafe.secretsmanagerbackend.folders.repo.FoldersRepository
import com.shadowsafe.secretsmanagerbackend.secret.dto.SaveSecretsRequestDTO
import com.shadowsafe.secretsmanagerbackend.secret.dto.SecretByUrlResponseDTO
import com.shadowsafe.secretsmanagerbackend.secret.dto.SecretsResponseDTO
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
    private val urlSecretMappingRepository: UrlSecretMappingRepository

) : SecretsService {

    override fun saveSecrets(request: SaveSecretsRequestDTO): SecretsResponseDTO {

        val parentFolder = foldersRepository.findById(request.parent).get()
        var secretKeysList = arrayListOf<String>();
        val autofillKeyValues = listOf("username","password","url")
        var urlVal = "none"

        if (!foldersRepository.existsById(request.parent))
            throw GenericException(GenericErrorCodes.FOLDER_NOT_FOUND)


        request.credentials?.forEach { cred ->
            secretKeysList.add(cred.fieldKey.lowercase())
            if (cred.fieldKey.lowercase() == "url")  urlVal = cred.fieldValue
        }

        val secretType  = if (secretKeysList.containsAll(autofillKeyValues)) SECRET_TYPE_AUTOFILL
        else SECRET_TYPE_OTHERS

        val secretsEntity = secretsRepository.save(
            SecretsEntity(
                name = request.name!!,
                credentials = request.credentials!!,
                parent = parentFolder.parents.plus(request.parent),
                createdAt = LocalDateTime.now(),
                description = request.description,
                updatedAt = LocalDateTime.now(),
                type = secretType
            )
        )

        if (secretType == SECRET_TYPE_AUTOFILL) {
            if (urlSecretMappingRepository.findByUrl(urlVal).isEmpty){
                urlSecretMappingRepository.save(
                        UrlSecretMappingEntity(
                                secretIds = listOf(secretsEntity._id.toHexString()) ,
                                url = urlVal
                        )
                )
            }
            else{
                val urlEntity = urlSecretMappingRepository.findByUrl(urlVal).get()
                urlEntity.secretIds += secretsEntity._id.toHexString()
                urlSecretMappingRepository.save(urlEntity)
            }
        }

        parentFolder.secrets.add(secretsEntity._id.toHexString())
        foldersRepository.save(parentFolder)

        return SecretsResponseDTO(
                name = secretsEntity.name,
                credentials = secretsEntity.credentials,
                parent = secretsEntity.parent.last(),
                description = secretsEntity.description,
                createdAt = secretsEntity.createdAt,
                updatedAt = secretsEntity.updatedAt
        )
    }

    override fun getSecretsByUrl(request: String): SecretByUrlResponseDTOList {

        val secretsByUrlResponseDTO = SecretByUrlResponseDTOList()
        val urlEntity = urlSecretMappingRepository.findByUrl(request).get()

        urlEntity.secretIds.forEach { secretId ->

            var secret = secretsRepository.findById(secretId).get()
            var secretByUrlResponseDTO = SecretByUrlResponseDTO(
                username = secret.credentials.stream().filter{
                    it.fieldKey.lowercase() == "username"
                }.map { return@map it.fieldValue }.toString()
            )
            secretsByUrlResponseDTO.secrets += secretByUrlResponseDTO
        }
        return secretsByUrlResponseDTO

    }


}
