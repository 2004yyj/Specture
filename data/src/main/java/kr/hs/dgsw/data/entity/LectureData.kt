package kr.hs.dgsw.data.entity

data class LectureData(
    val lectureId: Int,
    val title: String?,
    val content: String?,
    val attachmentUrl: List<String>?,
    val userId: String?,
    val field: List<String>?,
    val startDate: Long,
    val endDate: Long,
    val uploadDate: Long,
    val proposal: Long,
    val state: Int
)
