package kr.hs.dgsw.domain.usecase.account

import io.reactivex.rxjava3.core.Single
import kr.hs.dgsw.domain.base.ParamsUseCase
import kr.hs.dgsw.domain.entity.request.LoginRequest
import kr.hs.dgsw.domain.entity.response.Token
import kr.hs.dgsw.domain.repository.AccountRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val accountRepository: AccountRepository
): ParamsUseCase<LoginUseCase.Params, Single<Token>>() {
    override fun buildUseCaseObservable(params: Params): Single<Token> {
        return accountRepository.postLogin(params.loginRequest)
    }

    data class Params(
        val loginRequest: LoginRequest
    )
}