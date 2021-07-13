package kr.hs.dgsw.data.mapper

import kr.hs.dgsw.data.entity.TokenData
import kr.hs.dgsw.domain.entity.response.Token

fun TokenData.toEntity(): Token {
    return Token(
        this.token
    )
}

fun Token.toData(): TokenData {
    return TokenData(
        this.token
    )
}