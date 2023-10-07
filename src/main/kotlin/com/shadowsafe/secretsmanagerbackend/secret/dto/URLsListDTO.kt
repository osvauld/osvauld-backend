package com.shadowsafe.secretsmanagerbackend.secret.dto

import com.shadowsafe.secretsmanagerbackend.shared.IDTO

data class URLsListDTO(
    var urls: List<String> = arrayListOf<String>(),
) : IDTO
