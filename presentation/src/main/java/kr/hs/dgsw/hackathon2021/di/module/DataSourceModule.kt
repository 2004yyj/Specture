package kr.hs.dgsw.hackathon2021.di.module

import dagger.Module
import dagger.Provides
import kr.hs.dgsw.data.datasource.UserDataSource
import kr.hs.dgsw.data.network.remote.UserRemote
import javax.inject.Singleton

@Module
class DataSourceModule {
    @Provides
    @Singleton
    fun provideUserDataSource(userRemote: UserRemote): UserDataSource {
        return UserDataSource(userRemote)
    }
}