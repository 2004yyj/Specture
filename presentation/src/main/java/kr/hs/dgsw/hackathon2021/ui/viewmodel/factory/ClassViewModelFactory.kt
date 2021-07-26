package kr.hs.dgsw.hackathon2021.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.hs.dgsw.domain.usecase.lecture.GetAllLectureUseCase
import kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment.ClassViewModel

class ClassViewModelFactory(private val getAllLectureUseCase: GetAllLectureUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ClassViewModel::class.java)) {
             ClassViewModel(getAllLectureUseCase) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}