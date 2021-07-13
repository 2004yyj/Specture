package kr.hs.dgsw.data.repository

import io.reactivex.rxjava3.core.Single
import kr.hs.dgsw.data.datasource.LectureDataSource
import kr.hs.dgsw.domain.entity.response.Lecture
import kr.hs.dgsw.domain.repository.LectureRepository

class LectureRepositoryImpl(
    private val lectureDataSource: LectureDataSource
): LectureRepository {
    override fun getAllLecture(state: Int): Single<List<Lecture>> {
        return lectureDataSource.getAllClass(state)
    }

    override fun getLectureDetail(lectureId: Int): Single<Lecture> {
        return lectureDataSource.getLectureDetail(lectureId)
    }

    override fun getAllLectureByDate(year: Int, month: Int, day: Int): Single<List<Lecture>> {
        return lectureDataSource.getLectureByDate(year, month, day)
    }

}