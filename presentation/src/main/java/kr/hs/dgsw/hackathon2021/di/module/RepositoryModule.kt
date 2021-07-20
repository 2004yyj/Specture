package kr.hs.dgsw.hackathon2021.di.module

import dagger.Module
import dagger.Provides
import kr.hs.dgsw.data.datasource.LectureDataSource
import kr.hs.dgsw.data.datasource.AccountDataSource
import kr.hs.dgsw.data.repository.LectureRepositoryImpl
import kr.hs.dgsw.data.repository.AccountRepositoryImpl
import kr.hs.dgsw.domain.repository.LectureRepository
import kr.hs.dgsw.domain.repository.AccountRepository
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideUserRepository(accountDataSource: AccountDataSource): AccountRepository {
        return AccountRepositoryImpl(accountDataSource)
    }

    @Provides
    @Singleton
    fun provideLectureRepository(lectureDataSource: LectureDataSource): LectureRepository {
        return LectureRepositoryImpl(lectureDataSource)
    }
}