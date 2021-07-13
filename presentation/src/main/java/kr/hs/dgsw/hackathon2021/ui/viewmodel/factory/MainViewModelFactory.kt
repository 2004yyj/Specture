package kr.hs.dgsw.hackathon2021.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.hs.dgsw.domain.usecase.user.AutoLoginUseCase
import kr.hs.dgsw.hackathon2021.ui.viewmodel.activity.MainViewModel

class MainViewModelFactory(
    private val autoLoginUseCase: AutoLoginUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(autoLoginUseCase) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}
