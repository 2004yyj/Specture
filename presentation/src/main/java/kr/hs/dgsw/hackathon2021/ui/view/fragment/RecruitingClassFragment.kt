package kr.hs.dgsw.hackathon2021.ui.view.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import kr.hs.dgsw.domain.usecase.lecture.GetAllClassUseCase
import kr.hs.dgsw.hackathon2021.R
import kr.hs.dgsw.hackathon2021.databinding.FragmentRecruitingClassBinding
import kr.hs.dgsw.hackathon2021.di.application.MyDaggerApplication
import kr.hs.dgsw.hackathon2021.ui.view.adapter.LectureRecyclerViewAdapter
import kr.hs.dgsw.hackathon2021.ui.viewmodel.factory.RecruitingClassViewModelFactory
import kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment.RecruitingClassViewModel
import javax.inject.Inject

class RecruitingClassFragment : Fragment() {

    @Inject
    lateinit var getAllClassUseCase: GetAllClassUseCase

    companion object {
        fun newInstance() = RecruitingClassFragment()
    }

    private lateinit var binding: FragmentRecruitingClassBinding

    private lateinit var viewModel: RecruitingClassViewModel
    private val adapter: LectureRecyclerViewAdapter = LectureRecyclerViewAdapter()

    private val navController: NavController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecruitingClassBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity().application as MyDaggerApplication).daggerMyComponent.inject(this)
        viewModel = ViewModelProvider(this, RecruitingClassViewModelFactory(getAllClassUseCase))[RecruitingClassViewModel::class.java]

        init()

        setVisibility()
        setClass()

        adapter.setOnClickLectureListener {
            navigateToLectureDetail(it)
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
        viewModel = ViewModelProvider(this, RecruitingClassViewModelFactory(getAllClassUseCase))[RecruitingClassViewModel::class.java]

        viewModel.classList.observe(viewLifecycleOwner) {
            adapter.setList(it)
        }

        viewModel.isFailure.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        binding.rvRecruitingClass.adapter = adapter
    }

    private fun setClass() {
        viewModel.getAllClass()
    }
}