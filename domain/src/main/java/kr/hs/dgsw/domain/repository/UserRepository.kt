package kr.hs.dgsw.domain.repository

import io.reactivex.rxjava3.core.Single
import kr.hs.dgsw.domain.entity.request.LoginRequest
import kr.hs.dgsw.domain.entity.request.SignUpRequest
import kr.hs.dgsw.domain.entity.response.Token

interface UserRepository {
    fun postSignUp(signUpRequest: SignUpRequest): Single<Token>

    fun postLogin(loginRequest: LoginRequest): Single<Token>
}