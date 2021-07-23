package kr.hs.dgsw.hackathon2021.di.module

import dagger.Module
import dagger.Provides
import kr.hs.dgsw.data.datasource.LectureDataSource
import kr.hs.dgsw.data.datasource.AuthDataSource
import kr.hs.dgsw.data.datasource.UserDataSource
import kr.hs.dgsw.data.network.remote.LectureRemote
import kr.hs.dgsw.data.network.remote.AuthRemote
import kr.hs.dgsw.data.network.remote.UserRemote
import javax.inject.Singleton

@Module
class DataSourceModule {
    @Provides
    @Singleton
    fun provideAuthDataSource(authRemote: AuthRemote): AuthDataSource {
        return AuthDataSource(authRemote)
    }

    @Provides
    @Singleton
    fun provideLectureDataSource(lectureRemote: LectureRemote): LectureDataSource {
        return LectureDataSource(lectureRemote)
    }

    @Provides
    @Singleton
    fun provideUserDataSource(userRemote: UserRemote): UserDataSource {
        return UserDataSource(userRemote)
    }
}