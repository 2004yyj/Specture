package kr.hs.dgsw.hackathon2021.ui.view.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import kr.hs.dgsw.domain.entity.response.User
import kr.hs.dgsw.domain.usecase.account.GetUserUseCase
import kr.hs.dgsw.hackathon2021.R
import kr.hs.dgsw.hackathon2021.databinding.FragmentSettingBinding
import kr.hs.dgsw.hackathon2021.di.application.MyDaggerApplication
import kr.hs.dgsw.hackathon2021.ui.viewmodel.factory.SettingViewModelFactory
import kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment.SettingViewModel
import javax.inject.Inject

class SettingFragment : Fragment() {
    @Inject
    lateinit var getUserUseCase: GetUserUseCase

    companion object {
        fun newInstance() = SettingFragment()
    }

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

        init(view)
        viewModel.getUser()

        binding.card1Setting.setOnClickListener{
            navigateToRecruitingClass()
        }

        binding.card2Setting.setOnClickListener {
            navigateToRecruitingClass()
        }
    }

    private fun init(v: View) {
        viewModel = ViewModelProvider(this, SettingViewModelFactory(getUserUseCase))[SettingViewModel::class.java]

        viewModel.userData.observe(viewLifecycleOwner, {
            setText(it, v)
        })

        viewModel.isFailure.observe(viewLifecycleOwner, {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun setText(data: User, v: View) {
        binding.tvGradeUserInfo.text = "${data.grade}${data.klass}${data.number}"
        binding.tvIntroduceUserInfo.text = data.introduce
        binding.tvNameUserInfo.text = data.name

        Glide.with(v)
            .load(data.attachmentUrl)
            .into(binding.imgUserSetting)
    }

    private fun navigateToRecruitingClass() { //
        navController.navigate(R.id.action_settingFragment_to_recruitingClassFragment)
    }
}