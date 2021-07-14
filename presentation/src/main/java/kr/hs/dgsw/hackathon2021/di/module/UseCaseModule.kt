package kr.hs.dgsw.hackathon2021.di.module

import dagger.Module
import dagger.Provides
import kr.hs.dgsw.domain.repository.LectureRepository
import kr.hs.dgsw.domain.repository.UserRepository
import kr.hs.dgsw.domain.usecase.lecture.GetAllClassUseCase
import kr.hs.dgsw.domain.usecase.lecture.GetClassByDateUseCase
import kr.hs.dgsw.domain.usecase.lecture.GetLectureDetailUseCase
import kr.hs.dgsw.domain.usecase.user.AutoLoginUseCase
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

    @Provides
    @Singleton
    fun provideAutoLogin(userRepository: UserRepository): AutoLoginUseCase {
        return AutoLoginUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideLecture(lectureRepository: LectureRepository): GetAllClassUseCase {
        return GetAllClassUseCase(lectureRepository)
    }

    @Provides
    @Singleton
    fun provideLectureDetail(lectureRepository: LectureRepository): GetLectureDetailUseCase {
        return GetLectureDetailUseCase(lectureRepository)
    }

    @Provides
    @Singleton
    fun provideClassByDate(lectureRepository: LectureRepository): GetClassByDateUseCase {
        return GetClassByDateUseCase(lectureRepository)
    }
}