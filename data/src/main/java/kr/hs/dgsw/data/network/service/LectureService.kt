package kr.hs.dgsw.data.network.service

import io.reactivex.rxjava3.core.Single
import kr.hs.dgsw.data.entity.LectureData
import kr.hs.dgsw.data.util.Response
import retrofit2.http.GET

interface LectureService {
    @GET("/lecture")
    fun getAllClass(): Single<retrofit2.Response<Response<LectureData>>>
}