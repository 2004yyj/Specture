package kr.hs.dgsw.data.datasource

import io.reactivex.rxjava3.core.Single
import kr.hs.dgsw.data.base.BaseDataSource
import kr.hs.dgsw.data.mapper.toEntity
import kr.hs.dgsw.data.network.remote.UserRemote
import kr.hs.dgsw.domain.entity.response.User
import javax.inject.Inject

class UserDataSource @Inject constructor(
    override val remote: UserRemote
): BaseDataSource<UserRemote>() {
    fun getUser(): Single<User> {
        return remote.getUser().map { it.toEntity() }
    }
}