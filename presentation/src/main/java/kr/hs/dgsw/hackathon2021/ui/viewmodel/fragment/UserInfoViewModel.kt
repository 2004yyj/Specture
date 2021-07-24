package kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kr.hs.dgsw.domain.entity.response.User
import kr.hs.dgsw.domain.usecase.user.GetUserUseCase

class UserInfoViewModel(
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val userData = MutableLiveData<User>()

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

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}