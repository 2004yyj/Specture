package kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Update
import androidx.viewpager.widget.PagerTitleStrip
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kr.hs.dgsw.domain.entity.request.UpdateUserRequest
import kr.hs.dgsw.domain.entity.response.User
import kr.hs.dgsw.domain.usecase.auth.PasswordChkUseCase
import kr.hs.dgsw.domain.usecase.user.GetUserUseCase
import kr.hs.dgsw.domain.usecase.user.PutUserUseCase
import kr.hs.dgsw.hackathon2021.ui.view.util.SingleLiveEvent
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.ArrayList

class SettingViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val putUserUseCase: PutUserUseCase,
    private val passwordChkUseCase: PasswordChkUseCase
): ViewModel() {
    val introduce = ObservableField<String>()
    val username = ObservableField<String>()
    val name = ObservableField<String>()
    val grade = ObservableField<Int>()
    val klass = ObservableField<Int>()
    val number = ObservableField<Int>()
    val field: ArrayList<String> = ArrayList()

    private val compositeDisposable = CompositeDisposable()

    val userData = MutableLiveData<User>()

    private val _isUpdateUserSuccess = SingleLiveEvent<Unit>()
    val isUpdateUserSuccess: LiveData<Unit> = _isUpdateUserSuccess

    private val _isPasswordChkSuccess = MutableLiveData<String>()
    val isPasswordChkSuccess: LiveData<String> = _isPasswordChkSuccess

    private val _isFailure = MutableLiveData<String>()
    val isFailure: LiveData<String> = _isFailure

    fun getUser() {
        getUserUseCase.buildUseCaseObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                userData.postValue(it)
            }, {
                _isFailure.postValue(it.message)
            }).apply {
                compositeDisposable.add(this)
            }
    }

    fun putUser(password: String, image: MultipartBody.Part?) {
        val contentType = "text/plain".toMediaType()
        val fieldRequestBody = ArrayList<RequestBody>()
        field.forEach {
            val requestBody = it.toRequestBody(contentType)
            fieldRequestBody.add(requestBody)
        }

        val updateUserRequest = UpdateUserRequest(
            username.get()!!.toRequestBody(contentType),
            password.toRequestBody(contentType),
            name.get()!!.toRequestBody(contentType),
            grade.get()!!,
            klass.get()!!,
            number.get()!!,
            introduce.get()!!.toRequestBody(contentType),
            fieldRequestBody,
            image
        )
        putUserUseCase.buildUseCaseObservable(PutUserUseCase.Params(updateUserRequest))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                _isUpdateUserSuccess.call()
            }, {
                _isFailure.postValue(it.message)
            })
    }

    fun passwordChk(password: String) {
        passwordChkUseCase.buildUseCaseObservable(PasswordChkUseCase.Params(password))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                _isPasswordChkSuccess.value = password
            } ,{
                _isFailure.value = it.message
            })
            .apply {
                compositeDisposable.add(this)
            }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
