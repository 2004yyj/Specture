package kr.hs.dgsw.hackathon2021.ui.view.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.hs.dgsw.hackathon2021.R
import kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment.AddLectureViewModel

class AddLectureFragment : Fragment() {

    companion object {
        fun newInstance() = AddLectureFragment()
    }

    private lateinit var viewModel: AddLectureViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_add_lecture, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddLectureViewModel::class.java)
        // TODO: Use the ViewModel
    }

}