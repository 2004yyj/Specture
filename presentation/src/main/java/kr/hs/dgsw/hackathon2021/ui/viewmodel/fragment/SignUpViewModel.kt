package kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kr.hs.dgsw.domain.entity.request.SignUpRequest
import kr.hs.dgsw.domain.entity.response.Token
import kr.hs.dgsw.domain.usecase.user.SignUpUseCase

class SignUpViewModel(private val signUpUseCase: SignUpUseCase) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _isSuccess = MutableLiveData<String>()
    val isSuccess: LiveData<String> = _isSuccess

    private val _isFailure = MutableLiveData<String>()
    val isFailure: LiveData<String> = _isFailure

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun signUp(signUpRequest: SignUpRequest) {
        _isLoading.postValue(true)

        signUpUseCase.buildUseCaseObservable(SignUpUseCase.Params(signUpRequest))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                _isSuccess.postValue(it.token)
                _isLoading.postValue(false)
            }, {
                _isFailure.postValue(it.message)
                _isLoading.postValue(false)
            }).apply {
                compositeDisposable.add(this)
            }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}