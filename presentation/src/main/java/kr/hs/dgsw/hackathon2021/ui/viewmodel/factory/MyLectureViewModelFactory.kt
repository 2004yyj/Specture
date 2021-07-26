package kr.hs.dgsw.hackathon2021.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.hs.dgsw.domain.usecase.lecture.GetAllLectureByUserIdUseCase
import kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment.MyLectureViewModel

class MyLectureViewModelFactory(
    private val getAllLectureByUserIdUseCase: GetAllLectureByUserIdUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(MyLectureViewModel::class.java)) {
            MyLectureViewModel(getAllLectureByUserIdUseCase) as T
        } else {
            throw IllegalArgumentException()
        }
    }

}
