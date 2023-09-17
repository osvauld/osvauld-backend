package com.shadowsafe.secretsmanagerbackend.shared.rest

interface RequestDTO<DataClass> {
    val requestId: String?
    val data: DataClass
}
