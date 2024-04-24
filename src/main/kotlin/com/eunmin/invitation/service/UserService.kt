package com.eunmin.invitation.service

import com.eunmin.invitation.config.TokenProvider
import com.eunmin.invitation.domain.User
import com.eunmin.invitation.domain.UserRepository
import com.eunmin.invitation.external.KakaoAdapter
import com.eunmin.invitation.web.res.KakaoProfileRes
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.servlet.mvc.support.RedirectAttributes

//https://jaykaybaek.tistory.com/36
@Service
class UserService(
    val kakaoAdapter: KakaoAdapter,
    val userRepository: UserRepository,
    val tokenProvider: TokenProvider
) {

    @Transactional
    fun login(code: String) : String=
        with(kakaoAdapter.getToken(code)) {
            val user = getOrCreateUser(kakaoAdapter.getProfile(this))
            tokenProvider.createToken(user.name)
        }


    private fun getOrCreateUser(profile: KakaoProfileRes): User {
        val user = userRepository.findBySnsId(profile.id.toString())

        return if (user == null) {
            LOGGER.info { "new user creation : ${profile.id} / ${profile.properties?.nickname}" }
            userRepository.save(User.createUser(profile))
        } else {
            user.updateLastAccessedOn()
        }
    }


    companion object {
        val LOGGER = KotlinLogging.logger { }
    }

}