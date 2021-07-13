package kr.hs.dgsw.hackathon2021.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kr.hs.dgsw.domain.usecase.user.LoginUseCase
import kr.hs.dgsw.hackathon2021.R
import kr.hs.dgsw.hackathon2021.databinding.FragmentLoginBinding
import kr.hs.dgsw.hackathon2021.di.application.MyDaggerApplication
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

        viewModel = ViewModelProvider(this, LoginViewModelFactory(loginUseCase))[LoginViewModel::class.java]
        binding
    }

}