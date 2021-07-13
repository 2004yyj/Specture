package kr.hs.dgsw.hackathon2021.ui.viewmodel.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kr.hs.dgsw.domain.usecase.user.AutoLoginUseCase

class SplashViewModel(
    private val autoLoginUseCase: AutoLoginUseCase
): ViewModel() {

    private val _isSuccess = MutableLiveData<String>()
    val isSuccess: LiveData<String> = _isSuccess

    private val _isFailure = MutableLiveData<String>()
    val isFailure: LiveData<String> = _isFailure

    fun autoLogin() {
        autoLoginUseCase.buildUseCaseObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                _isSuccess.postValue(it.token)
            }, {
                _isFailure.postValue(it.message)
            })
    }

}
