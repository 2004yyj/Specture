package kr.hs.dgsw.domain.entity.request

import okhttp3.MultipartBody

data class LoginRequest(
    val userId: String,
    val password: String
)