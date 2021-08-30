package kr.hs.dgsw.hackathon2021.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.Toast.makeText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kr.hs.dgsw.domain.usecase.auth.SignUpUseCase
import kr.hs.dgsw.hackathon2021.R
import kr.hs.dgsw.hackathon2021.databinding.FragmentSignUpBinding
import kr.hs.dgsw.hackathon2021.di.application.MyDaggerApplication
import kr.hs.dgsw.hackathon2021.ui.view.util.EventObserver
import kr.hs.dgsw.hackathon2021.ui.viewmodel.factory.SignUpViewModelFactory
import kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment.SignUpViewModel
import javax.inject.Inject

class SignUpFragment: Fragment() {

    @Inject
    lateinit var signUpUseCase: SignUpUseCase

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity().application as MyDaggerApplication).daggerMyComponent.inject(this)
        binding = FragmentSignUpBinding.inflate(inflater)
        viewModel = ViewModelProvider(requireActivity(), SignUpViewModelFactory(signUpUseCase))[SignUpViewModel::class.java]
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewModel) {
            isFailure.observe(viewLifecycleOwner, EventObserver {
                makeText(context, it, Toast.LENGTH_SHORT).show()
            })

            navigateSignUpToSignUpInfo.observe(viewLifecycleOwner, {
                findNavController().navigate(R.id.action_signUpFragment_to_signUpInfoFragment)
            })
        }
    }
}