package kr.hs.dgsw.hackathon2021.di.module

import dagger.Module
import dagger.Provides
import kr.hs.dgsw.domain.repository.UserRepository
import kr.hs.dgsw.domain.usecase.user.LoginUseCase
import kr.hs.dgsw.domain.usecase.user.SignUpUseCase
import javax.inject.Singleton

@Module
class UseCaseModule {
    @Provides
    @Singleton
    fun provideSignUp(userRepository: UserRepository): SignUpUseCase {
        return SignUpUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideLogin(userRepository: UserRepository): LoginUseCase {
        return LoginUseCase(userRepository)
    }
}