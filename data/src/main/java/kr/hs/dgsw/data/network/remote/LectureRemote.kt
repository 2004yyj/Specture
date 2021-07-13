package kr.hs.dgsw.data.network.remote

import io.reactivex.rxjava3.core.Single
import kr.hs.dgsw.data.base.BaseRemote
import kr.hs.dgsw.data.entity.LectureData
import kr.hs.dgsw.data.network.service.LectureService
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
}