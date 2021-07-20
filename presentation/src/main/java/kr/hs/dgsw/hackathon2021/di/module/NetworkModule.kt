package kr.hs.dgsw.hackathon2021.di.module

import dagger.Module
import dagger.Provides
import kr.hs.dgsw.hackathon2021.ui.view.interceptor.UserInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun provideClient() =
        OkHttpClient.Builder()
            .addInterceptor(UserInterceptor())
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient) =
        Retrofit.Builder()
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .baseUrl("http://10.0.2.2:8080")
            .build()
}