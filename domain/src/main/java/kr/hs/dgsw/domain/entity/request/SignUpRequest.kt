package kr.hs.dgsw.domain.entity.request

import okhttp3.MultipartBody
import okhttp3.RequestBody

data class SignUpRequest(
    val userId: RequestBody,
    val password: RequestBody,
    val profile: MultipartBody.Part?,
    val name: RequestBody,
    val grade: Int,
    val klass: Int,
    val number: Int,
    val field: RequestBody,
    val introduce: RequestBody,
)