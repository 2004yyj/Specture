package kr.hs.dgsw.data.network.remote

import io.reactivex.rxjava3.core.Single
import kr.hs.dgsw.data.base.BaseRemote
import kr.hs.dgsw.data.entity.LectureData
import kr.hs.dgsw.data.network.service.LectureService
import kr.hs.dgsw.data.util.Response
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class LectureRemote @Inject constructor(
    override val service: LectureService
): BaseRemote<LectureService>() {

    fun getAllClass(): Single<List<LectureData>> {
        return service.getAllClass().map(getResponse())
    }

    fun getLectureDetail(lectureId: Int): Single<LectureData> {
        return service.getLectureDetail(lectureId).map(getResponse())
    }

    fun postLectureProposal(lectureId: Int): Single<Any?> {
        return service.postLectureProposal(lectureId).map(getResponse())
    }

    fun postLecture(
        title: RequestBody,
        content: RequestBody,
        attachment: ArrayList<MultipartBody.Part>,
        field: RequestBody,
        start_date: Long,
        end_date: Long,
        proposal: Long
    ): Single<Any?> {
        return service.postLecture(title, content, attachment, field, start_date, end_date, proposal).map(getResponse())
    }
}