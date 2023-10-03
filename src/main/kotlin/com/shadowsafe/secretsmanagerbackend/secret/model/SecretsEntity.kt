package com.shadowsafe.secretsmanagerbackend.secret.model

import com.shadowsafe.secretsmanagerbackend.shared.IDTO
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document("secrets")
data class SecretsEntity(
    @Id
    val _id: ObjectId = ObjectId.get(),
    var name: String?,
    var credentials: List<CredentialsEntity>,
    var parent: List<String>,
    var description: String,
    var type: String,
    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime
) : IDTO
