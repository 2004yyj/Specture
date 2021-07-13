package kr.hs.dgsw.hackathon2021.ui.view.interceptor

import kr.hs.dgsw.hackathon2021.ui.view.helper.InfoHelper.token
import okhttp3.Interceptor
import okhttp3.Response

class UserInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().addHeader("Authorization", token?:"").build()
        return chain.proceed(request)
    }
}