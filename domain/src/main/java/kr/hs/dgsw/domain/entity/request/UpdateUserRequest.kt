package kr.hs.dgsw.domain.entity.request

import okhttp3.MultipartBody
import okhttp3.RequestBody

data class UpdateUserRequest(
    val userId: RequestBody,
    val password: RequestBody,
    val name: RequestBody,
    val grade: Int,
    val klass: Int,
    val number: Int,
    val introduce: RequestBody,
    val field: ArrayList<RequestBody>,
    val profile: MultipartBody.Part?
)