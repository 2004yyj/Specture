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
import kr.hs.dgsw.domain.entity.request.LoginRequest
import kr.hs.dgsw.domain.usecase.auth.LoginUseCase
import kr.hs.dgsw.hackathon2021.R
import kr.hs.dgsw.hackathon2021.databinding.FragmentLoginBinding
import kr.hs.dgsw.hackathon2021.di.application.MyDaggerApplication
import kr.hs.dgsw.hackathon2021.ui.view.activity.MainActivity
import kr.hs.dgsw.hackathon2021.ui.view.util.InfoHelper
import kr.hs.dgsw.hackathon2021.ui.view.util.InfoHelper.autoLoginChk
import kr.hs.dgsw.hackathon2021.ui.viewmodel.factory.LoginViewModelFactory
import kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment.LoginViewModel
import javax.inject.Inject

class LoginFragment : Fragment() {

    @Inject
    lateinit var loginUseCase: LoginUseCase

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel

    private val navController: NavController by lazy {
        findNavController()
    }

    private lateinit var chkAutoLogin: CheckBox
    private lateinit var btnSubmit: Button
    private lateinit var btnSignUp: TextView
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText

    private fun init() {
        viewModel = ViewModelProvider(this, LoginViewModelFactory(loginUseCase))[LoginViewModel::class.java]

        chkAutoLogin = binding.chkAutoLogin
        btnSubmit = binding.btnSubmitLogin
        btnSignUp = binding.btnSignUpLogin
        etUsername = binding.etUsernameLogin
        etPassword = binding.etPasswordLogin

        with(viewModel) {
            chkAutoLogin.isChecked = autoLoginChk

            isSuccess.observe(viewLifecycleOwner, {
                InfoHelper.token = it
                autoLoginChk = chkAutoLogin.isChecked

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity().applicationContext as MyDaggerApplication).daggerMyComponent.inject(this)
        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()


        btnSubmit.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            if(username.isNotBlank() && password.isNotBlank()) {
                val loginRequest = LoginRequest(username, password)
                viewModel.login(loginRequest)
            } else {
                Toast.makeText(context, "아이디 또는 비밀번호가 비어 있습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        btnSignUp.setOnClickListener {
            navigateLoginToSignUp()
        }
    }

    private fun navigateLoginToSignUp() {
        navController.navigate(R.id.action_loginFragment_to_signUpFragment)
    }

}