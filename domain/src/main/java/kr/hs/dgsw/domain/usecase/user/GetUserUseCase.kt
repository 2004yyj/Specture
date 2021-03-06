package kr.hs.dgsw.domain.usecase.user

import io.reactivex.rxjava3.core.Single
import kr.hs.dgsw.domain.base.BaseUseCase
import kr.hs.dgsw.domain.entity.response.User
import kr.hs.dgsw.domain.repository.UserRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository
): BaseUseCase<Single<User>>() {
    override fun buildUseCaseObservable(): Single<User> {
        return userRepository.getUser()
    }
}