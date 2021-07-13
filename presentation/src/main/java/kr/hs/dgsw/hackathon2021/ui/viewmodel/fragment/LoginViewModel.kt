package kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kr.hs.dgsw.domain.entity.request.LoginRequest
import kr.hs.dgsw.domain.usecase.user.LoginUseCase

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    fun login(loginRequest: LoginRequest) {
        val params = LoginUseCase.Params(loginRequest)
        loginUseCase.buildUseCaseObservable(params)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({

            } ,{

            })
    }
}
