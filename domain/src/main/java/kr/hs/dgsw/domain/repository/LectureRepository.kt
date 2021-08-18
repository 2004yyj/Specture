package kr.hs.dgsw.domain.repository

import io.reactivex.rxjava3.core.Single
import kr.hs.dgsw.domain.entity.response.Lecture
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface LectureRepository {
    fun getAllLecture(state: Int): Single<List<Lecture>>

    fun getLectureDetail(lectureId: Int): Single<Lecture>

    fun getAllLectureByDate(year: Int, month: Int, day: Int): Single<List<Lecture>>

    fun getAllLectureByUserId(userId: String): Single<List<Lecture>>

    fun postLectureProposal(lectureId: Int): Single<String>

    fun getAllLectureProposalByUserId(userId: String): Single<List<Lecture>>

    fun postLecture(
        title: RequestBody,
        content: RequestBody,
        attachment: ArrayList<MultipartBody.Part>,
        field: ArrayList<RequestBody>,
        start_date: Long,
        end_date: Long,
        proposal: Long
    ): Single<String>
}