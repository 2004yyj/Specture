package kr.hs.dgsw.data.datasource

import io.reactivex.rxjava3.core.Single
import kr.hs.dgsw.data.base.BaseDataSource
import kr.hs.dgsw.data.mapper.toEntity
import kr.hs.dgsw.data.network.remote.LectureRemote
import kr.hs.dgsw.domain.entity.response.Lecture
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class LectureDataSource @Inject constructor(
    override val remote: LectureRemote
): BaseDataSource<LectureRemote>() {
    fun getAllClass(state: Int): Single<List<Lecture>> =
        remote.getAllClass().map { lectureDataList ->
            val lectureList = ArrayList<Lecture>()

            lectureDataList.forEach {
                if (it.state == state) {
                    lectureList.add(it.toEntity())
                }
            }
            lectureList
        }


    fun getLectureByDate(year: Int, month: Int, day: Int): Single<List<Lecture>> =
        remote.getAllClass().map { lectureDataList ->
            val lectureList = ArrayList<Lecture>()

            val sdf = SimpleDateFormat("yyyyMMdd", Locale.KOREA)
            val curDate = sdf.parse(year.toString()+String.format("%02d", month)+String.format("%02d", day))!!.time

            lectureDataList.forEach {
                val strDate = sdf.parse(sdf.format(Date(it.startDate)))!!.time
                val endDate = sdf.parse(sdf.format(Date(it.endDate)))!!.time

                if (curDate in strDate..endDate) {
                    lectureList.add(it.toEntity())
                }
            }
            lectureList
        }

    fun getLectureDetail(lectureId: Int): Single<Lecture> {
        return remote.getLectureDetail(lectureId).map { it.toEntity() }
    }

    fun postLectureProposal(lectureId: Int): Single<Any?> {
        return remote.postLectureProposal(lectureId)
    }

}