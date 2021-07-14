package kr.hs.dgsw.hackathon2021.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.hs.dgsw.domain.usecase.lecture.GetLectureDetailUseCase
import kr.hs.dgsw.domain.usecase.lecture.PostLectureProposalUseCase
import kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment.LectureDetailViewModel
import java.lang.IllegalArgumentException

class LectureDetailViewModelFactory(
    private val getLectureDetailUseCase: GetLectureDetailUseCase,
    private val postLectureProposalUseCase: PostLectureProposalUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(LectureDetailViewModel::class.java)) {
            LectureDetailViewModel(getLectureDetailUseCase, postLectureProposalUseCase) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}