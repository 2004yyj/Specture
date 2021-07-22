package kr.hs.dgsw.hackathon2021.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.hs.dgsw.domain.usecase.account.AutoLoginUseCase
import kr.hs.dgsw.hackathon2021.ui.viewmodel.activity.SplashViewModel

class SplashViewModelFactory(
    private val autoLoginUseCase: AutoLoginUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            SplashViewModel(autoLoginUseCase) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}
