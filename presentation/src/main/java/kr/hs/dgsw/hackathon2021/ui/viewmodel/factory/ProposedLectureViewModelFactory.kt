package kr.hs.dgsw.hackathon2021.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.hs.dgsw.domain.usecase.lecture.GetAllLectureProposalByUserIdUseCase
import kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment.ProposedLectureViewModel

class ProposedLectureViewModelFactory(
    private val getAllLectureProposalByUserIdUseCase: GetAllLectureProposalByUserIdUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ProposedLectureViewModel::class.java)) {
            ProposedLectureViewModel(getAllLectureProposalByUserIdUseCase) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}