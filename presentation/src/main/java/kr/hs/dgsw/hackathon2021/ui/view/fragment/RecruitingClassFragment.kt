package kr.hs.dgsw.hackathon2021.ui.view.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kr.hs.dgsw.domain.usecase.lecture.GetAllLectureUseCase
import kr.hs.dgsw.hackathon2021.R
import kr.hs.dgsw.hackathon2021.databinding.FragmentRecruitingClassBinding
import kr.hs.dgsw.hackathon2021.di.application.MyDaggerApplication
import kr.hs.dgsw.hackathon2021.ui.view.adapter.LectureAdapter
import kr.hs.dgsw.hackathon2021.ui.viewmodel.factory.ClassViewModelFactory
import kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment.ClassViewModel
import javax.inject.Inject

class RecruitingClassFragment : Fragment() {

    @Inject
    lateinit var getAllLectureUseCase: GetAllLectureUseCase

    companion object {
        private const val state = 0
        fun newInstance() = RecruitingClassFragment()
    }

    private lateinit var binding: FragmentRecruitingClassBinding
    private lateinit var viewModel: ClassViewModel

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var adapter: LectureAdapter

    private val navController: NavController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecruitingClassBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity().application as MyDaggerApplication).daggerMyComponent.inject(this)

        adapter = LectureAdapter()

        init()
        setVisibility()

        adapter.setOnClickLectureListener {
            navigateToLectureDetail(it)
        }
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.getAllClass(state)
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
            binding.rvRecruitingClass.visibility = View.GONE
        } else {
            binding.imgNoData.visibility = View.GONE
            binding.tvNoData.visibility = View.GONE
            binding.rvRecruitingClass.visibility = View.VISIBLE
        }
    }

    private fun init() {
        viewModel = ViewModelProvider(this, ClassViewModelFactory(getAllLectureUseCase))[ClassViewModel::class.java]

        viewModel.getAllClass(state)

        viewModel.classList.observe(viewLifecycleOwner) {
            adapter.setList(it)
            setVisibility()
            swipeRefreshLayout.isRefreshing = false
        }

        viewModel.isFailure.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        binding.rvRecruitingClass.adapter = adapter
        swipeRefreshLayout = binding.srlRecruitingClass
    }
}