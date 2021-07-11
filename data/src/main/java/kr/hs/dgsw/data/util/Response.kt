package kr.hs.dgsw.data.util

data class Response<T>(
    val data: T,
    val status: Int,
    val message: String
)