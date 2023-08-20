package com.shadowsafe.secretsmanagerbackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SecretsManagerBackendApplication

fun main(args: Array<String>) {
	runApplication<SecretsManagerBackendApplication>(*args)
}
