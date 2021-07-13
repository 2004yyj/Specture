package kr.hs.dgsw.hackathon2021.ui.view.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.hs.dgsw.hackathon2021.R
import kr.hs.dgsw.hackathon2021.databinding.FragmentLectureDetailBinding
import kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment.LectureDetailViewModel

class LectureDetailFragment : Fragment() {

    companion object {
        fun newInstance() = LectureDetailFragment()
    }

    private lateinit var binding: FragmentLectureDetailBinding

    private lateinit var viewModel: LectureDetailViewModel
    private var lectureId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        lectureId = arguments?.getInt("lectureId") as Int

        binding = FragmentLectureDetailBinding.inflate(inflater)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LectureDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}