package kr.hs.dgsw.hackathon2021.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import kr.hs.dgsw.domain.entity.response.User
import kr.hs.dgsw.domain.usecase.user.GetUserUseCase
import kr.hs.dgsw.hackathon2021.R
import kr.hs.dgsw.hackathon2021.databinding.FragmentSettingBinding
import kr.hs.dgsw.hackathon2021.di.application.MyDaggerApplication
import kr.hs.dgsw.hackathon2021.di.util.Address.SERVER_ADDRESS
import kr.hs.dgsw.hackathon2021.ui.view.util.addChip
import kr.hs.dgsw.hackathon2021.ui.view.util.clear
import kr.hs.dgsw.hackathon2021.ui.viewmodel.factory.SettingViewModelFactory
import kr.hs.dgsw.hackathon2021.ui.viewmodel.factory.UserInfoViewModelFactory
import kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment.SettingViewModel
import kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment.UserInfoViewModel
import javax.inject.Inject

class SettingFragment : Fragment() {

    private lateinit var binding: FragmentSettingBinding
    private lateinit var viewModel: SettingViewModel

    private val navController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity().applicationContext as MyDaggerApplication).daggerMyComponent.inject(this)
        binding = FragmentSettingBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        viewModel = ViewModelProvider(this, SettingViewModelFactory())[SettingViewModel::class.java]
    }

    private fun setView(user: User) {
        binding.tvGradeSetting.text = "${user.grade}학년 ${user.klass}반 ${user.number}번"
        binding.tvIntroduceSetting.text = user.introduce
        binding.tvNameSetting.text = user.name

        binding.fbFieldSetting.clear()
        user.field.forEach {
            binding.fbFieldSetting.addChip(
                resources,
                isClickable = true,
                isCloseIconVisible = false,
                it
            )
        }

        Glide.with(binding.root)
            .load("${SERVER_ADDRESS}/image/${user.attachmentUrl}")
            .into(binding.ivProfileSetting)
    }
}