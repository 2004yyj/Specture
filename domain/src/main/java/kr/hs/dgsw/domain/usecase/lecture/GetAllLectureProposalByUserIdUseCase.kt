package kr.hs.dgsw.domain.usecase.lecture

import io.reactivex.rxjava3.core.Single
import kr.hs.dgsw.domain.base.ParamsUseCase
import kr.hs.dgsw.domain.entity.response.Lecture
import kr.hs.dgsw.domain.repository.LectureRepository
import javax.inject.Inject

class GetAllLectureProposalByUserIdUseCase @Inject constructor(
    private val lectureRepository: LectureRepository
): ParamsUseCase<GetAllLectureProposalByUserIdUseCase.Params, Single<List<Lecture>>>() {
    override fun buildUseCaseObservable(params: Params): Single<List<Lecture>> {
        return lectureRepository.getAllLectureProposalByUserId(params.userId)
    }

    data class Params(
        val userId: String
    )
}