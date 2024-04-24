package com.eunmin.invitation.config

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component
import java.sql.Timestamp
import java.time.Instant
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*

@PropertySource("classpath:jwt.properties")
@Component
class TokenProvider(
    @Value("\${secret-key}")
    private val secretKey: String,
    @Value("\${expiration-hours}")
    private val expirationHours: Long,
    @Value("\${issuer}")
    private val issuer: String
) {
    //https://stackabuse.com/spring-security-in-memory-invalidation-of-jwt-token-during-user-logout/
    //logout
    fun createToken(userSpecification: String): String = Jwts.builder()
        .signWith(SignatureAlgorithm.HS512, secretKey.toByteArray())
        .setSubject(userSpecification)   // JWT 토큰 제목
        .setIssuer(issuer)    // JWT 토큰 발급자
        .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))    // JWT 토큰 발급 시간
        .setExpiration(Date.from(Instant.now().plus(expirationHours, ChronoUnit.HOURS)))    // JWT 토큰의 만료시간 설정
        .compact()!!

    fun validateTokenAndGetSubject(token: String): String? = Jwts.parser()
        .setSigningKey(secretKey.toByteArray())
        .parseClaimsJws(token)
        .body
        .subject
}