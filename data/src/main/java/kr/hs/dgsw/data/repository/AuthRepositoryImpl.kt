package kr.hs.dgsw.data.repository

import io.reactivex.rxjava3.core.Single
import kr.hs.dgsw.data.datasource.AuthDataSource
import kr.hs.dgsw.domain.entity.request.LoginRequest
import kr.hs.dgsw.domain.entity.request.SignUpRequest
import kr.hs.dgsw.domain.entity.response.Token
import kr.hs.dgsw.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val authDataSource: AuthDataSource
): AuthRepository {
    override fun postSignUp(signUpRequest: SignUpRequest): Single<Token> {
        return authDataSource.postSignUp(signUpRequest)
    }

    override fun postLogin(loginRequest: LoginRequest): Single<Token> {
        return authDataSource.postLogin(loginRequest)
    }

    override fun postAutoLogin(): Single<Token> {
        return authDataSource.postAutoLogin()
    }
}