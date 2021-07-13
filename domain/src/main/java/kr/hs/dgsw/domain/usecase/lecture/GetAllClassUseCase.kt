package kr.hs.dgsw.domain.usecase.lecture

import io.reactivex.rxjava3.core.Single
import kr.hs.dgsw.domain.base.ParamsUseCase
import kr.hs.dgsw.domain.entity.response.Lecture
import kr.hs.dgsw.domain.repository.LectureRepository
import javax.inject.Inject

class GetAllClassUseCase @Inject constructor(
    private val lectureRepository: LectureRepository
): ParamsUseCase<Int, Single<List<Lecture>>>() {
    override fun buildUseCaseObservable(params: Int): Single<List<Lecture>> {
        return lectureRepository.getAllLecture(params)
    }

}