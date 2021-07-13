package kr.hs.dgsw.domain.usecase.lecture

import io.reactivex.rxjava3.core.Single
import kr.hs.dgsw.domain.base.BaseUseCase
import kr.hs.dgsw.domain.entity.response.Lecture
import kr.hs.dgsw.domain.repository.LectureRepository
import kr.hs.dgsw.domain.repository.UserRepository
import javax.inject.Inject

class GetAllClassUseCase @Inject constructor(
    private val lectureRepository: LectureRepository
): BaseUseCase<Single<Lecture>>() {
    override fun buildUseCaseObservable(): Single<Lecture> {
        return lectureRepository.getAllLecture()
    }

}