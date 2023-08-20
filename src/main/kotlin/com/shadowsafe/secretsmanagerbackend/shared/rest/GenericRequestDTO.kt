package com.shadowsafe.secretsmanagerbackend.shared.rest

data class GenericRequestDTO<DataClass>(
    override val requestId: String,
//  @get:Valid
    override val data: DataClass,
) : RequestDTO<DataClass>
