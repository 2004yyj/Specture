package kr.hs.dgsw.domain.usecase.lecture

import io.reactivex.rxjava3.core.Single
import kr.hs.dgsw.domain.base.ParamsUseCase
import kr.hs.dgsw.domain.entity.response.Lecture
import kr.hs.dgsw.domain.repository.LectureRepository
import java.lang.reflect.Parameter
import java.time.Month
import javax.inject.Inject

class GetAllLectureByDateUseCase @Inject constructor(
    private val lectureRepository: LectureRepository
): ParamsUseCase<GetAllLectureByDateUseCase.Params, Single<List<Lecture>>>() {
    data class Params(
        val year: Int,
        val month: Int,
        val day: Int
    )

    override fun buildUseCaseObservable(params: Params): Single<List<Lecture>> {
        return lectureRepository.getAllLectureByDate(params.year, params.month, params.day)
    }

}