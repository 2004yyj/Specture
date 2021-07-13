package kr.hs.dgsw.data.entity

data class UserData(
    val userId: String,
    val password: String,
    val profile: String,
    val name: String,
    val grade: Int,
    val klass: Int,
    val number: Int,
    val info: String,
    val field: String
)
