package kr.hs.dgsw.data.network.service

import io.reactivex.rxjava3.core.Single
import kr.hs.dgsw.data.entity.LectureData
import kr.hs.dgsw.data.util.Response
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface LectureService {
    @GET("/lecture")
    fun getAllLecture(@Query("state") state: Int): Single<retrofit2.Response<Response<List<LectureData>>>>

    @GET("/lecture")
    fun getAllLectureByDate(
        @Query("year") year: Int,
        @Query("month") month: Int,
        @Query("day") day: Int
    ): Single<retrofit2.Response<Response<List<LectureData>>>>

    @GET("/lecture")
    fun getAllLectureByUserId(
        @Query("userId") userId: String
    ): Single<retrofit2.Response<Response<List<LectureData>>>>

    @GET("/lecture/{lectureId}")
    fun getLectureDetail(@Path("lectureId") lectureId: Int): Single<retrofit2.Response<Response<LectureData>>>

    @Multipart
    @POST("/lecture")
    fun postLecture(
        @Part("title") title: RequestBody,
        @Part("content") content: RequestBody,
        @Part  attachment: List<MultipartBody.Part>?,
        @Part("field") field: ArrayList<RequestBody>,
        @Part("start_date") start_date: Long,
        @Part("end_date") end_date: Long,
        @Part("proposal") proposal: Long
    ): Single<retrofit2.Response<Response<Any?>>>

    @POST("/lecture/{lectureId}/proposal")
    fun postLectureProposal(@Path("lectureId") lectureId: Int): Single<retrofit2.Response<Response<Any?>>>

    @GET("/lecture/proposal")
    fun getAllLectureProposalByUserId(@Query("userId") userId: String): Single<retrofit2.Response<Response<List<LectureData>>>>
}