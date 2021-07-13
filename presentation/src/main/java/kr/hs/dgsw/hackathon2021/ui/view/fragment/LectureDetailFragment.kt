package kr.hs.dgsw.hackathon2021.ui.view.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kr.hs.dgsw.domain.usecase.lecture.GetLectureDetailUseCase
import kr.hs.dgsw.hackathon2021.R
import kr.hs.dgsw.hackathon2021.databinding.FragmentLectureDetailBinding
import kr.hs.dgsw.hackathon2021.di.application.MyDaggerApplication
import kr.hs.dgsw.hackathon2021.ui.viewmodel.factory.LectureDetailViewModelFactory
import kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment.LectureDetailViewModel
import javax.inject.Inject

class LectureDetailFragment : Fragment() {

    @Inject
    lateinit var getLectureDetailUseCase: GetLectureDetailUseCase

    companion object {
        fun newInstance() = LectureDetailFragment()
    }

    private lateinit var binding: FragmentLectureDetailBinding

    private lateinit var viewModel: LectureDetailViewModel

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
        viewModel.lectureId = arguments?.getInt("lectureId") as Int
    }

    private fun init() {
        viewModel = ViewModelProvider(this, LectureDetailViewModelFactory(getLectureDetailUseCase))[LectureDetailViewModel::class.java]

        viewModel.lectureDetail.observe(viewLifecycleOwner, {
            binding.tvContentLectureDetail.text = it.content
            binding.tvTitleLectureDetail.text = it.title
            binding.tvUserLectureDetail.text = "김뫄뫄"
            binding.tvParticipantsLectureDetail.text = "10명"
        })

        viewModel.isFailure.observe(viewLifecycleOwner, {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })
    }
}