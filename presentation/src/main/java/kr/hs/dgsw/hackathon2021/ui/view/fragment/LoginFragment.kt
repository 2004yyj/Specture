package kr.hs.dgsw.hackathon2021.ui.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import kr.hs.dgsw.domain.usecase.auth.LoginUseCase
import kr.hs.dgsw.hackathon2021.R
import kr.hs.dgsw.hackathon2021.databinding.FragmentLoginBinding
import kr.hs.dgsw.hackathon2021.di.application.MyDaggerApplication
import kr.hs.dgsw.hackathon2021.ui.view.activity.MainActivity
import kr.hs.dgsw.hackathon2021.ui.view.util.EventObserver
import kr.hs.dgsw.hackathon2021.ui.view.util.InfoHelper
import kr.hs.dgsw.hackathon2021.ui.view.util.InfoHelper.autoLoginChk
import kr.hs.dgsw.hackathon2021.ui.viewmodel.factory.LoginViewModelFactory
import kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment.LoginViewModel
import javax.inject.Inject

class LoginFragment : Fragment() {

    @Inject
    lateinit var loginUseCase: LoginUseCase

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity().applicationContext as MyDaggerApplication).daggerMyComponent.inject(this)
        binding = FragmentLoginBinding.inflate(inflater)
        viewModel = ViewModelProvider(this, LoginViewModelFactory(loginUseCase))[LoginViewModel::class.java]
        binding.vm = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewModel) {
            isSuccess.observe(viewLifecycleOwner, EventObserver {
                InfoHelper.token = it
                autoLoginChk = (autoLoginCheck.value == true)

                val intent = Intent(requireActivity(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            })

            isFailure.observe(viewLifecycleOwner, EventObserver {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            })

            navigateLoginToSignUp.observe(viewLifecycleOwner, {
                findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
            })

            isLoading.observe(viewLifecycleOwner, EventObserver {
                // 로딩중
            })
        }
    }

}