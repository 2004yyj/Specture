package kr.hs.dgsw.data.datasource

import io.reactivex.rxjava3.core.Single
import kr.hs.dgsw.data.base.BaseDataSource
import kr.hs.dgsw.data.mapper.toEntity
import kr.hs.dgsw.data.network.remote.LectureRemote
import kr.hs.dgsw.domain.entity.response.Lecture
import javax.inject.Inject

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
}