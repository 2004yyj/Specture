package kr.hs.dgsw.domain.usecase.lecture

import io.reactivex.rxjava3.core.Single
import kr.hs.dgsw.domain.base.ParamsUseCase
import kr.hs.dgsw.domain.repository.LectureRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class PostLectureUseCase @Inject constructor(
    private val lectureRepository: LectureRepository
): ParamsUseCase<PostLectureUseCase.Params, Single<String>>() {
    data class Params(
        val title: RequestBody,
        val content: RequestBody,
        val attachment: List<MultipartBody.Part>?,
        val field: ArrayList<RequestBody>,
        val startDate: Long,
        val endDate: Long,
        val proposal: Long
    )

    override fun buildUseCaseObservable(params: Params): Single<String> {
        return lectureRepository.postLecture(
            params.title,
            params.content,
            params.attachment,
            params.field,
            params.startDate,
            params.endDate,
            params.proposal
        )
    }
}