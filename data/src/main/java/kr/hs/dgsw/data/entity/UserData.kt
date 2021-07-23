package kr.hs.dgsw.data.entity

data class UserData(
    val userId: String,
    val name: String,
    val grade: Int,
    val klass: Int,
    val number: Int,
    val introduce: String,
    val field: List<String>,
    val attachmentUrl: String
)
