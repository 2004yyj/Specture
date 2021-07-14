package kr.hs.dgsw.data.datasource

import io.reactivex.rxjava3.core.Single
import kr.hs.dgsw.data.base.BaseDataSource
import kr.hs.dgsw.data.mapper.toEntity
import kr.hs.dgsw.data.network.remote.UserRemote
import kr.hs.dgsw.domain.entity.request.LoginRequest
import kr.hs.dgsw.domain.entity.request.SignUpRequest
import kr.hs.dgsw.domain.entity.response.Token
import kr.hs.dgsw.domain.entity.response.User
import javax.inject.Inject

class UserDataSource @Inject constructor(
    override val remote: UserRemote
): BaseDataSource<UserRemote>() {
    fun postSignUp(signUpRequest: SignUpRequest): Single<Token> {
        return remote.postSignUp(signUpRequest).map { it.toEntity() }
    }

    fun postLogin(loginRequest: LoginRequest): Single<Token> {
        return remote.postLogin(loginRequest).map { it.toEntity() }
    }

    fun postAutoLogin(): Single<Token> {
        return remote.postAutoLogin().map { it.toEntity() }
    }

    fun getUser(): Single<User> {
        return remote.getUser().map { it.toEntity() }
    }
}