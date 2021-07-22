package kr.hs.dgsw.domain.usecase.auth

import io.reactivex.rxjava3.core.Single
import kr.hs.dgsw.domain.base.BaseUseCase
import kr.hs.dgsw.domain.entity.response.Token
import kr.hs.dgsw.domain.repository.AuthRepository

class AutoLoginUseCase(
    private val authRepository: AuthRepository
): BaseUseCase<Single<Token>>() {
    override fun buildUseCaseObservable(): Single<Token> {
        return authRepository.postAutoLogin()
    }
}