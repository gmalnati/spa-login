package it.polito.wa2.spalogin

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@EnableWebSecurity
@Configuration
class SecurityConfig {
    @Bean
    fun securityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        httpSecurity
            .authorizeHttpRequests {
                it.requestMatchers("/serverLogin").authenticated()
                it.anyRequest().permitAll()
            }
            .oauth2Login {
                it.successHandler { _, response, _ -> response.sendRedirect("/ui")}
                it.failureUrl("/ui?error=login")
            }
            .csrf {
                it.ignoringRequestMatchers("/logout")
            }
            .logout {
                it.clearAuthentication(true)
                it.invalidateHttpSession(true)
                it.logoutSuccessHandler { _, response, _ -> response.sendRedirect("/ui?logout") }
            }

        return httpSecurity.build()
    }
}