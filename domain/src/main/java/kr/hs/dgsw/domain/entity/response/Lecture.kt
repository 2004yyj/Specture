package kr.hs.dgsw.domain.entity.response

data class Lecture(
    val lectureId: Int,
    val title: String?,
    val content: String?,
    val userId: String?,
    val field: List<String>?,
    val startDate: Long,
    val endDate: Long,
    val uploadDate: Long,
    val proposal: Long,
    val state: Int
)
