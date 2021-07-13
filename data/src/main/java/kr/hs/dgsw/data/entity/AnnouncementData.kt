package kr.hs.dgsw.data.entity

data class AnnouncementData(
    val announcementId: Int,
    val lectureId: Int,
    val content: String?,
    val title: String?,
    val attachmentUrl: String?,
    val userId: String?,
    val uploadDate: Long
)
