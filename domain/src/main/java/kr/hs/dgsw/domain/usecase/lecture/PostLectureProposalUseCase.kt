package kr.hs.dgsw.domain.usecase.lecture

import io.reactivex.rxjava3.core.Single
import kr.hs.dgsw.domain.base.BaseUseCase
import kr.hs.dgsw.domain.base.ParamsUseCase
import kr.hs.dgsw.domain.repository.LectureRepository
import javax.inject.Inject
import javax.inject.Singleton

class PostLectureProposalUseCase @Inject constructor(
    private val lectureRepository: LectureRepository
): ParamsUseCase<Int, Single<Any?>>() {
    override fun buildUseCaseObservable(params: Int): Single<Any?> {
        return lectureRepository.postLectureProposal(params)
    }

}