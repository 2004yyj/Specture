package kr.hs.dgsw.domain.usecase.lecture

import io.reactivex.rxjava3.core.Single
import kr.hs.dgsw.domain.base.BaseUseCase
import kr.hs.dgsw.domain.base.ParamsUseCase
import kr.hs.dgsw.domain.repository.LectureRepository
import javax.inject.Inject
import javax.inject.Singleton

class LectureProposalUseCase @Inject constructor(
    private val lectureRepository: LectureRepository
): ParamsUseCase<Int, Single<Unit?>>() {
}