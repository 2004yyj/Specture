package kr.hs.dgsw.hackathon2021.ui.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexboxLayout
import kr.hs.dgsw.domain.entity.request.SignUpRequest
import kr.hs.dgsw.domain.usecase.auth.SignUpUseCase
import kr.hs.dgsw.hackathon2021.databinding.FragmentSignUpInfoBinding
import kr.hs.dgsw.hackathon2021.di.application.MyDaggerApplication
import kr.hs.dgsw.hackathon2021.ui.view.activity.MainActivity
import kr.hs.dgsw.hackathon2021.ui.view.util.*
import kr.hs.dgsw.hackathon2021.ui.viewmodel.factory.SignUpViewModelFactory
import kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment.SignUpViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject


class SignUpInfoFragment : Fragment() {

    @Inject
    lateinit var signUpUseCase: SignUpUseCase

    private lateinit var activityResultLauncher: ActivityResultLauncher<String>

    private lateinit var viewModel: SignUpViewModel
    private lateinit var binding: FragmentSignUpInfoBinding

    private lateinit var multipartBody: MultipartBody.Part

    private lateinit var btnSubmit: Button
    private lateinit var btnImageAdd: CardView
    private lateinit var tvName: TextView
    private lateinit var tvSchool: TextView
    private lateinit var etIntroduce: EditText
    private lateinit var etField: EditText
    private lateinit var fbField: FlexboxLayout
    private lateinit var ivProfile: ImageView

    private fun init() {
        btnSubmit = binding.btnSubmitSignUpInfo
        btnImageAdd = binding.btnImageAddSignUpInfo
        tvName = binding.tvNameSignUpInfo
        tvSchool = binding.tvSchoolSignUpInfo
        etIntroduce = binding.etIntroduceSignUpInfo
        etField = binding.etFieldSignUpInfo
        fbField = binding.fbFieldSignUpInfo
        ivProfile = binding.ivProfileSignUpInfo

        activityResultLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
            with(requireActivity()) {
                if (it != null) {
                    multipartBody = it.asMultipart("profile", cacheDir, contentResolver)!!
                    Glide.with(requireContext()).load(it).into(ivProfile)
                }
            }
        }

        with(viewModel) {
            isSuccess.observe(viewLifecycleOwner, EventObserver {
                InfoHelper.token = it

                val intent = Intent(requireActivity(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            })

            isFailure.observe(viewLifecycleOwner, EventObserver {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            })

            isLoading.observe(viewLifecycleOwner, {

            })
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpInfoBinding.inflate(inflater)
        (requireActivity().application as MyDaggerApplication).daggerMyComponent.inject(this)
        viewModel = ViewModelProvider(requireActivity(), SignUpViewModelFactory(signUpUseCase))[SignUpViewModel::class.java]
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        val userId = viewModel.userId.get()!!
        val password = viewModel.password.get()!!
        val name = viewModel.name.get()!!
        val grade = viewModel.grade.get()
        val klass = viewModel.klass.get()
        val number = viewModel.number.get()

        tvName.text = name
        tvSchool.text = "${grade}학년 ${klass}반 ${number}번"

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

        btnImageAdd.setOnClickListener {
            activityResultLauncher.launch("image/*")
        }

        btnSubmit.setOnClickListener {
            val introduce = etIntroduce.text.toString()
            val field = fbField.getAllText()

            if (introduce.isNotBlank() && field.isNotEmpty()) {
                val mediaType = "text/plain".toMediaType()
                val userIdBody = userId.toRequestBody(mediaType)
                val passwordBody = password.toRequestBody(mediaType)
                val nameBody = name.toRequestBody(mediaType)
                val introduceBody = introduce.toRequestBody(mediaType)

                val fieldBody = ArrayList<RequestBody>()
                field.forEach {
                    fieldBody.add(it.toRequestBody(mediaType))
                }

                val signUpRequest =
                    if (this::multipartBody.isInitialized) {
                        SignUpRequest(userIdBody, passwordBody, multipartBody, nameBody, grade, klass, number, fieldBody, introduceBody)
                    } else {
                        SignUpRequest(userIdBody, passwordBody, null, nameBody, grade, klass, number, fieldBody, introduceBody)
                    }
                viewModel.signUp(signUpRequest)
            } else {
                Toast.makeText(context, "빈 값이 없는지 확인해 주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}