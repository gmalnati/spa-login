package it.polito.wa2.spalogin.controllers

import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MeController {
    @GetMapping("/api/me")
    fun me(authentication: Authentication?): Map<String, Any> {
        if (authentication!=null) {
            return mapOf(
                "name" to (authentication.principal as OidcUser).preferredUsername,
                "authorities" to authentication.authorities.map { it.authority }
            )
        } else {
            return mapOf("error" to "User not authenticated")
        }
    }
}