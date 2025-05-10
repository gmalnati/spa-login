package it.polito.wa2.service1

import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class Service1Controller {

    @GetMapping("")
    fun home(): String {
        return "Hello from Service 1"
    }

    @GetMapping("method1")
    fun service1(authentication: Authentication): Map<String, Any> {
        return mapOf(
            "message" to "Hello from Service 1",
            "user" to (authentication.principal) as Jwt,
            "authorities" to authentication.authorities.map { it.authority }
        )
    }

}