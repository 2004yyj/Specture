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
        return lectureDataSource.getAllLecture(state)
    }

    override fun getLectureDetail(lectureId: Int): Single<Lecture> {
        return lectureDataSource.getLectureDetail(lectureId)
    }

    override fun getAllLectureByDate(year: Int, month: Int, day: Int): Single<List<Lecture>> {
        return lectureDataSource.getAllLectureByDate(year, month, day)
    }

    override fun getAllLectureByUserId(userId: String): Single<List<Lecture>> {
        return lectureDataSource.getAllLectureByUserId(userId)
    }

    override fun postLectureProposal(lectureId: Int): Single<String> {
        return lectureDataSource.postLectureProposal(lectureId)
    }

    override fun getAllLectureProposalByUserId(userId: String): Single<List<Lecture>> {
        return lectureDataSource.getAllLectureProposalByUserId(userId)
    }

    override fun postLecture(
        title: RequestBody,
        content: RequestBody,
        attachment: List<MultipartBody.Part>?,
        field: ArrayList<RequestBody>,
        start_date: Long,
        end_date: Long,
        proposal: Long
    ): Single<String> {
        return lectureDataSource.postLecture(title, content, attachment, field, start_date, end_date, proposal)
    }
}