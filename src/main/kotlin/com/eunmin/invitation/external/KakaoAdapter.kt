package com.eunmin.invitation.external

import com.eunmin.invitation.web.res.KakaoProfileRes
import com.eunmin.invitation.web.res.KakaoTokenRes
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import mu.KotlinLogging
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient

@Component
class KakaoAdapter(private val webClient : WebClient) {

    fun getToken(code: String) : KakaoTokenRes {
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        params.add("grant_type", "authorization_code")
        params.add("client_id", "dffccdd7bda4104d3214311ae335fd78")
        params.add("redirect_uri", "http://localhost:8080/login")
        params.add("code", code)

        val res = webClient.method(HttpMethod.POST)
            .uri("https://kauth.kakao.com/oauth/token")
            .headers { header ->
                header.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            }
            .body(BodyInserters.fromFormData(params))
            .retrieve()
            .bodyToMono(String::class.java)
            .block()

        LOGGER.info { res }
        return ObjectMapper().readValue(res, KakaoTokenRes::class.java)
    }

    fun getProfile(res : KakaoTokenRes) : KakaoProfileRes {
        val res = webClient.method(HttpMethod.POST)
            .uri("https://kapi.kakao.com/v2/user/me")
            .headers { header ->
                header.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                header.setBearerAuth(res.accessToken)
            }
            .retrieve()
            .bodyToMono(String::class.java)
            .block()

        LOGGER.info { res }
        return ObjectMapper().registerModule(JavaTimeModule()).readValue(res, KakaoProfileRes::class.java)
    }

    companion object {
        val LOGGER = KotlinLogging.logger {  }
    }
}