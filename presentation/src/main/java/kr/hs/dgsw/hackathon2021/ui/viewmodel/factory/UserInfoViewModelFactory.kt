package kr.hs.dgsw.hackathon2021.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.hs.dgsw.domain.usecase.user.GetUserUseCase
import kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment.UserInfoViewModel

class UserInfoViewModelFactory(
    private val getUserUseCase: GetUserUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(UserInfoViewModel::class.java)) {
            UserInfoViewModel(getUserUseCase) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}