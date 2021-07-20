package kr.hs.dgsw.data.repository

import io.reactivex.rxjava3.core.Single
import kr.hs.dgsw.data.datasource.AccountDataSource
import kr.hs.dgsw.domain.entity.request.LoginRequest
import kr.hs.dgsw.domain.entity.request.SignUpRequest
import kr.hs.dgsw.domain.entity.response.Token
import kr.hs.dgsw.domain.entity.response.User
import kr.hs.dgsw.domain.repository.AccountRepository

class AccountRepositoryImpl(
    private val accountDataSource: AccountDataSource
): AccountRepository {
    override fun postSignUp(signUpRequest: SignUpRequest): Single<Token> {
        return accountDataSource.postSignUp(signUpRequest)
    }

    override fun postLogin(loginRequest: LoginRequest): Single<Token> {
        return accountDataSource.postLogin(loginRequest)
    }

    override fun postAutoLogin(): Single<Token> {
        return accountDataSource.postAutoLogin()
    }

    override fun getUser(): Single<User> {
        return accountDataSource.getUser()
    }
}