package kr.hs.dgsw.domain.usecase.lecture

import io.reactivex.rxjava3.core.Single
import kr.hs.dgsw.domain.base.ParamsUseCase
import kr.hs.dgsw.domain.entity.response.Lecture
import kr.hs.dgsw.domain.repository.LectureRepository
import java.lang.reflect.Parameter
import javax.inject.Inject

class GetLectureDetailUseCase @Inject constructor(
    private val lectureRepository: LectureRepository
): ParamsUseCase<Int, Single<Lecture>>() {
    override fun buildUseCaseObservable(params: Int): Single<Lecture> {
        return lectureRepository.getLectureDetail(params)
    }

}