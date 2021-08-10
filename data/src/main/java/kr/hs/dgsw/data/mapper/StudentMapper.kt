package kr.hs.dgsw.data.mapper

import kr.hs.dgsw.data.entity.StudentData
import kr.hs.dgsw.domain.entity.response.Student

fun StudentData.toEntity(): Student {
    return Student(
        this.lectureId,
        this.name,
        this.userId
    )
}

fun Student.toData(): StudentData {
    return StudentData(
        this.lectureId,
        this.name,
        this.userId
    )
}