package kr.hs.dgsw.domain.entity.request

data class LoginRequest(
    val userId: String,
    val password: String
)