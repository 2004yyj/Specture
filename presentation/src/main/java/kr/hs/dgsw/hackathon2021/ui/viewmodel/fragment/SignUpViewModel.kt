package kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment

import android.widget.Toast
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
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class SignUpViewModel(private val signUpUseCase: SignUpUseCase) : ViewModel() {

    val userId = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val passwordRe = MutableLiveData<String>()
    val name = MutableLiveData<String>()
    val grade = MutableLiveData(0)
    val klass = MutableLiveData(0)
    val number = MutableLiveData(0)

    val introduce = MutableLiveData<String>()
    val field = MutableLiveData<String>()
    val fieldList = ArrayList<String>()

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
        val getUserId = userId.value
        val getPassword = password.value
        val getPasswordRe = passwordRe.value
        val getName = name.value
        val getGrade = grade.value
        val getKlass = klass.value
        val getNumber = number.value

        if (!(getUserId.isNullOrEmpty() && getPassword.isNullOrEmpty() &&
                    getPasswordRe.isNullOrEmpty() && getName.isNullOrEmpty() &&
                    getGrade == 0 && getKlass == 0 && getNumber == 0)
        ) {
            _navigateSignUpToSignUpInfo.call()
        } else {
            _isFailure.value = Event("값이 비어 있지 않은지 확인해주세요.")
        }
    }

    fun signUp(multipartBody: MultipartBody.Part?) {
        _isLoading.value = Event(true)

        if (introduce.value != null) {
            val mediaType = "text/plain".toMediaType()
            val userIdBody = userId.value!!.toRequestBody(mediaType)
            val passwordBody = password.value!!.toRequestBody(mediaType)
            val nameBody = name.value!!.toRequestBody(mediaType)
            val introduceBody = introduce.value!!.toRequestBody(mediaType)
            val fieldListBody = ArrayList<RequestBody>().apply {
                addAll(
                    fieldList.map {
                        it.toRequestBody(mediaType)
                    }
                )
            }

            val signUpRequest =
                    SignUpRequest(
                        userIdBody,
                        passwordBody,
                        multipartBody,
                        nameBody,
                        grade.value!!,
                        klass.value!!,
                        number.value!!,
                        fieldListBody,
                        introduceBody
                    )

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
        } else {
            _isFailure.value = Event("빈 값이 없는지 확인해 주세요.")
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
