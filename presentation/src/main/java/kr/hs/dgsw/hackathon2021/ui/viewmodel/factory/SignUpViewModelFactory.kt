package kr.hs.dgsw.hackathon2021.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.hs.dgsw.domain.usecase.auth.SignUpUseCase
import kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment.SignUpViewModel

class SignUpViewModelFactory(private val signUpUseCase: SignUpUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            SignUpViewModel(signUpUseCase) as T
        } else {
            throw IllegalArgumentException()
        }
    }

}
