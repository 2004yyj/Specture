package kr.hs.dgsw.hackathon2021.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.hs.dgsw.domain.usecase.lecture.PostLectureUseCase
import kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment.AddLectureViewModel

class AddLectureViewModelFactory(private val postLectureUseCase: PostLectureUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AddLectureViewModel::class.java)) {
            AddLectureViewModel(postLectureUseCase,) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}