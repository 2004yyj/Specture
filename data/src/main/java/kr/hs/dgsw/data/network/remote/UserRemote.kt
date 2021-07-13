package kr.hs.dgsw.data.network.remote

import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Single
import kr.hs.dgsw.data.base.BaseRemote
import kr.hs.dgsw.data.entity.TokenData
import kr.hs.dgsw.data.network.service.UserService
import kr.hs.dgsw.domain.entity.request.LoginRequest
import kr.hs.dgsw.domain.entity.request.SignUpRequest
import javax.inject.Inject

class UserRemote @Inject constructor(
    override val service: UserService
): BaseRemote<UserService>() {

    fun postSignUp(signUpRequest: SignUpRequest): Single<TokenData> {
        return service.postSignUp(signUpRequest).map(getResponse())
    }

    fun postLogin(loginRequest: LoginRequest): Single<TokenData> {
        return service.postLogin(loginRequest).map(getResponse())
    }

}