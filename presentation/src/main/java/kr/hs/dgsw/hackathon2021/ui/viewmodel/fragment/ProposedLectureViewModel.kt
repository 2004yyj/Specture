package kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kr.hs.dgsw.domain.entity.response.Lecture
import kr.hs.dgsw.domain.usecase.lecture.GetAllLectureProposalByUserIdUseCase

class ProposedLectureViewModel(
    private val getAllLectureProposalByUserIdUseCase: GetAllLectureProposalByUserIdUseCase
): ViewModel() {
    val compositeDisposable = CompositeDisposable()

    private val _onSuccess = MutableLiveData<List<Lecture>>()
    val isSuccess: LiveData<List<Lecture>> = _onSuccess

    private val _onFailure = MutableLiveData<String>()
    val isFailure: LiveData<String> = _onFailure

    fun getAllLecture(userId: String) {
        getAllLectureProposalByUserIdUseCase
            .buildUseCaseObservable(GetAllLectureProposalByUserIdUseCase.Params(userId))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                _onSuccess.postValue(it)
            }, {
                _onFailure.postValue(it.message)
            })
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}