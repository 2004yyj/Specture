package kr.hs.dgsw.domain.entity.response

data class User(
    val userId: String,
    val password: String,
    val profile: String,
    val name: String,
    val grade: Int,
    val klass: Int,
    val number: Int,
    val introduce: String,
    val field: List<String>,
    val attachmentUrl: List<String>
)