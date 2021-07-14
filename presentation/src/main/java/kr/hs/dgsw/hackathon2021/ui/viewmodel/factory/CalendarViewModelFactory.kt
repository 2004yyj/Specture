package kr.hs.dgsw.hackathon2021.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.hs.dgsw.domain.usecase.lecture.GetAllLectureByDateUseCase
import kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment.CalendarViewModel

class CalendarViewModelFactory(
    private val getAllLectureByDateUseCase: GetAllLectureByDateUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(CalendarViewModel::class.java)) {
            CalendarViewModel(getAllLectureByDateUseCase) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}
