package kr.hs.dgsw.hackathon2021.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.hs.dgsw.domain.usecase.user.LoginUseCase
import kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment.LoginViewModel
import kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment.RecruitingClassViewModel

class RecruitingClassViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
             RecruitingClassViewModel() as T
        } else {
            throw IllegalArgumentException()
        }
    }
}