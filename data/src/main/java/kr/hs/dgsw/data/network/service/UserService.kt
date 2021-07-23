package kr.hs.dgsw.data.network.service

import io.reactivex.rxjava3.core.Single
import kr.hs.dgsw.data.entity.UserData
import kr.hs.dgsw.data.util.Response
import retrofit2.http.GET

interface UserService {
    @GET("/user")
    fun getUser() : Single<retrofit2.Response<Response<UserData>>>
}