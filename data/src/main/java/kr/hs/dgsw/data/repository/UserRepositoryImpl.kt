package kr.hs.dgsw.data.repository

import io.reactivex.rxjava3.core.Single
import kr.hs.dgsw.data.datasource.UserDataSource
import kr.hs.dgsw.domain.entity.request.UpdateUserRequest
import kr.hs.dgsw.domain.entity.response.User
import kr.hs.dgsw.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource
): UserRepository {
    override fun getUser(): Single<User> {
        return userDataSource.getUser()
    }

    override fun putUser(updateUserRequest: UpdateUserRequest): Single<String> {
        return userDataSource.putUser(updateUserRequest)
    }
}