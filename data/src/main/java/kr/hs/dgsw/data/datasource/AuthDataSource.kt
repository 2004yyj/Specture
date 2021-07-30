package kr.hs.dgsw.data.datasource

import io.reactivex.rxjava3.core.Single
import kr.hs.dgsw.data.base.BaseDataSource
import kr.hs.dgsw.data.mapper.toEntity
import kr.hs.dgsw.data.network.remote.AuthRemote
import kr.hs.dgsw.domain.entity.request.LoginRequest
import kr.hs.dgsw.domain.entity.request.SignUpRequest
import kr.hs.dgsw.domain.entity.response.Token
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    override val remote: AuthRemote
): BaseDataSource<AuthRemote>() {
    fun postSignUp(signUpRequest: SignUpRequest): Single<Token> {
        return remote.postSignUp(signUpRequest).map { it.toEntity() }
    }

    fun postLogin(loginRequest: LoginRequest): Single<Token> {
        return remote.postLogin(loginRequest).map { it.toEntity() }
    }

    fun postAutoLogin(): Single<Token> {
        return remote.postAutoLogin().map { it.toEntity() }
    }

    fun postPasswordChk(password: String): Single<String> {
        return remote.postPasswordChk(password)
    }
}