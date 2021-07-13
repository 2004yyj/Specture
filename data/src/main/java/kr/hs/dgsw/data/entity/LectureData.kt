package kr.hs.dgsw.data.entity

data class LectureData(
    val lectureId: Int,
    val title: String?,
    val content: String?,
    val userId: String?,
    val field: String?,
    val startDate: Long,
    val endDate: Long,
    val uploadDate: Long,
    val proposalEnd: Long
)
