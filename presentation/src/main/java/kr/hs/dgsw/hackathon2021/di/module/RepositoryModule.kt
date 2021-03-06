package kr.hs.dgsw.hackathon2021.di.module

import dagger.Module
import dagger.Provides
import kr.hs.dgsw.data.datasource.LectureDataSource
import kr.hs.dgsw.data.datasource.AuthDataSource
import kr.hs.dgsw.data.datasource.UserDataSource
import kr.hs.dgsw.data.repository.LectureRepositoryImpl
import kr.hs.dgsw.data.repository.AuthRepositoryImpl
import kr.hs.dgsw.data.repository.UserRepositoryImpl
import kr.hs.dgsw.domain.repository.LectureRepository
import kr.hs.dgsw.domain.repository.AuthRepository
import kr.hs.dgsw.domain.repository.UserRepository
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideAuthRepository(authDataSource: AuthDataSource): AuthRepository {
        return AuthRepositoryImpl(authDataSource)
    }

    @Provides
    @Singleton
    fun provideLectureRepository(lectureDataSource: LectureDataSource): LectureRepository {
        return LectureRepositoryImpl(lectureDataSource)
    }

    @Provides
    @Singleton
    fun provideUserRepository(userDataSource: UserDataSource): UserRepository {
        return UserRepositoryImpl(userDataSource)
    }
}