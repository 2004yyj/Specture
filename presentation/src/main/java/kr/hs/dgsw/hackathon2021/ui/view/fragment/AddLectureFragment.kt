package kr.hs.dgsw.hackathon2021.ui.view.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.util.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kr.hs.dgsw.domain.usecase.lecture.PostLectureUseCase
import kr.hs.dgsw.hackathon2021.R
import kr.hs.dgsw.hackathon2021.databinding.FragmentAddLectureBinding
import kr.hs.dgsw.hackathon2021.di.application.MyDaggerApplication
import kr.hs.dgsw.hackathon2021.ui.view.adapter.LectureImageAdapter
import kr.hs.dgsw.hackathon2021.ui.view.util.asMultipart
import kr.hs.dgsw.hackathon2021.ui.viewmodel.factory.AddLectureViewModelFactory
import kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment.AddLectureViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class AddLectureFragment : Fragment() {

    companion object {
        fun newInstance() = AddLectureFragment()
    }

    @Inject
    lateinit var postLectureUseCase: PostLectureUseCase

    private lateinit var viewModel: AddLectureViewModel
    private lateinit var binding: FragmentAddLectureBinding

    private lateinit var lectureImageAdapter: LectureImageAdapter
    private lateinit var fabAdd: FloatingActionButton
    private lateinit var etTitle: EditText
    private lateinit var btnProposalDate: Button
    private lateinit var btnStartToEndDate: Button
    private lateinit var etContent: EditText
    private lateinit var etField: EditText
    private lateinit var btnSubmit: Button

    private lateinit var materialDateRangePicker: MaterialDatePicker<Pair<Long, Long>>


    private lateinit var imageList: ArrayList<String>

    private lateinit var rvImageList: RecyclerView

    private lateinit var multipartBuilder: MultipartBody.Builder

    private lateinit var activityResultLauncher: ActivityResultLauncher<String>

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
        init()

        btnProposalDate.setOnClickListener {
            materialDateRangePicker.show(requireActivity().supportFragmentManager, "")
        }

        btnSubmit.setOnClickListener {
            val sdf = SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA)

            val title = etTitle.text.toString()
            val content = etContent.text.toString()
            val proposal = sdf.parse("2021년 8월 30일")?.time
            val start = sdf.parse("2021년 9월 1일")?.time
            val end = sdf.parse("2021년 10월 1일")?.time
            val field = etField.text.toString()

            val fieldList = ArrayList<RequestBody>()
            fieldList.add(field.toRequestBody("text/plain".toMediaType()))

            if (title.isNotBlank() && content.isNotBlank() && proposal != null && start != null && end != null) {
                viewModel.postLecture(
                    title.toRequestBody("text/plain".toMediaType()),
                    content.toRequestBody("text/plain".toMediaType()),
                    multipartBuilder.build().parts as ArrayList<MultipartBody.Part>,
                    fieldList,
                    start,
                    end,
                    proposal
                )
            } else {
                Toast.makeText(context, "빈 칸이 없는지 확인해 주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        fabAdd.setOnClickListener {
            activityResultLauncher.launch("image/*")
        }

    }

    private fun init() {
        viewModel = ViewModelProvider(this, AddLectureViewModelFactory(postLectureUseCase))[AddLectureViewModel::class.java]

        lectureImageAdapter = LectureImageAdapter()

        fabAdd = binding.fabAddImage
        etTitle = binding.etTitleAddLecture
        btnProposalDate = binding.btnProposalDateAddLecture
        btnStartToEndDate = binding.btnStartToEndDateAddLecture
        etContent = binding.etContentAddLecture
        btnSubmit = binding.btnSubmitAddLecture
        etField = binding.etFieldAddLecture
        rvImageList = binding.rvImageAddLecture
        rvImageList.adapter = lectureImageAdapter
        rvImageList.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        materialDateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText("날짜를 설정해 주세요.")
            .setSelection(Pair(MaterialDatePicker.todayInUtcMilliseconds(), MaterialDatePicker.todayInUtcMilliseconds()))
            .build()

        activityResultLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
            if (it != null) {
                imageList.add(it.toString())

                with(requireActivity()) {
                    multipartBuilder.addPart(it.asMultipart("profile", cacheDir, contentResolver)!!)
                }
                lectureImageAdapter.setList(imageList)
            }
        }
    }

}