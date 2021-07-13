package kr.hs.dgsw.domain.repository

import io.reactivex.rxjava3.core.Single
import kr.hs.dgsw.domain.entity.response.Lecture

interface LectureRepository {
    fun getAllLecture(): Single<Lecture>
}