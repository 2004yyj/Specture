package kr.hs.dgsw.hackathon2021.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.hs.dgsw.domain.usecase.lecture.GetAllClassUseCase
import kr.hs.dgsw.domain.usecase.user.LoginUseCase
import kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment.LoginViewModel
import kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment.RecruitingClassViewModel

class RecruitingClassViewModelFactory(private val getAllClassUseCase: GetAllClassUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(RecruitingClassViewModel::class.java)) {
             RecruitingClassViewModel(getAllClassUseCase) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}