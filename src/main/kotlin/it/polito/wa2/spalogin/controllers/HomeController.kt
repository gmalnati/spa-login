package it.polito.wa2.spalogin.controllers

import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HomeController {

    @GetMapping("/")
    fun home(httpServletResponse: HttpServletResponse) {
        httpServletResponse.sendRedirect("/ui")
    }

    @GetMapping("/serverLogin")
    fun login() {
    }

    @GetMapping("/me")
    fun me(authentication: Authentication?): Map<String, Any> {
        if (authentication!=null) {
            val user = authentication.principal as OidcUser
            return mapOf(
                "name" to user.preferredUsername,
                "userInfo" to user.userInfo,
            )
        } else {
            return mapOf("error" to "User not authenticated")
        }
    }
}