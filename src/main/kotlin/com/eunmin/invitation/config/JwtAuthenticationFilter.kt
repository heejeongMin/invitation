package com.eunmin.invitation.config

import com.eunmin.invitation.domain.UserRepository
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.WebAuthenticationDetails
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.security.Principal

@Component
class JwtAuthenticationFilter(
    private val tokenProvider: TokenProvider,
    private val userRepository: UserRepository
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
//        try {
//            val token = parseBearerToken(request)
//            val user = parseUserSpecification(token)
//            UsernamePasswordAuthenticationToken.authenticated(user, token, user.authorities)
//                .apply { details = WebAuthenticationDetails(request) }
//                .also { SecurityContextHolder.getContext().authentication = it }
//
//            if (request.method != "OPTIONS" && !isLoginOrLogout(request)){
//                userAccessCheck(user.username)
//            }
//
//        } catch (ex: Exception) {
//            LOGGER.warn { ex }
//            throw NotAuthenticatedException()
//        }

        filterChain.doFilter(request, response)
    }

    private fun isLoginOrLogout(request: HttpServletRequest) : Boolean {
        val isPathLoginOrLogout = request.requestURI.contains("/game/user/login") ||
                request.requestURI.contains("/game/user/logout")


       return isPathLoginOrLogout && request.method == "POST"
    }

//    private fun userAccessCheck(username: String) {
//        var user = userRepository.findByUsername(username)?: throw DataNotFoundException("user not found")
//        if(user.hasUserLoggedOut()) {
//            throw TokenExpiredException()
//        }
//    }

    private fun parseBearerToken(request: HttpServletRequest) = request.getHeader(HttpHeaders.AUTHORIZATION)
        .takeIf { it?.startsWith("Bearer ", true) ?: false }?.substring(7)

    private fun parseUserSpecification(token: String?) = (
            token?.takeIf { it.length >= 10 }
                ?.let { tokenProvider.validateTokenAndGetSubject(it) }
                ?: "anonymous:anonymous"
            ).split(":")
        .let { User(it[0], "", listOf(SimpleGrantedAuthority(it[1]))) }

    companion object {
        private val LOGGER = KotlinLogging.logger {  }
    }
}