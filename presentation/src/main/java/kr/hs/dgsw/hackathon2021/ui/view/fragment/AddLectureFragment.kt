package kr.hs.dgsw.hackathon2021.ui.view.fragment

import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.util.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kr.hs.dgsw.domain.usecase.lecture.PostLectureUseCase
import kr.hs.dgsw.hackathon2021.databinding.FragmentAddLectureBinding
import kr.hs.dgsw.hackathon2021.di.application.MyDaggerApplication
import kr.hs.dgsw.hackathon2021.ui.view.adapter.AddLectureImageAdapter
import kr.hs.dgsw.hackathon2021.ui.view.util.addChip
import kr.hs.dgsw.hackathon2021.ui.view.util.asMultipart
import kr.hs.dgsw.hackathon2021.ui.view.util.getAllText
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

    private val navController by lazy {
        findNavController()
    }

    private lateinit var viewModel: AddLectureViewModel
    private lateinit var binding: FragmentAddLectureBinding

    private lateinit var addLectureImageAdapter: AddLectureImageAdapter
    private lateinit var fabAdd: FloatingActionButton
    private lateinit var etTitle: EditText
    private lateinit var btnProposalDate: Button
    private lateinit var btnStartToEndDate: Button
    private lateinit var etContent: EditText
    private lateinit var etField: EditText
    private lateinit var fbField: FlexboxLayout
    private lateinit var btnSubmit: Button

    private lateinit var materialStartToEndDatePicker: MaterialDatePicker<Pair<Long, Long>>
    private lateinit var materialProposalDatePicker: MaterialDatePicker<Long>

    private val imageList = ArrayList<Uri>()

    private lateinit var rvImageList: RecyclerView

    private val multipartBuilder = MultipartBody.Builder()

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
        observe()

        btnProposalDate.setOnClickListener {
            materialProposalDatePicker.show(requireActivity().supportFragmentManager, "")
        }

        btnStartToEndDate.setOnClickListener {
            materialStartToEndDatePicker.show(requireActivity().supportFragmentManager, "")
        }

        materialProposalDatePicker.addOnPositiveButtonClickListener {
            val sdf = SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA)
            btnProposalDate.text = sdf.format(Date(it))
            viewModel.proposalDate = sdf.format(Date(it))
        }

        materialStartToEndDatePicker.addOnPositiveButtonClickListener {
            val sdf = SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA)
            btnStartToEndDate.text = "${sdf.format(Date(it.first))} - ${sdf.format(Date(it.second))}"
            viewModel.startToEndDates = it
        }

        btnSubmit.setOnClickListener {
            val sdf = SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA)

            val title = etTitle.text.toString()
            val content = etContent.text.toString()
            val fieldTextList = fbField.getAllText()

            val fieldBodyList = ArrayList<RequestBody>()
            fieldTextList.forEach {
                fieldBodyList.add(it.toRequestBody("text/plain".toMediaType()))
            }

            with(viewModel) {
                if (title.isNotBlank() && content.isNotBlank() &&
                    viewModel.proposalDate.isNotEmpty() && fieldBodyList.isNotEmpty() &&
                    viewModel.startToEndDates.first != 0L && viewModel.startToEndDates.second != 0L) {

                    val proposal = sdf.parse(proposalDate)!!.time
                    val start = viewModel.startToEndDates.first
                    val end = viewModel.startToEndDates.second

                    viewModel.postLecture(
                        title.toRequestBody("text/plain".toMediaType()),
                        content.toRequestBody("text/plain".toMediaType()),
                        multipartBuilder.build().parts,
                        fieldBodyList,
                        start,
                        end,
                        proposal
                    )
                } else {
                    Toast.makeText(context, "빈 칸이 없는지 확인해 주세요.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        fabAdd.setOnClickListener {
            activityResultLauncher.launch("image/*")
        }

    }

    private fun observe() {
        viewModel.isSuccess.observe(viewLifecycleOwner) {
            navController.navigateUp()
        }
        viewModel.isFailure.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun init() {
        viewModel = ViewModelProvider(this, AddLectureViewModelFactory(postLectureUseCase))[AddLectureViewModel::class.java]


        addLectureImageAdapter = AddLectureImageAdapter()

        fabAdd = binding.fabAddImage
        etTitle = binding.etTitleAddLecture
        btnProposalDate = binding.btnProposalDateAddLecture
        btnStartToEndDate = binding.btnStartToEndDateAddLecture
        etContent = binding.etContentAddLecture
        btnSubmit = binding.btnSubmitAddLecture
        etField = binding.etFieldAddLecture
        fbField = binding.fbFieldAddLecture

        rvImageList = binding.rvImageAddLecture
        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(rvImageList)

        rvImageList.adapter = addLectureImageAdapter
        rvImageList.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        materialStartToEndDatePicker = MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText("강의 시작일과 종료일을 입력해 주세요.")
            .setSelection(Pair(MaterialDatePicker.todayInUtcMilliseconds(), MaterialDatePicker.todayInUtcMilliseconds()))
            .build()

        materialProposalDatePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("강의 신청 종료일을 입력해 주세요.")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        activityResultLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
            if (it != null) {
                imageList.add(it)
                addLectureImageAdapter.setList(imageList)

                with(requireActivity()) {
                    multipartBuilder.addPart(it.asMultipart("attachmentUrl", cacheDir, contentResolver)!!)
                }
            }
        }

        etField.doAfterTextChanged { s ->
            val trimmed = s.toString().trim { it <= ' ' }
            if (trimmed.length > 1 && trimmed.endsWith(",")) {
                fbField.addChip(
                    resources,
                    isClickable = true,
                    isCloseIconVisible = true,
                    trimmed.substring(0, trimmed.length - 1)
                )
                s?.clear()
            }
        }
    }

}