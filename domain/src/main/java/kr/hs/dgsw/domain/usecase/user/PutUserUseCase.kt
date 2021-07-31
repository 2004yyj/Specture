package kr.hs.dgsw.domain.usecase.user

import io.reactivex.rxjava3.core.Single
import kr.hs.dgsw.domain.base.ParamsUseCase
import kr.hs.dgsw.domain.entity.request.UpdateUserRequest
import kr.hs.dgsw.domain.repository.UserRepository
import javax.inject.Inject

class PutUserUseCase @Inject constructor(
    private val userRepository: UserRepository
): ParamsUseCase<PutUserUseCase.Params, Single<String>>() {
    override fun buildUseCaseObservable(params: Params): Single<String> {
        return userRepository.putUser(params.updateUserRequest)
    }

    data class Params(
        val updateUserRequest: UpdateUserRequest
    )
}