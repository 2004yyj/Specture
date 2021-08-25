package kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kr.hs.dgsw.domain.entity.request.SignUpRequest
import kr.hs.dgsw.domain.usecase.auth.SignUpUseCase
import kr.hs.dgsw.hackathon2021.ui.view.util.Event
import kr.hs.dgsw.hackathon2021.ui.view.util.SingleLiveEvent

class SignUpViewModel(private val signUpUseCase: SignUpUseCase) : ViewModel() {

    val userId = ObservableField<String>()
    val password = ObservableField<String>()
    val passwordRe = ObservableField<String>()
    val name = ObservableField<String>()
    val grade = ObservableInt()
    val klass = ObservableInt()
    val number = ObservableInt()

    private val compositeDisposable = CompositeDisposable()

    private val _navigateSignUpToSignUpInfo = SingleLiveEvent<Unit>()
    val navigateSignUpToSignUpInfo = _navigateSignUpToSignUpInfo

    private val _isSuccess = MutableLiveData<Event<String>>()
    val isSuccess: LiveData<Event<String>> = _isSuccess

    private val _isFailure = MutableLiveData<Event<String>>()
    val isFailure: LiveData<Event<String>> = _isFailure

    private val _isLoading = MutableLiveData<Event<Boolean>>()
    val isLoading: LiveData<Event<Boolean>> = _isLoading

    fun navigateSignUpToSignUpInfo() {
        val getUserId = userId.get()
        val getPassword = password.get()
        val getPasswordRe = passwordRe.get()
        val getName = name.get()
        val getGrade = grade.get()
        val getKlass = klass.get()
        val getNumber = number.get()

        if (!(getUserId.isNullOrEmpty() && getPassword.isNullOrEmpty() &&
                    getPasswordRe.isNullOrEmpty() && getName.isNullOrEmpty() &&
                    getGrade != 0 && getKlass != 0 && getNumber != 0)
        ) {
            _navigateSignUpToSignUpInfo.call()
        } else {
            _isFailure.value = Event("값이 비어 있지 않은지 확인해주세요.")
        }
    }

    fun signUp(signUpRequest: SignUpRequest) {
        _isLoading.value = Event(true)

        signUpUseCase.buildUseCaseObservable(SignUpUseCase.Params(signUpRequest))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                _isSuccess.value = Event(it.token)
                _isLoading.value = Event(false)
            }, {
                _isFailure.value = Event(it.message?:"")
                _isLoading.value = Event(false)
            }).apply {
                compositeDisposable.add(this)
            }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
