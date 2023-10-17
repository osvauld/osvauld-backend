package com.shadowsafe.secretsmanagerbackend.secret.model

import com.shadowsafe.secretsmanagerbackend.shared.IDTO
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("url_secrets")
data class UrlSecretMappingEntity(
    @Id
    val _id: ObjectId = ObjectId.get(),
    var secretIds: List<String>,
    var userId: String,
    val url: String,
) : IDTO
