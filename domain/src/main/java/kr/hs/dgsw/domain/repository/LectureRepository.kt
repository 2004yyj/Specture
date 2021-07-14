package kr.hs.dgsw.domain.repository

import io.reactivex.rxjava3.core.Single
import kr.hs.dgsw.domain.entity.response.Lecture

interface LectureRepository {
    fun getAllLecture(state: Int): Single<List<Lecture>>

    fun getLectureDetail(lectureId: Int): Single<Lecture>

    fun getAllLectureByDate(year: Int, month: Int, day: Int): Single<List<Lecture>>
}