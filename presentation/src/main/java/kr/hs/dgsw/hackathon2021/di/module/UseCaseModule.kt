package kr.hs.dgsw.hackathon2021.di.module

import dagger.Module
import dagger.Provides
import kr.hs.dgsw.domain.repository.LectureRepository
import kr.hs.dgsw.domain.repository.AuthRepository
import kr.hs.dgsw.domain.repository.UserRepository
import kr.hs.dgsw.domain.usecase.lecture.*
import kr.hs.dgsw.domain.usecase.auth.AutoLoginUseCase
import kr.hs.dgsw.domain.usecase.user.GetUserUseCase
import kr.hs.dgsw.domain.usecase.auth.LoginUseCase
import kr.hs.dgsw.domain.usecase.auth.PasswordChkUseCase
import kr.hs.dgsw.domain.usecase.auth.SignUpUseCase
import kr.hs.dgsw.domain.usecase.user.PutUserUseCase
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
    fun providePasswordChk(authRepository: AuthRepository): PasswordChkUseCase {
        return PasswordChkUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideGetAllLecture(lectureRepository: LectureRepository): GetAllLectureUseCase {
        return GetAllLectureUseCase(lectureRepository)
    }

    @Provides
    @Singleton
    fun provideGetLectureDetail(lectureRepository: LectureRepository): GetLectureDetailUseCase {
        return GetLectureDetailUseCase(lectureRepository)
    }

    @Provides
    @Singleton
    fun provideGetAllLectureByDate(lectureRepository: LectureRepository): GetAllLectureByDateUseCase {
        return GetAllLectureByDateUseCase(lectureRepository)
    }

    @Provides
    @Singleton
    fun provideGetAllLectureByUserId(lectureRepository: LectureRepository): GetAllLectureByUserIdUseCase {
        return GetAllLectureByUserIdUseCase(lectureRepository)
    }

    @Provides
    @Singleton
    fun providePostLectureProposal(lectureRepository: LectureRepository): PostLectureProposalUseCase {
        return PostLectureProposalUseCase(lectureRepository)
    }

    @Provides
    @Singleton
    fun provideGetUser(userRepository: UserRepository): GetUserUseCase {
        return GetUserUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun providePutUser(userRepository: UserRepository): PutUserUseCase {
        return PutUserUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun providePostLecture(lectureRepository: LectureRepository): PostLectureUseCase {
        return PostLectureUseCase(lectureRepository)
    }

    @Provides
    @Singleton
    fun provideGetAllLectureProposalByUserId(lectureRepository: LectureRepository): GetAllLectureProposalByUserIdUseCase {
        return GetAllLectureProposalByUserIdUseCase(lectureRepository)
    }
}