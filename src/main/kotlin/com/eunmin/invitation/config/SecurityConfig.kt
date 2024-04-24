package com.eunmin.invitation.config

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import org.springframework.security.web.csrf.CsrfToken
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler
import org.springframework.web.filter.OncePerRequestFilter
import java.beans.Customizer
import java.io.IOException


//https://jaykaybaek.tistory.com/36
//@EnableMethodSecurity
//@EnableWebSecurity
@Configuration
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter
) {

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        val requestHandler = CsrfTokenRequestAttributeHandler()
        requestHandler.setCsrfRequestAttributeName("_csrf")

        http.csrf { csrf ->
            csrf.csrfTokenRequestHandler(requestHandler)
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringRequestMatchers("/", "/login/**", "/logout/**", "/demo/**")
        }.authorizeHttpRequests { request ->
            request.requestMatchers(
                PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .anyRequest().permitAll()
        }.addFilterAfter(CsrfCookieFilter(), BasicAuthenticationFilter::class.java)

        return http.build()
    }
}

class CsrfCookieFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val csrfToken: CsrfToken = request.getAttribute(CsrfToken::class.java.name) as CsrfToken
        if (csrfToken.headerName != null) {
            response.setHeader(csrfToken.headerName, csrfToken.token)
        }
        filterChain.doFilter(request, response)
    }
}
