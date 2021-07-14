package kr.hs.dgsw.data.repository

import io.reactivex.rxjava3.core.Single
import kr.hs.dgsw.data.datasource.LectureDataSource
import kr.hs.dgsw.domain.entity.response.Lecture
import kr.hs.dgsw.domain.repository.LectureRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

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

    override fun postLectureProposal(lectureId: Int): Single<Any?> {
        return lectureDataSource.postLectureProposal(lectureId)
    }

    override fun postLecture(
        title: RequestBody,
        content: RequestBody,
        attachment: ArrayList<MultipartBody.Part>,
        field: RequestBody,
        start_date: Long,
        end_date: Long,
        proposal: Long
    ): Single<Any?> {
        return lectureDataSource.postLecture(title, content, attachment, field, start_date, end_date, proposal)
    }
}