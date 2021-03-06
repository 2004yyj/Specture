package kr.hs.dgsw.domain.usecase.auth

import io.reactivex.rxjava3.core.Single
import kr.hs.dgsw.domain.base.ParamsUseCase
import kr.hs.dgsw.domain.entity.request.SignUpRequest
import kr.hs.dgsw.domain.entity.response.Token
import kr.hs.dgsw.domain.repository.AuthRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository
): ParamsUseCase<SignUpUseCase.Params, Single<Token>>() {
    override fun buildUseCaseObservable(params: Params): Single<Token> {
        return authRepository.postSignUp(params.signUpRequest)
    }

    data class Params(
        val signUpRequest: SignUpRequest
    )
}