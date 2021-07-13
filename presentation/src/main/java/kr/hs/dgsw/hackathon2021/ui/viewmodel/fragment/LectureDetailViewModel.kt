package kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kr.hs.dgsw.domain.entity.response.Lecture
import kr.hs.dgsw.domain.usecase.lecture.GetLectureDetailUseCase

class LectureDetailViewModel(
    private val getLectureDetailUseCase: GetLectureDetailUseCase
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    val lectureDetail = MutableLiveData<Lecture>()

    private val _isFailure = MutableLiveData<String>()
    val isFailure = _isFailure

    var lectureId: Int = 0

    fun getLectureDetail() {
        getLectureDetailUseCase.buildUseCaseObservable(lectureId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                lectureDetail.postValue(it)
            }, {
                _isFailure.postValue(it.message)
            })
            .apply {
                compositeDisposable.add(this)
            }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}