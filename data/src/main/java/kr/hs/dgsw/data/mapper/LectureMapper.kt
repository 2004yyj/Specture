package kr.hs.dgsw.data.mapper

import kr.hs.dgsw.data.entity.LectureData
import kr.hs.dgsw.domain.entity.response.Lecture

fun LectureData.toEntity(): Lecture {
    return Lecture(
        this.lectureId,
        this.title,
        this.content,
        this.attachmentUrl,
        this.name,
        this.userId,
        this.field,
        this.startDate,
        this.endDate,
        this.uploadDate,
        this.proposal,
        this.state,
    )
}

fun Lecture.toData(): LectureData {
    return LectureData(
        this.lectureId,
        this.title,
        this.content,
        this.attachmentUrl,
        this.name,
        this.userId,
        this.field,
        this.startDate,
        this.endDate,
        this.uploadDate,
        this.proposal,
        this.state,
    )
}