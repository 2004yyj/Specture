package kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kr.hs.dgsw.domain.entity.response.Lecture
import kr.hs.dgsw.domain.usecase.lecture.GetAllLectureUseCase

class ClassViewModel(
    private val getAllLectureUseCase: GetAllLectureUseCase
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val classList = MutableLiveData(ArrayList<Lecture>())

    private val _isFailure = MutableLiveData<String>()
    val isFailure = _isFailure

    fun getAllClass(state: Int) {
        getAllLectureUseCase.buildUseCaseObservable(GetAllLectureUseCase.Params(state))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                Log.d("getAllClass()", it.toString())
                classList.postValue(it as ArrayList<Lecture>)
            }, {
                _isFailure.postValue(it.message)
            })
            .apply {
                compositeDisposable.add(this)
            }
    }

    fun getClass(lectureId: Int) {

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}