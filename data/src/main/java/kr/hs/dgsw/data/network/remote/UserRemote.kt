package kr.hs.dgsw.data.network.remote

import io.reactivex.rxjava3.core.Single
import kr.hs.dgsw.data.base.BaseRemote
import kr.hs.dgsw.data.entity.TokenData
import kr.hs.dgsw.data.entity.UserData
import kr.hs.dgsw.data.network.service.UserService
import kr.hs.dgsw.domain.entity.request.LoginRequest
import kr.hs.dgsw.domain.entity.request.SignUpRequest
import javax.inject.Inject

class UserRemote @Inject constructor(
    override val service: UserService
): BaseRemote<UserService>() {

    fun postSignUp(signUpRequest: SignUpRequest): Single<TokenData> {
        with(signUpRequest) {
            return service.postSignUp(
                userId, password, name, grade, klass, number, introduce, field, profile
            ).map(getResponse())
        }
    }

    fun postLogin(loginRequest: LoginRequest): Single<TokenData> {
        return service.postLogin(loginRequest).map(getResponse())
    }

    fun postAutoLogin(): Single<TokenData> {
        return service.postAutoLogin().map(getResponse())
    }

    fun getUser(): Single<UserData> {
        return service.getUser().map(getResponse())
    }

}