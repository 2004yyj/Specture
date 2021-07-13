package kr.hs.dgsw.hackathon2021.ui.view.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.hs.dgsw.hackathon2021.R
import kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment.RecruitingClassViewModel

class RecruitingClassFragment : Fragment() {

    companion object {
        fun newInstance() = RecruitingClassFragment()
    }

    private lateinit var viewModel: RecruitingClassViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.recruiting_class_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RecruitingClassViewModel::class.java)
        // TODO: Use the ViewModel
    }

}