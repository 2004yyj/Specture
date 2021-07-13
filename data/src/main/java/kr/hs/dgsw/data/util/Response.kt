package kr.hs.dgsw.data.util

data class Response<T>(
    val data: T,
    val message: String
)