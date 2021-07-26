package kr.hs.dgsw.data.datasource

import io.reactivex.rxjava3.core.Single
import kr.hs.dgsw.data.base.BaseDataSource
import kr.hs.dgsw.data.mapper.toEntity
import kr.hs.dgsw.data.network.remote.LectureRemote
import kr.hs.dgsw.domain.entity.response.Lecture
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class LectureDataSource @Inject constructor(
    override val remote: LectureRemote
): BaseDataSource<LectureRemote>() {
    fun getAllLecture(state: Int): Single<List<Lecture>> =
        remote.getAllLecture(state).map { lectureDataList ->
            val lectureList = ArrayList<Lecture>()
            lectureDataList.forEach {
                lectureList.add(it.toEntity())
            }
            lectureList
        }


    fun getAllLectureByDate(year: Int, month: Int, day: Int): Single<List<Lecture>> =
        remote.getAllLectureByDate(year, month, day).map { lectureDataList ->
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

    fun getAllLectureByUserId(userId: String): Single<List<Lecture>> =
        remote.getAllLectureByUserId(userId).map {  lectureDataList ->
            lectureDataList.map {
                it.toEntity()
            }
        }

    fun getLectureDetail(lectureId: Int): Single<Lecture> {
        return remote.getLectureDetail(lectureId).map { it.toEntity() }
    }

    fun postLectureProposal(lectureId: Int): Single<Any?> {
        return remote.postLectureProposal(lectureId)
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
        return remote.postLecture(title, content, attachment, field, start_date, end_date, proposal)
    }

}