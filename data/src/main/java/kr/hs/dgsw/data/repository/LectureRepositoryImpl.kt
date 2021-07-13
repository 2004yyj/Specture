package kr.hs.dgsw.data.repository

import io.reactivex.rxjava3.core.Single
import kr.hs.dgsw.data.datasource.LectureDataSource
import kr.hs.dgsw.domain.entity.response.Lecture
import kr.hs.dgsw.domain.repository.LectureRepository

class LectureRepositoryImpl(
    private val lectureDataSource: LectureDataSource
): LectureRepository {
    override fun getAllLecture(): Single<Lecture> {
        return lectureDataSource.getAllClass()
    }

}