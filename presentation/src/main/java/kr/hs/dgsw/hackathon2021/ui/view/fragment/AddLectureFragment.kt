package kr.hs.dgsw.hackathon2021.ui.view.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageButton
import kr.hs.dgsw.domain.usecase.lecture.PostLectureUseCase
import kr.hs.dgsw.hackathon2021.databinding.FragmentAddLectureBinding
import kr.hs.dgsw.hackathon2021.di.application.MyDaggerApplication
import kr.hs.dgsw.hackathon2021.ui.viewmodel.factory.AddLectureViewModelFactory
import kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment.AddLectureViewModel
import javax.inject.Inject

class AddLectureFragment : Fragment() {

    companion object {
        fun newInstance() = AddLectureFragment()
    }

    @Inject
    lateinit var postLectureUseCase: PostLectureUseCase

    private lateinit var viewModel: AddLectureViewModel
    private lateinit var binding: FragmentAddLectureBinding

    private lateinit var ibAdd: ImageButton
    private lateinit var etTitle: EditText
    private lateinit var proposalDate: DatePicker
    private lateinit var startDate: DatePicker
    private lateinit var endDate: DatePicker
    private lateinit var etContent: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        (requireActivity().application as MyDaggerApplication).daggerMyComponent.inject(this)

        binding = FragmentAddLectureBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, AddLectureViewModelFactory(postLectureUseCase))[AddLectureViewModel::class.java]
    }

}