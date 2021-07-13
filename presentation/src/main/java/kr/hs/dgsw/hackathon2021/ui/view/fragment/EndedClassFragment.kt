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
import kr.hs.dgsw.hackathon2021.databinding.FragmentEndedClassBinding
import kr.hs.dgsw.hackathon2021.databinding.FragmentRecruitingClassBinding
import kr.hs.dgsw.hackathon2021.ui.view.adapter.LectureRecyclerViewAdapter
import kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment.EndedClassViewModel

class EndedClassFragment : Fragment() {

    companion object {
        fun newInstance() = EndedClassFragment()
    }

    private lateinit var binding: FragmentEndedClassBinding

    private lateinit var viewModel: EndedClassViewModel
    private val adapter: LectureRecyclerViewAdapter = LectureRecyclerViewAdapter()

    private val navController: NavController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEndedClassBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EndedClassViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        adapter.setOnClickLectureListener {
            navigateToLectureDetail(it)
        }
    }

    private fun initRecyclerView() {
        binding.rvRecruitingClass.adapter = adapter
    }

    private fun navigateToLectureDetail(id: Int) {
        val bundle = Bundle()
        bundle.putInt("putId", id)
        navController.navigate(R.id.action_homeFragment_to_lectureDetailFragment)
    }
}