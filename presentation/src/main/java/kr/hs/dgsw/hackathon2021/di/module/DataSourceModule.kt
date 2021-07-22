package kr.hs.dgsw.hackathon2021.di.module

import dagger.Module
import dagger.Provides
import kr.hs.dgsw.data.datasource.LectureDataSource
import kr.hs.dgsw.data.datasource.AccountDataSource
import kr.hs.dgsw.data.network.remote.LectureRemote
import kr.hs.dgsw.data.network.remote.AccountRemote
import javax.inject.Singleton

@Module
class DataSourceModule {
    @Provides
    @Singleton
    fun provideAccountDataSource(accountRemote: AccountRemote): AccountDataSource {
        return AccountDataSource(accountRemote)
    }

    @Provides
    @Singleton
    fun provideLectureDataSource(lectureRemote: LectureRemote): LectureDataSource {
        return LectureDataSource(lectureRemote)
    }
}