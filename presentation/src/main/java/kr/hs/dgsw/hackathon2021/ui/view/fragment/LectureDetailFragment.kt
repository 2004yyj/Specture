package kr.hs.dgsw.hackathon2021.ui.view.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kr.hs.dgsw.domain.entity.response.Lecture
import kr.hs.dgsw.domain.usecase.lecture.GetLectureDetailUseCase
import kr.hs.dgsw.domain.usecase.lecture.PostLectureProposalUseCase
import kr.hs.dgsw.hackathon2021.databinding.FragmentLectureDetailBinding
import kr.hs.dgsw.hackathon2021.di.application.MyDaggerApplication
import kr.hs.dgsw.hackathon2021.ui.view.adapter.LectureAdapter
import kr.hs.dgsw.hackathon2021.ui.view.adapter.LectureImageAdapter
import kr.hs.dgsw.hackathon2021.ui.viewmodel.factory.LectureDetailViewModelFactory
import kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment.LectureDetailViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class LectureDetailFragment : Fragment() {

    @Inject
    lateinit var getLectureDetailUseCase: GetLectureDetailUseCase

    @Inject
    lateinit var postPostLectureDetailUseCase: PostLectureProposalUseCase

    companion object {
        fun newInstance() = LectureDetailFragment()
    }

    private lateinit var binding: FragmentLectureDetailBinding

    private lateinit var viewModel: LectureDetailViewModel

    private val adapter = LectureImageAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        (requireActivity().applicationContext as MyDaggerApplication).daggerMyComponent.inject(this)
        binding = FragmentLectureDetailBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        viewModel.getLectureDetail()
        viewModel.lectureId = arguments?.getInt("lectureId") as Int

        binding.btnParticipateLectureDetail.setOnClickListener {
            viewModel.postLectureProposal()
        }
    }

    private fun init() {
        viewModel = ViewModelProvider(
            this,
            LectureDetailViewModelFactory(getLectureDetailUseCase, postPostLectureDetailUseCase))[LectureDetailViewModel::class.java]

        binding.rvImgLectureDetail.adapter = adapter

        viewModel.lectureDetail.observe(viewLifecycleOwner, {
            setText(it)
            adapter.setList(it.attachmentUrl as ArrayList<String>)
        })

        viewModel.isFailure.observe(viewLifecycleOwner, {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })

        viewModel.singleLiveData.observe(viewLifecycleOwner, {
            Toast.makeText(context, "신청이 완료되었습니다.", Toast.LENGTH_SHORT).show()
        })
    }

    private fun setText(data: Lecture) {
        val output = SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA)

        val started = output.format(data.startDate)!!
        val ended = output.format(data.endDate)!!
        val uploaded = output.format(data.uploadDate)!!
        val proposal = output.format(data.proposal)!!

        binding.tvContentLectureDetail.text = data.content
        binding.tvTitleLectureDetail.text = data.title
        binding.tvUserLectureDetail.text = data.userId  //
        binding.tvParticipantsLectureDetail.text = "10"
        binding.tvEndDateLectureDetail.text = ended
        binding.tvStartDateLectureDetail.text = started
        binding.tvUploadDateLectureDetail.text = uploaded
        binding.tvProposalLectureDetail.text = proposal
    }

}