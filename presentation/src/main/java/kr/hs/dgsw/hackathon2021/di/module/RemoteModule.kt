package kr.hs.dgsw.hackathon2021.di.module

import dagger.Module
import dagger.Provides
import kr.hs.dgsw.data.network.remote.UserRemote
import kr.hs.dgsw.data.network.service.UserService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class RemoteModule {
    @Provides
    @Singleton
    fun provideUserRemote(retrofit: Retrofit): UserRemote {
        return UserRemote(retrofit.create(UserService::class.java))
    }
}