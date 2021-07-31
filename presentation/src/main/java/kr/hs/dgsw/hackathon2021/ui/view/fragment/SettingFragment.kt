package kr.hs.dgsw.hackathon2021.ui.view.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.size
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import kr.hs.dgsw.domain.entity.response.User
import kr.hs.dgsw.domain.usecase.auth.PasswordChkUseCase
import kr.hs.dgsw.domain.usecase.user.GetUserUseCase
import kr.hs.dgsw.domain.usecase.user.PutUserUseCase
import kr.hs.dgsw.hackathon2021.R
import kr.hs.dgsw.hackathon2021.databinding.FragmentSettingBinding
import kr.hs.dgsw.hackathon2021.di.application.MyDaggerApplication
import kr.hs.dgsw.hackathon2021.di.util.Address.SERVER_ADDRESS
import kr.hs.dgsw.hackathon2021.ui.view.activity.IntroActivity
import kr.hs.dgsw.hackathon2021.ui.view.activity.MainActivity
import kr.hs.dgsw.hackathon2021.ui.view.util.InfoHelper.autoLoginChk
import kr.hs.dgsw.hackathon2021.ui.view.util.InfoHelper.token
import kr.hs.dgsw.hackathon2021.ui.view.util.addChip
import kr.hs.dgsw.hackathon2021.ui.view.util.asMultipart
import kr.hs.dgsw.hackathon2021.ui.view.util.clear
import kr.hs.dgsw.hackathon2021.ui.view.util.getAllText
import kr.hs.dgsw.hackathon2021.ui.viewmodel.factory.SettingViewModelFactory
import kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment.SettingViewModel
import okhttp3.MultipartBody
import javax.inject.Inject

class SettingFragment : Fragment() {

    @Inject
    lateinit var getUserUseCase: GetUserUseCase

    @Inject
    lateinit var putUserUseCase: PutUserUseCase

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        viewModel.getUser()
    }

    private fun init() {
        requireActivity().viewModelStore.clear()
        viewModel = ViewModelProvider(requireActivity(), SettingViewModelFactory(getUserUseCase, putUserUseCase, passwordChkUseCase))[SettingViewModel::class.java]
        binding.vm = viewModel

        with(viewModel) {
            userData.observe(viewLifecycleOwner) { user ->
                setView(user)
            }
            isFailure.observe(viewLifecycleOwner) {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
            isPasswordChkSuccess.observe(viewLifecycleOwner) {
                if (this@SettingFragment::multipartBody.isInitialized) {
                    putUser(it, multipartBody)
                } else {
                    putUser(it, null)
                }
            }
            isUpdateUserSuccess.observe(viewLifecycleOwner) {
                if (token != null) token = null

                val intent = Intent(context, IntroActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }

        with(binding) {

            btnSubmitSetting.setOnClickListener {

                val intDataChk = with(viewModel) { grade.get() != 0 && klass.get() != 0 && number.get() != 0 }
                val textDataChk = with(viewModel) {
                    !name.get().isNullOrEmpty() && !username.get()
                        .isNullOrEmpty() && !introduce.get().isNullOrEmpty()
                    field.addAll(binding.fbFieldSetting.getAllText())
                }

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
            viewModel.grade.set(user.grade)
            viewModel.klass.set(user.klass)
            viewModel.number.set(user.number)
            viewModel.name.set(user.name)
            viewModel.username.set(user.userId)
            viewModel.introduce.set(user.introduce)

            user.field.forEach {
                fbFieldSetting.addChip(
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