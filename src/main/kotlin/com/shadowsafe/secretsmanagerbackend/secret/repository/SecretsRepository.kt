package com.shadowsafe.secretsmanagerbackend.secret.repository

import com.shadowsafe.secretsmanagerbackend.secret.model.SecretsEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

interface SecretsRepository : MongoRepository<SecretsEntity, String> {
}
