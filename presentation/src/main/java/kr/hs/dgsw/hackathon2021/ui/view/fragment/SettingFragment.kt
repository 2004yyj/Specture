package kr.hs.dgsw.hackathon2021.ui.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import kr.hs.dgsw.domain.entity.response.User
import kr.hs.dgsw.domain.usecase.auth.PasswordChkUseCase
import kr.hs.dgsw.domain.usecase.user.GetUserUseCase
import kr.hs.dgsw.hackathon2021.R
import kr.hs.dgsw.hackathon2021.databinding.FragmentSettingBinding
import kr.hs.dgsw.hackathon2021.di.application.MyDaggerApplication
import kr.hs.dgsw.hackathon2021.di.util.Address.SERVER_ADDRESS
import kr.hs.dgsw.hackathon2021.ui.view.activity.MainActivity
import kr.hs.dgsw.hackathon2021.ui.view.util.addChip
import kr.hs.dgsw.hackathon2021.ui.view.util.asMultipart
import kr.hs.dgsw.hackathon2021.ui.view.util.getAllText
import kr.hs.dgsw.hackathon2021.ui.viewmodel.factory.SettingViewModelFactory
import kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment.SettingViewModel
import okhttp3.MultipartBody
import javax.inject.Inject

class SettingFragment : Fragment() {

    @Inject
    lateinit var getUserUseCase: GetUserUseCase

    @Inject
    lateinit var passwordChkUseCase: PasswordChkUseCase

    private lateinit var binding: FragmentSettingBinding
    private lateinit var viewModel: SettingViewModel

    private lateinit var activityResultLauncher: ActivityResultLauncher<String>

    private lateinit var multipartBody: MultipartBody.Part

    private val navController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity().applicationContext as MyDaggerApplication).daggerMyComponent.inject(this)
        binding = FragmentSettingBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        viewModel.getUser()
    }

    private fun init() {
        viewModel = ViewModelProvider(requireActivity(), SettingViewModelFactory(getUserUseCase, passwordChkUseCase))[SettingViewModel::class.java]

        with(viewModel) {
            userData.observe(viewLifecycleOwner) { user ->
                setView(user)
            }
            isFailure.observe(viewLifecycleOwner) {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
            isPasswordChkSuccess.observe(viewLifecycleOwner) {
                // 데이터 전송 코드
            }
        }

        with(binding) {

            btnSubmitSetting.setOnClickListener {
                val grade = spinnerGradeSetting.selectedItemPosition
                val klass = spinnerClassSetting.selectedItemPosition
                val number = spinnerNumberSetting.selectedItemPosition
                val name = etNameSetting.text.toString()
                val username = etUsernameSetting.text.toString()
                val introduce = etIntroduceSetting.text.toString()
                val field = fbFieldSetting.getAllText()

                val intDataChk = grade != 0 && klass != 0 && number != 0
                val textDataChk = name.isNotEmpty() && username.isNotEmpty() && introduce.isNotEmpty()

                if (intDataChk && textDataChk) {
                    val passwordChkFragment = PasswordChkFragment.newInstance()
                    passwordChkFragment.show(requireActivity().supportFragmentManager, "")
                } else {
                    Toast.makeText(context, "빈 값이 없는지 확인해 주세요.", Toast.LENGTH_SHORT).show()
                }
            }

            btnImageAddSetting.setOnClickListener {
                activityResultLauncher.launch("image/*")
            }

            etFieldSetting.doAfterTextChanged { s ->
                val trimmed = s.toString().trim { it <= ' ' }
                if (trimmed.length > 1 && trimmed.endsWith(",")) {
                    fbFieldSetting.addChip(
                        resources,
                        isClickable = true,
                        isCloseIconVisible = true,
                        trimmed.substring(0, trimmed.length - 1)
                    )
                    s?.clear()
                }
            }

            val appBarConfiguration = (requireActivity() as MainActivity).appBarConfiguration
            toolbarSetting.setupWithNavController(navController, appBarConfiguration)

            ArrayAdapter.createFromResource(
                requireContext(),
                R.array.grade,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
                spinnerGradeSetting.adapter = adapter
            }

            ArrayAdapter.createFromResource(
                requireContext(),
                R.array.klass,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
                spinnerClassSetting.adapter = adapter
            }

            ArrayAdapter.createFromResource(
                requireContext(),
                R.array.number,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
                spinnerNumberSetting.adapter = adapter
            }

            activityResultLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
                with(requireActivity()) {
                    if (it != null) {
                        multipartBody = it.asMultipart("profile", cacheDir, contentResolver)!!
                        Glide.with(requireContext()).load(it).into(ivProfileSetting)
                    }
                }
            }
        }
    }

    private fun setView(user: User) {
        with(binding) {
            spinnerGradeSetting.setSelection(user.grade)
            spinnerClassSetting.setSelection(user.klass)
            spinnerNumberSetting.setSelection(user.number)
            etNameSetting.setText(user.name)
            etUsernameSetting.setText(user.userId)
            etIntroduceSetting.setText(user.introduce)

            user.field.forEach {
                binding.fbFieldSetting.addChip(
                    resources,
                    isClickable = true,
                    isCloseIconVisible = true,
                    it
                )
            }

            Glide.with(root)
                .load("${SERVER_ADDRESS}/image/${user.attachmentUrl}")
                .into(ivProfileSetting)
        }
    }
}