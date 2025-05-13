package it.polito.wa2.spalogin

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.web.SecurityFilterChain

@EnableWebSecurity
@Configuration
class SecurityConfig(val crr: ClientRegistrationRepository) {



    fun oidcLogoutSuccessHandler() = OidcClientInitiatedLogoutSuccessHandler(crr).also {
        it.setPostLogoutRedirectUri("{baseUrl}/ui?logout")
    }

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
                //it.disable()
                //it.ignoringRequestMatchers("/logout")
            }
            .logout {
                it.logoutSuccessHandler(oidcLogoutSuccessHandler())
            }

        return httpSecurity.build()
    }
}