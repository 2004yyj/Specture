package kr.hs.dgsw.data.network.remote

import io.reactivex.rxjava3.core.Single
import kr.hs.dgsw.data.base.BaseRemote
import kr.hs.dgsw.data.entity.UserData
import kr.hs.dgsw.data.network.service.UserService
import kr.hs.dgsw.domain.entity.request.UpdateUserRequest
import javax.inject.Inject

class UserRemote @Inject constructor(
    override val service: UserService
): BaseRemote<UserService>() {
    fun getUser(): Single<UserData> {
        return service.getUser().map(getResponse())
    }

    fun putUser(updateUserRequest: UpdateUserRequest): Single<String> {
        with(updateUserRequest) {
            return service.putUser(
                userId, password, name, grade, klass, number, introduce, field, profile
            ).map(getMessage())
        }
    }
}