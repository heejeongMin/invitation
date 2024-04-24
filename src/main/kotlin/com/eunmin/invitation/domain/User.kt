package com.eunmin.invitation.domain

import com.eunmin.invitation.web.res.KakaoProfileRes
import jakarta.persistence.*
import java.time.Instant

@Entity(name = "users")
class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Enumerated(EnumType.STRING)
    val snsRoute: SNSRoute,
    val snsId: String?,
    val name: String,
    val email: String?,
    var isActive: Boolean,
    val createdOn: Instant,
    var lastAccessedOn: Instant
) {

    fun updateLastAccessedOn() : User {
        lastAccessedOn = Instant.now()
        return this
    }

    companion object {
        fun createUser(profile: KakaoProfileRes) : User {
            val now = Instant.now()

            return User(
                snsRoute = SNSRoute.KAKAO,
                snsId = profile.id.toString(),
                name = profile.properties!!.nickname!!,
                email = null,
                isActive = true,
                createdOn = now,
                lastAccessedOn = now
            )
        }
    }
}

enum class SNSRoute {
    KAKAO
}