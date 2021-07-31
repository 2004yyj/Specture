package kr.hs.dgsw.domain.repository

import io.reactivex.rxjava3.core.Single
import kr.hs.dgsw.domain.entity.request.UpdateUserRequest
import kr.hs.dgsw.domain.entity.response.User

interface UserRepository {
    fun getUser(): Single<User>

    fun putUser(updateUserRequest: UpdateUserRequest): Single<String>
}