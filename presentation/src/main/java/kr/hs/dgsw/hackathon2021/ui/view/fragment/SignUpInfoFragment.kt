package kr.hs.dgsw.hackathon2021.ui.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexboxLayout
import kr.hs.dgsw.domain.usecase.auth.SignUpUseCase
import kr.hs.dgsw.hackathon2021.databinding.FragmentSignUpInfoBinding
import kr.hs.dgsw.hackathon2021.di.application.MyDaggerApplication
import kr.hs.dgsw.hackathon2021.ui.view.activity.MainActivity
import kr.hs.dgsw.hackathon2021.ui.view.util.*
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

    private var multipartBody: MultipartBody.Part? = null

    private lateinit var btnSubmit: Button
    private lateinit var btnImageAdd: CardView
    private lateinit var fbField: FlexboxLayout
    private lateinit var ivProfile: ImageView

    private fun init() {
        btnSubmit = binding.btnSubmitSignUpInfo
        btnImageAdd = binding.btnImageAddSignUpInfo
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

            isLoading.observe(viewLifecycleOwner, EventObserver {

            })

            field.observe(viewLifecycleOwner) { s ->
                if (s.isNotEmpty()) {
                    val trimmed = s.toString().trim { it <= ' ' }
                    if (trimmed.length > 1 && trimmed.endsWith(",")) {
                        val text = trimmed.substring(0, trimmed.length - 1)
                        fbField.addChip(
                            resources,
                            isClickable = true,
                            isCloseIconVisible = true,
                            text
                        )
                        viewModel.fieldList.clear()
                        viewModel.fieldList.addAll(fbField.getAllText())
                        field.value = ""
                    }
                }
            }
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
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        btnImageAdd.setOnClickListener {
            activityResultLauncher.launch("image/*")
        }

        btnSubmit.setOnClickListener {
            viewModel.signUp(multipartBody)
        }
    }
}