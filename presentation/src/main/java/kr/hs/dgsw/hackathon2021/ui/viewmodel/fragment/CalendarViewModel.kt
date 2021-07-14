package kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kr.hs.dgsw.domain.entity.response.Lecture
import kr.hs.dgsw.domain.usecase.lecture.GetAllLectureByDateUseCase

class CalendarViewModel(
    private val getAllLectureByDateUseCase: GetAllLectureByDateUseCase
): ViewModel() {

    private val _isSuccess = MutableLiveData<List<Lecture>>()
    val isSuccess : LiveData<List<Lecture>> = _isSuccess

    private val _isFailure = MutableLiveData<String>()
    val isFailure : LiveData<String> = _isFailure

    fun getAllLectureByDate(year: Int, month: Int, day: Int) {
        getAllLectureByDateUseCase.buildUseCaseObservable(GetAllLectureByDateUseCase.Params(year, month, day))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                _isSuccess.postValue(it)
            } ,{
                _isFailure.postValue(it.message)
            })
    }

}
