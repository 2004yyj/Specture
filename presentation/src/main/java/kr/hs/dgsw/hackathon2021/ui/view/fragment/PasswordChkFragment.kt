package kr.hs.dgsw.hackathon2021.ui.view.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import kr.hs.dgsw.domain.usecase.auth.PasswordChkUseCase
import kr.hs.dgsw.domain.usecase.user.GetUserUseCase
import kr.hs.dgsw.hackathon2021.databinding.FragmentPasswordChkBinding
import kr.hs.dgsw.hackathon2021.di.application.MyDaggerApplication
import kr.hs.dgsw.hackathon2021.ui.viewmodel.factory.SettingViewModelFactory
import kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment.SettingViewModel
import javax.inject.Inject

class PasswordChkFragment : DialogFragment() {

    companion object {
        fun newInstance() = PasswordChkFragment()
    }

    @Inject
    lateinit var getUserUseCase: GetUserUseCase

    @Inject
    lateinit var passwordChkUseCase: PasswordChkUseCase

    private lateinit var viewModel: SettingViewModel
    private lateinit var binding: FragmentPasswordChkBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        (requireActivity().application as MyDaggerApplication).daggerMyComponent.inject(this)
        viewModel = ViewModelProvider(requireActivity(), SettingViewModelFactory(getUserUseCase, passwordChkUseCase))[SettingViewModel::class.java]
        val inflater = requireActivity().layoutInflater
        binding = FragmentPasswordChkBinding.inflate(inflater)

        val dialogBuilder = AlertDialog.Builder(context)

        dialogBuilder
            .setTitle("비밀번호 확인")
            .setView(binding.root)
            .setPositiveButton("확인") { dialog, which ->
                viewModel.passwordChk(binding.etPasswordPasswordChk.text.toString())
            }
            .setNegativeButton("취소") { dialog, which ->
                dismiss()
            }

        return dialogBuilder.create()
    }
}