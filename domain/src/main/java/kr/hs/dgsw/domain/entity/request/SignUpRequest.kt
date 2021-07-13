package kr.hs.dgsw.domain.entity.request

import okhttp3.MultipartBody

data class SignUpRequest(
    val userId: String,
    val password: String,
    val profile: MultipartBody.Part?,
    val name: String,
    val grade: Int,
    val klass: Int,
    val number: Int,
    val info: String,
    val field: String
)