package kr.hs.dgsw.domain.usecase.auth

import io.reactivex.rxjava3.core.Single
import kr.hs.dgsw.domain.base.ParamsUseCase
import kr.hs.dgsw.domain.repository.AuthRepository
import javax.inject.Inject

class PasswordChkUseCase @Inject constructor(
    private val authRepository: AuthRepository
): ParamsUseCase<PasswordChkUseCase.Params, Single<String>>() {
    override fun buildUseCaseObservable(params: Params): Single<String> {
        return authRepository.postPasswordChk(params.password)
    }

    data class Params(
        val password: String
    )
}