package it.polito.wa2.service1

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun filterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        httpSecurity
            .authorizeHttpRequests { auth ->
                auth.requestMatchers("/").permitAll()
                auth.requestMatchers("/method2").permitAll()
                auth.anyRequest().authenticated()
            }
            .oauth2ResourceServer { it.jwt {  } }
            .sessionManagement { it.sessionCreationPolicy( SessionCreationPolicy.STATELESS) }
            .csrf { it.disable() }
            .cors { it.disable() }
        return httpSecurity.build()
    }
}