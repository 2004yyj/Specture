package kr.hs.dgsw.data.mapper

import kr.hs.dgsw.data.entity.UserData
import kr.hs.dgsw.domain.entity.response.User

fun UserData.toEntity(): User {
    return User(
        this.userId,
        this.password,
        this.profile,
        this.name,
        this.grade,
        this.klass,
        this.number,
        this.introduce,
        this.field,
        this.attachmentUrl
    )
}

fun User.toData(): UserData {
    return UserData(
        this.userId,
        this.password,
        this.profile,
        this.name,
        this.grade,
        this.klass,
        this.number,
        this.introduce,
        this.field,
        this.attachmentUrl
    )
}