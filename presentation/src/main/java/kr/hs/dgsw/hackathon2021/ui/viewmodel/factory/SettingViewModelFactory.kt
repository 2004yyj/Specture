package kr.hs.dgsw.hackathon2021.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.hs.dgsw.domain.usecase.account.GetUserUseCase
import kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment.SettingViewModel

class SettingViewModelFactory(
    private val getUserUseCase: GetUserUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            SettingViewModel(getUserUseCase) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}