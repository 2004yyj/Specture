package kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kr.hs.dgsw.domain.entity.request.LoginRequest
import kr.hs.dgsw.domain.usecase.auth.LoginUseCase
import kr.hs.dgsw.hackathon2021.ui.view.util.Event
import kr.hs.dgsw.hackathon2021.ui.view.util.InfoHelper
import kr.hs.dgsw.hackathon2021.ui.view.util.SingleLiveEvent

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val autoLoginCheck = MutableLiveData(InfoHelper.autoLoginChk)

    private val compositeDisposable = CompositeDisposable()

    private val _isSuccess = MutableLiveData<Event<String>>()
    val isSuccess : LiveData<Event<String>> = _isSuccess

    private val _isFailure = MutableLiveData<Event<String>>()
    val isFailure : LiveData<Event<String>> = _isFailure

    private val _isLoading = MutableLiveData<Event<Boolean>>()
    val isLoading : LiveData<Event<Boolean>> = _isLoading

    private val _navigateLoginToSignUp = SingleLiveEvent<Unit>()
    val navigateLoginToSignUp: LiveData<Unit> = _navigateLoginToSignUp

    fun login() {
        if(!(username.value.isNullOrEmpty() && password.value.isNullOrEmpty())) {
            val loginRequest = LoginRequest(username.value!!, password.value!!)

            _isLoading.value = Event(true)

            val params = LoginUseCase.Params(loginRequest)
            loginUseCase.buildUseCaseObservable(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    _isSuccess.value = Event(it.token)
                    _isLoading.value = Event(false)
                } ,{
                    _isFailure.value = Event(it.message?:"")
                    _isLoading.value = Event(false)
                }).apply {
                    compositeDisposable.add(this)
                }

        } else {
            _isFailure.value = Event("????????? ?????? ??????????????? ?????? ????????????.")
        }
    }

    fun navigateLoginToSignUp() {
        _navigateLoginToSignUp.call()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
