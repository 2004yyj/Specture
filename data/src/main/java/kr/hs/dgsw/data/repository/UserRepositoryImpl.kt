package kr.hs.dgsw.data.repository

import io.reactivex.rxjava3.core.Single
import kr.hs.dgsw.data.datasource.UserDataSource
import kr.hs.dgsw.domain.entity.request.LoginRequest
import kr.hs.dgsw.domain.entity.request.SignUpRequest
import kr.hs.dgsw.domain.entity.response.Token
import kr.hs.dgsw.domain.repository.UserRepository

class UserRepositoryImpl(
    private val userDataSource: UserDataSource
): UserRepository {
    override fun postSignUp(signUpRequest: SignUpRequest): Single<Token> {
        return userDataSource.postSignUp(signUpRequest)
    }

    override fun postLogin(loginRequest: LoginRequest): Single<Token> {
        return userDataSource.postLogin(loginRequest)
    }

    override fun postAutoLogin(): Single<Token> {
        return userDataSource.postAutoLogin()
    }
}