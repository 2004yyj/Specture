package kr.hs.dgsw.hackathon2021.di.module

import dagger.Module
import dagger.Provides
import kr.hs.dgsw.domain.repository.LectureRepository
import kr.hs.dgsw.domain.repository.AccountRepository
import kr.hs.dgsw.domain.usecase.lecture.*
import kr.hs.dgsw.domain.usecase.account.AutoLoginUseCase
import kr.hs.dgsw.domain.usecase.account.GetUserUseCase
import kr.hs.dgsw.domain.usecase.account.LoginUseCase
import kr.hs.dgsw.domain.usecase.account.SignUpUseCase
import javax.inject.Singleton

@Module
class UseCaseModule {
    @Provides
    @Singleton
    fun provideSignUp(accountRepository: AccountRepository): SignUpUseCase {
        return SignUpUseCase(accountRepository)
    }

    @Provides
    @Singleton
    fun provideLogin(accountRepository: AccountRepository): LoginUseCase {
        return LoginUseCase(accountRepository)
    }

    @Provides
    @Singleton
    fun provideAutoLogin(accountRepository: AccountRepository): AutoLoginUseCase {
        return AutoLoginUseCase(accountRepository)
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
    fun provideUser(accountRepository: AccountRepository): GetUserUseCase {
        return GetUserUseCase(accountRepository)
    }

    @Provides
    @Singleton
    fun providePostLecture(lectureRepository: LectureRepository): PostLectureUseCase {
        return PostLectureUseCase(lectureRepository)
    }
}