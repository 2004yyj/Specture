package kr.hs.dgsw.hackathon2021.ui.view.fragment

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import kr.hs.dgsw.domain.entity.request.SignUpRequest
import kr.hs.dgsw.domain.usecase.user.SignUpUseCase
import kr.hs.dgsw.hackathon2021.R
import kr.hs.dgsw.hackathon2021.databinding.FragmentSignUpBinding
import kr.hs.dgsw.hackathon2021.databinding.FragmentSignUpInfoBinding
import kr.hs.dgsw.hackathon2021.di.application.MyDaggerApplication
import kr.hs.dgsw.hackathon2021.ui.view.activity.MainActivity
import kr.hs.dgsw.hackathon2021.ui.view.util.InfoHelper
import kr.hs.dgsw.hackathon2021.ui.view.util.asMultipart
import kr.hs.dgsw.hackathon2021.ui.viewmodel.factory.SignUpViewModelFactory
import kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment.SignUpViewModel
import okhttp3.MultipartBody
import javax.inject.Inject

class SignUpInfoFragment : Fragment() {

    @Inject
    lateinit var signUpUseCase: SignUpUseCase

    private lateinit var activityResultLauncher: ActivityResultLauncher<String>

    private lateinit var viewModel: SignUpViewModel
    private lateinit var binding: FragmentSignUpInfoBinding

    private lateinit var multipartBody: MultipartBody.Part

    private val btnSubmit: Button by lazy {
        binding.btnSubmitSignUpInfo
    }

    private val btnImageAdd: CardView by lazy {
        binding.btnImageAddSignUpInfo
    }

    private val tvName: TextView by lazy {
        binding.tvNameSignUpInfo
    }

    private val tvSchool: TextView by lazy {
        binding.tvSchoolSignUpInfo
    }

    private val etIntroduce: EditText by lazy {
        binding.etIntroduceSignUpInfo
    }

    private val etKind: EditText by lazy {
        binding.etKindSignUpInfo
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpInfoBinding.inflate(inflater)
        (requireActivity().application as MyDaggerApplication).daggerMyComponent.inject(this)
        viewModel = ViewModelProvider(this, SignUpViewModelFactory(signUpUseCase))[SignUpViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arguments = requireArguments()

        val userId = arguments.getString("userId")!!
        val password = arguments.getString("password")!!
        val name = arguments.getString("name")!!
        val grade = arguments.getInt("grade")
        val klass = arguments.getInt("klass")
        val number = arguments.getInt("number")

        tvName.text = name
        tvSchool.text = "${grade}학년 ${klass}반 ${number}번"

        btnImageAdd.setOnClickListener {
            activityResultLauncher.launch("image/*")
        }

        btnSubmit.setOnClickListener {
            val introduce = etIntroduce.text.toString()
            val kind = etKind.text.toString()

            if (introduce.isNotBlank() && kind.isNotBlank()) {
                val signUpRequest = if (this::multipartBody.isInitialized) {
                    SignUpRequest(userId, password, multipartBody, name, grade, klass, number, introduce, kind)
                } else {
                    SignUpRequest(userId, password, null, name, grade, klass, number, introduce, kind)
                }
                viewModel.signUp(signUpRequest)
            } else {
                Toast.makeText(context, "빈 칸이 없는지 확인해 주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        activityResultLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
            if (it != null) {
                val inputStream = requireActivity().contentResolver.openInputStream(it)
                val image = BitmapFactory.decodeStream(inputStream)
                with(requireActivity()) {
                    multipartBody = it.asMultipart("profileImage", cacheDir, contentResolver)!!
                }
                binding.ivProfileSignUpInfo.setImageBitmap(image)
            }
        }

        with(viewModel) {
            isSuccess.observe(viewLifecycleOwner, {
                InfoHelper.token = it

                val intent = Intent(requireActivity(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            })

            isFailure.observe(viewLifecycleOwner, {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            })

            isLoading.observe(viewLifecycleOwner, {

            })
        }
    }

}