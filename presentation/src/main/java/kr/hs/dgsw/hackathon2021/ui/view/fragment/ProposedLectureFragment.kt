package kr.hs.dgsw.hackathon2021.ui.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kr.hs.dgsw.domain.entity.response.Lecture
import kr.hs.dgsw.domain.usecase.lecture.GetAllLectureProposalByUserIdUseCase
import kr.hs.dgsw.hackathon2021.R
import kr.hs.dgsw.hackathon2021.databinding.FragmentMyLectureBinding
import kr.hs.dgsw.hackathon2021.databinding.FragmentProposedLectureBinding
import kr.hs.dgsw.hackathon2021.di.application.MyDaggerApplication
import kr.hs.dgsw.hackathon2021.ui.view.activity.MainActivity
import kr.hs.dgsw.hackathon2021.ui.view.adapter.LectureAdapter
import kr.hs.dgsw.hackathon2021.ui.viewmodel.factory.ProposedLectureViewModelFactory
import kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment.ProposedLectureViewModel
import javax.inject.Inject

class ProposedLectureFragment : Fragment() {

    @Inject
    lateinit var getAllLectureProposalByUserIdUseCase: GetAllLectureProposalByUserIdUseCase

    companion object {
        fun newInstance() = ProposedLectureFragment()
    }

    private lateinit var userId: String

    private lateinit var binding: FragmentProposedLectureBinding
    private lateinit var viewModel: ProposedLectureViewModel

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var adapter: LectureAdapter

    private val navController: NavController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProposedLectureBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity().application as MyDaggerApplication).daggerMyComponent.inject(this)

        init()
        setVisibility()

        adapter.setOnClickLectureListener {
            navigateToLectureDetail(it)
        }
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.getAllLecture(userId)
        }
    }


    private fun navigateToLectureDetail(id: Int) {
        val bundle = Bundle()
        bundle.putInt("lectureId", id)
        navController.navigate(R.id.action_homeFragment_to_lectureDetailFragment, bundle)
    }

    private fun setVisibility() {
        if(adapter.itemCount <= 0) {
            binding.imgNoData.visibility = View.VISIBLE
            binding.tvNoData.visibility = View.VISIBLE
            binding.rvProposedLecture.visibility = View.GONE
        } else {
            binding.imgNoData.visibility = View.GONE
            binding.tvNoData.visibility = View.GONE
            binding.rvProposedLecture.visibility = View.VISIBLE
        }
    }

    private fun init() {
        adapter = LectureAdapter()

        viewModel = ViewModelProvider(
            this,
            ProposedLectureViewModelFactory(getAllLectureProposalByUserIdUseCase)
        )[ProposedLectureViewModel::class.java]

        userId = requireArguments().getString("userId", "")

        viewModel.getAllLecture(userId)

        viewModel.isSuccess.observe(viewLifecycleOwner) {
            adapter.setList(it)
            setVisibility()
            swipeRefreshLayout.isRefreshing = false
        }

        viewModel.isFailure.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        binding.rvProposedLecture.adapter = adapter
        swipeRefreshLayout = binding.srlProposedLecture

        val appBarConfiguration = (requireActivity() as MainActivity).appBarConfiguration
        binding.toolbarProposedLecture.setupWithNavController(navController, appBarConfiguration)
    }
}