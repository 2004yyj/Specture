package kr.hs.dgsw.data.network.service

import io.reactivex.rxjava3.core.Single
import kr.hs.dgsw.data.entity.UserData
import kr.hs.dgsw.data.util.Response
import kr.hs.dgsw.domain.entity.request.UpdateUserRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface UserService {
    @GET("/user")
    fun getUser() : Single<retrofit2.Response<Response<UserData>>>

    @Multipart
    @PUT("/user")
    fun putUser(
        @Part("userId") userId: RequestBody,
        @Part("password") password: RequestBody,
        @Part("name") name: RequestBody,
        @Part("grade") grade: Int,
        @Part("klass") klass: Int,
        @Part("number") number: Int,
        @Part("introduce") introduce: RequestBody,
        @Part("field") field: ArrayList<RequestBody>,
        @Part profile: MultipartBody.Part?
    ): Single<retrofit2.Response<Response<Any?>>>
}