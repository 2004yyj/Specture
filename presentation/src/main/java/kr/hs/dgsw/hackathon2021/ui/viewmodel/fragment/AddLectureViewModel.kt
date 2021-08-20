package kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment

import androidx.core.util.Pair
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kr.hs.dgsw.domain.usecase.lecture.PostLectureUseCase
import kr.hs.dgsw.hackathon2021.ui.view.util.SingleLiveEvent
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddLectureViewModel(
    private val postLectureUseCase: PostLectureUseCase
) : ViewModel() {

    var proposalDate: String = ""
    var startToEndDates: Pair<Long, Long> = Pair(0L, 0L)

    private val compositeDisposable = CompositeDisposable()
    val isSuccess = SingleLiveEvent<Any?>()
    private val _isFailure = MutableLiveData<String>()
    val isFailure: LiveData<String> = _isFailure

    fun postLecture(
        title: RequestBody,
        content: RequestBody,
        attachment: List<MultipartBody.Part>?,
        field: ArrayList<RequestBody>,
        startDate: Long,
        endDate: Long,
        proposal: Long
    ) {
        postLectureUseCase.buildUseCaseObservable(PostLectureUseCase.Params(
            title, content, attachment, field, startDate, endDate, proposal
        )).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                isSuccess.call()
            }, {
                _isFailure.postValue(it.message)
            }).apply {
                compositeDisposable.add(this)
            }
    }

    override fun onCleared() {
        super.onCleared()
    }

}