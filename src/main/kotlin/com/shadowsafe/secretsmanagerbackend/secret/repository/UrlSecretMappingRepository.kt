package com.shadowsafe.secretsmanagerbackend.secret.repository

import com.shadowsafe.secretsmanagerbackend.secret.model.UrlSecretMappingEntity
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface UrlSecretMappingRepository : MongoRepository<UrlSecretMappingEntity, String> {

    fun findByUrl(url: String): Optional<UrlSecretMappingEntity>
}
