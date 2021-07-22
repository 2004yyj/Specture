package kr.hs.dgsw.domain.usecase.auth

import io.reactivex.rxjava3.core.Single
import kr.hs.dgsw.domain.base.BaseUseCase
import kr.hs.dgsw.domain.entity.response.User
import kr.hs.dgsw.domain.repository.AuthRepository

class GetUserUseCase(
    private val authRepository: AuthRepository
): BaseUseCase<Single<User>>() {
    override fun buildUseCaseObservable(): Single<User> {
        return authRepository.getUser()
    }
}