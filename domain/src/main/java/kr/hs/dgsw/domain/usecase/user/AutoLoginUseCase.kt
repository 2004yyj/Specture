package kr.hs.dgsw.domain.usecase.user

import io.reactivex.rxjava3.core.Single
import kr.hs.dgsw.domain.base.BaseUseCase
import kr.hs.dgsw.domain.entity.response.Token
import kr.hs.dgsw.domain.repository.AccountRepository

class AutoLoginUseCase(
    private val accountRepository: AccountRepository
): BaseUseCase<Single<Token>>() {
    override fun buildUseCaseObservable(): Single<Token> {
        return accountRepository.postAutoLogin()
    }
}