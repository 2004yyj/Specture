package kr.hs.dgsw.hackathon2021.di.module

import dagger.Module
import dagger.Provides
import kr.hs.dgsw.domain.repository.LectureRepository
import kr.hs.dgsw.domain.repository.AuthRepository
import kr.hs.dgsw.domain.usecase.lecture.*
import kr.hs.dgsw.domain.usecase.auth.AutoLoginUseCase
import kr.hs.dgsw.domain.usecase.auth.GetUserUseCase
import kr.hs.dgsw.domain.usecase.auth.LoginUseCase
import kr.hs.dgsw.domain.usecase.auth.SignUpUseCase
import javax.inject.Singleton

@Module
class UseCaseModule {
    @Provides
    @Singleton
    fun provideSignUp(authRepository: AuthRepository): SignUpUseCase {
        return SignUpUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideLogin(authRepository: AuthRepository): LoginUseCase {
        return LoginUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideAutoLogin(authRepository: AuthRepository): AutoLoginUseCase {
        return AutoLoginUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideGetLecture(lectureRepository: LectureRepository): GetAllClassUseCase {
        return GetAllClassUseCase(lectureRepository)
    }

    @Provides
    @Singleton
    fun provideLectureDetail(lectureRepository: LectureRepository): GetLectureDetailUseCase {
        return GetLectureDetailUseCase(lectureRepository)
    }

    @Provides
    @Singleton
    fun provideLectureByDate(lectureRepository: LectureRepository): GetAllLectureByDateUseCase {
        return GetAllLectureByDateUseCase(lectureRepository)
    }

    @Provides
    @Singleton
    fun provideLectureProposal(lectureRepository: LectureRepository): PostLectureProposalUseCase {
        return PostLectureProposalUseCase(lectureRepository)
    }

    @Provides
    @Singleton
    fun provideUser(authRepository: AuthRepository): GetUserUseCase {
        return GetUserUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun providePostLecture(lectureRepository: LectureRepository): PostLectureUseCase {
        return PostLectureUseCase(lectureRepository)
    }
}