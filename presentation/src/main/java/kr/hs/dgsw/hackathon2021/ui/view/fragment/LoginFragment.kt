package kr.hs.dgsw.hackathon2021.ui.view.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kr.hs.dgsw.domain.entity.request.LoginRequest
import kr.hs.dgsw.domain.usecase.user.LoginUseCase
import kr.hs.dgsw.hackathon2021.R
import kr.hs.dgsw.hackathon2021.databinding.FragmentLoginBinding
import kr.hs.dgsw.hackathon2021.di.application.MyDaggerApplication
import kr.hs.dgsw.hackathon2021.ui.view.activity.MainActivity
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

    private val btnLogin: Button by lazy {
        binding.btnLoginLogin
    }
    private val etUsername: EditText by lazy {
        binding.etUsernameLogin
    }
    private val etPassword: EditText by lazy {
        binding.etPasswordLogin
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

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            val loginRequest = LoginRequest(username, password)
            viewModel.login(loginRequest)
        }
    }

    private fun init() {
        viewModel = ViewModelProvider(this, LoginViewModelFactory(loginUseCase))[LoginViewModel::class.java]

        viewModel.success.observe(viewLifecycleOwner, {
            val intent = Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        })

        viewModel.failure.observe(viewLifecycleOwner, {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })

        viewModel.loading.observe(viewLifecycleOwner, {

        })
    }

}