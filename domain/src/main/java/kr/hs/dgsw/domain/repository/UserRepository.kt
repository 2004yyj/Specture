package kr.hs.dgsw.domain.repository

import io.reactivex.rxjava3.core.Single
import kr.hs.dgsw.domain.entity.response.User

interface UserRepository {
    fun getUser(): Single<User>
}