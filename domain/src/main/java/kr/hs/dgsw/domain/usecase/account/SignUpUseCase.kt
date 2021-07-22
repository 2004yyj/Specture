package kr.hs.dgsw.domain.usecase.account

import io.reactivex.rxjava3.core.Single
import kr.hs.dgsw.domain.base.ParamsUseCase
import kr.hs.dgsw.domain.entity.request.SignUpRequest
import kr.hs.dgsw.domain.entity.response.Token
import kr.hs.dgsw.domain.repository.AccountRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val accountRepository: AccountRepository
): ParamsUseCase<SignUpUseCase.Params, Single<Token>>() {
    override fun buildUseCaseObservable(params: Params): Single<Token> {
        return accountRepository.postSignUp(params.signUpRequest)
    }

    data class Params(
        val signUpRequest: SignUpRequest
    )
}