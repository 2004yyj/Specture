package kr.hs.dgsw.hackathon2021.ui.view.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import kr.hs.dgsw.hackathon2021.R
import kr.hs.dgsw.hackathon2021.databinding.RecruitingClassFragmentBinding
import kr.hs.dgsw.hackathon2021.ui.view.adapter.LectureRecyclerViewAdapter
import kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment.RecruitingClassViewModel

class RecruitingClassFragment : Fragment() {

    companion object {
        fun newInstance() = RecruitingClassFragment()
    }

    private lateinit var binding: RecruitingClassFragmentBinding

    private lateinit var viewModel: RecruitingClassViewModel
    private val adapter: LectureRecyclerViewAdapter = LectureRecyclerViewAdapter()

    private val navController: NavController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RecruitingClassFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RecruitingClassViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        adapter.setOnClickLectureListener {
        }
    }

    private fun initRecyclerView() {
        binding.rvRecruitingClass.adapter = adapter
    }

    private fun navigateToLectureDetail() {
        navController.navigate(R.id.action_homeFragment_to_lectureDetailFragment)
    }
}