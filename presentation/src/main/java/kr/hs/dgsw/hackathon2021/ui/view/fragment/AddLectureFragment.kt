package kr.hs.dgsw.hackathon2021.ui.view.fragment

import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kr.hs.dgsw.domain.usecase.lecture.PostLectureUseCase
import kr.hs.dgsw.hackathon2021.databinding.FragmentAddLectureBinding
import kr.hs.dgsw.hackathon2021.di.application.MyDaggerApplication
import kr.hs.dgsw.hackathon2021.ui.view.util.asMultipart
import kr.hs.dgsw.hackathon2021.ui.viewmodel.factory.AddLectureViewModelFactory
import kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment.AddLectureViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
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

    private lateinit var fabAdd: FloatingActionButton
    private lateinit var etTitle: EditText
    private lateinit var proposalDate: DatePicker
    private lateinit var startDate: DatePicker
    private lateinit var endDate: DatePicker
    private lateinit var etContent: EditText
    private lateinit var etField: EditText
    private lateinit var btnSubmit: Button

    private lateinit var imageList: ArrayList<MultipartBody.Part>

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

        btnSubmit.setOnClickListener {
            val sdf = SimpleDateFormat("yyyyMMdd", Locale.KOREA)

            val title = etTitle.text.toString()
            val content = etContent.text.toString()
            val proposal = sdf.parse(proposalDate.year.toString()+String.format("%02d", proposalDate.month)+String.format("%02d", proposalDate.dayOfMonth))?.time
            val start = sdf.parse(startDate.year.toString()+String.format("%02d", startDate.month)+String.format("%02d", startDate.dayOfMonth))?.time
            val end = sdf.parse(endDate.year.toString()+String.format("%02d", endDate.month)+String.format("%02d", endDate.dayOfMonth))?.time
            val field = etField.text.toString()

            val fieldList = JSONArray()
            fieldList.put(field)

            if (title.isNotBlank() && content.isNotBlank() && proposal != null && start != null && end != null) {
                viewModel.postLecture(
                    title.toRequestBody("text/plain".toMediaType()),
                    content.toRequestBody("text/plain".toMediaType()),
                    multipartBuilder.build().parts as ArrayList<MultipartBody.Part>,
                    fieldList.toString().toRequestBody("text/plain".toMediaType()),
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

        fabAdd = binding.fabAddImage
        etTitle = binding.etTitleAddLecture
        proposalDate = binding.proposalDate
        startDate = binding.startDate
        endDate = binding.endDate
        etContent = binding.etContentAddLecture
        btnSubmit = binding.btnSubmitAddLecture
        etField = binding.etFieldAddLecture

        activityResultLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
            if (it != null) {
                val inputStream = requireActivity().contentResolver.openInputStream(it)
                val image = BitmapFactory.decodeStream(inputStream)
                with(requireActivity()) {
                    multipartBuilder.addPart(it.asMultipart("profile", cacheDir, contentResolver)!!)
                }

            }
        }
    }

}