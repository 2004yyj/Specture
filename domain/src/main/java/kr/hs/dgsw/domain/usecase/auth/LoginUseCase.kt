package kr.hs.dgsw.domain.usecase.auth

import io.reactivex.rxjava3.core.Single
import kr.hs.dgsw.domain.base.ParamsUseCase
import kr.hs.dgsw.domain.entity.request.LoginRequest
import kr.hs.dgsw.domain.entity.response.Token
import kr.hs.dgsw.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
): ParamsUseCase<LoginUseCase.Params, Single<Token>>() {
    override fun buildUseCaseObservable(params: Params): Single<Token> {
        return authRepository.postLogin(params.loginRequest)
    }

    data class Params(
        val loginRequest: LoginRequest
    )
}