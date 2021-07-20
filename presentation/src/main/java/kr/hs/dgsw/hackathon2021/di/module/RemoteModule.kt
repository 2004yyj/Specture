package kr.hs.dgsw.hackathon2021.di.module

import dagger.Module
import dagger.Provides
import kr.hs.dgsw.data.network.remote.LectureRemote
import kr.hs.dgsw.data.network.remote.AccountRemote
import kr.hs.dgsw.data.network.service.LectureService
import kr.hs.dgsw.data.network.service.AccountService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class RemoteModule {
    @Provides
    @Singleton
    fun provideUserRemote(retrofit: Retrofit): AccountRemote {
        return AccountRemote(retrofit.create(AccountService::class.java))
    }

    @Provides
    @Singleton
    fun provideLectureRemote(retrofit: Retrofit): LectureRemote {
        return LectureRemote(retrofit.create(LectureService::class.java))
    }
}