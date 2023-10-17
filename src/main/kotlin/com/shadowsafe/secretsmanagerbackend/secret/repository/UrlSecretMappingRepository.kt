package com.shadowsafe.secretsmanagerbackend.secret.repository

import com.shadowsafe.secretsmanagerbackend.secret.model.UrlSecretMappingEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import java.util.*

interface UrlSecretMappingRepository : MongoRepository<UrlSecretMappingEntity, String> {

    @Query("{ url: ?0 , userId: ?1 }")
    fun findByUrlAndUserId(url: String, userId: String): Optional<UrlSecretMappingEntity>
}
