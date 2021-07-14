package kr.hs.dgsw.data.network.service

import io.reactivex.rxjava3.core.Single
import kr.hs.dgsw.data.entity.TokenData
import kr.hs.dgsw.data.entity.UserData
import kr.hs.dgsw.data.util.Response
import kr.hs.dgsw.domain.entity.request.LoginRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface UserService {
    @Multipart
    @POST("/user/signUp")
    fun postSignUp(
        @Part("userId") userId: RequestBody,
        @Part("password") password: RequestBody,
        @Part("name") name: RequestBody,
        @Part("grade") grade: Int,
        @Part("klass") klass: Int,
        @Part("number") number: Int,
        @Part("introduce") introduce: RequestBody,
        @Part("field") field: RequestBody,
        @Part profile: MultipartBody.Part?
    ) : Single<retrofit2.Response<Response<TokenData>>>

    @POST("/user/login")
    fun postLogin(@Body loginRequest: LoginRequest) : Single<retrofit2.Response<Response<TokenData>>>

    @POST("/user/autoLogin")
    fun postAutoLogin() : Single<retrofit2.Response<Response<TokenData>>>

    @GET("/user")
    fun getUser() : Single<retrofit2.Response<Response<UserData>>>
}