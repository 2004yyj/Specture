package kr.hs.dgsw.data.network.service

import io.reactivex.rxjava3.core.Single
import kr.hs.dgsw.data.entity.TokenData
import kr.hs.dgsw.data.util.Response
import kr.hs.dgsw.domain.entity.request.LoginRequest
import kr.hs.dgsw.domain.entity.request.SignUpRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("/user/signUp")
    fun postSignUp(@Body signUpRequest: SignUpRequest) : Single<retrofit2.Response<Response<TokenData>>>

    @POST("/user/login")
    fun postLogin(@Body loginRequest: LoginRequest) : Single<retrofit2.Response<Response<TokenData>>>

    @POST("/user/login")
    fun postLogin1(@Body loginRequest: LoginRequest) : Call<Response<TokenData>>
}