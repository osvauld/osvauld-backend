package com.shadowsafe.secretsmanagerbackend.secret.service.impl

import com.shadowsafe.secretsmanagerbackend.folders.repo.FoldersRepository
import com.shadowsafe.secretsmanagerbackend.secret.dto.SaveSecretsRequestDTO
import com.shadowsafe.secretsmanagerbackend.secret.dto.SecretsResponseDTO
import com.shadowsafe.secretsmanagerbackend.secret.model.SecretsEntity
import com.shadowsafe.secretsmanagerbackend.secret.repository.SecretsRepository
import com.shadowsafe.secretsmanagerbackend.secret.service.SecretsService
import com.shadowsafe.secretsmanagerbackend.shared.exception.GenericErrorCodes
import com.shadowsafe.secretsmanagerbackend.shared.exception.GenericException
import org.springframework.stereotype.Service
import java.lang.Exception
import java.time.LocalDateTime

@Service
class SecretServiceImpl(

    private val secretsRepository: SecretsRepository,
    private val foldersRepository: FoldersRepository
) : SecretsService {

    override fun saveSecrets(request: SaveSecretsRequestDTO) : SecretsResponseDTO {

        var secretsEntity : SecretsEntity ;

        if(!foldersRepository.existsById(request.parent))
            throw GenericException(GenericErrorCodes.FOLDER_NOT_FOUND)

        var parentFolder = foldersRepository.findById(request.parent).get();

            secretsEntity = secretsRepository.save(
                    SecretsEntity(
                            name = request.name!!,
                            credentials = request.credentials!!,
                            parent = parentFolder.parents.plus(request.parent),
                            createdAt = LocalDateTime.now(),
                            description = request.description,
                            updatedAt = LocalDateTime.now()
                    )
            )

        parentFolder.secrets.add(secretsEntity._id.toHexString()) ;
        foldersRepository.save(parentFolder) ;

        return SecretsResponseDTO(
                name = secretsEntity.name,
                credentials = secretsEntity.credentials,
                parent = secretsEntity.parent.last(),
                description = secretsEntity.description,
                createdAt = secretsEntity.createdAt,
                updatedAt = secretsEntity.updatedAt
        )
    }

}
