package com.eunmin.invitation.web.res

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant

@JsonIgnoreProperties(ignoreUnknown = true)
data class KakaoProfileRes(
    @JsonProperty("id")
    val id: Long,
    @JsonProperty("connected_at")
    val connectedAt: Instant,
    @JsonProperty("properties")
    val properties: Properties?,
    @JsonProperty("kakao_account")
    val kakaoAccount: KakaoAccount?
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Properties(
    @JsonProperty("nickname")
    val nickname: String?
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class KakaoAccount(
    @JsonProperty("profile")
    val profile: Profile?
)

data class Profile(
    @JsonProperty("profile_nickname_needs_agreement")
    val profileNicknameNeedsAgreement: Boolean?,
    @JsonProperty("nickname")
    val nickname: String?,
    @JsonProperty("is_default_nickname")
    val isDefaultNickname: Boolean?
)