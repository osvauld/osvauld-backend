package com.shadowsafe.secretsmanagerbackend.health

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckController {
    @GetMapping("/ping")
    fun ping(): String {
        return "Your secrets are safe in the shadow"
    }
}
