package com.shadowsafe.secretsmanagerbackend.shared.exception

open class GenericException(
    open val errorCode: GenericErrorCodes,
    open val args: Array<String>? = null,
) : RuntimeException(errorCode.defaultMessage)
