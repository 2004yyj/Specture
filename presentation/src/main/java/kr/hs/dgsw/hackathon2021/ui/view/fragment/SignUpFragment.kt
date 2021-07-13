package kr.hs.dgsw.hackathon2021.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import kr.hs.dgsw.hackathon2021.R
import kr.hs.dgsw.hackathon2021.databinding.FragmentSignUpBinding
import kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment.SignUpViewModel

class SignUpFragment: Fragment() {

    private lateinit var viewModel: SignUpViewModel
    private lateinit var binding: FragmentSignUpBinding

    private val navController: NavController by lazy {
        findNavController()
    }

    private val etName: EditText by lazy {
        binding.etNameSignUp
    }
    private val etUsername: EditText by lazy {
        binding.etUsernameSignUp
    }
    private val etPassword: EditText by lazy {
        binding.etPasswordSignUp
    }
    private val etPasswordRe: EditText by lazy {
        binding.etPasswordReSignUp
    }
    private val spnGrade: Spinner by lazy {
        binding.spinnerGradeSignUp
    }
    private val spnKlass: Spinner by lazy {
        binding.spinnerClassSignUp
    }
    private val spnNumber: Spinner by lazy {
        binding.spinnerNumberSignUp
    }
    private val btnNext: Button by lazy {
        binding.btnNextSignUp
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        btnNext.setOnClickListener {
            val userId = etUsername.text.toString()
            val password = etPassword.text.toString()
            val passwordRe = etPasswordRe.text.toString()
            val name = etName.text.toString()

            val grade = spnGrade.selectedItem.toString()
            val klass = spnKlass.selectedItem.toString()
            val number = spnNumber.selectedItem.toString()

            val notBlankChk = userId.isNotBlank() && password.isNotBlank() && passwordRe.isNotBlank() && name.isNotBlank()
            val spinnerChk = grade != "학년" && klass != "반" && number != "번호"

            if (notBlankChk && spinnerChk) {
                navigateSignUpToInfo(userId, password, name, grade.toInt(), klass.toInt(), number.toInt())
            } else {
                Toast.makeText(context, "값이 비어 있지 않은지 확인해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateSignUpToInfo(
        userId: String,
        password: String,
        name: String,
        grade: Int,
        klass: Int,
        number: Int
    ) {
        val bundle = Bundle()

        bundle.putString("userId", userId)
        bundle.putString("password", password)
        bundle.putString("name", name)
        bundle.putInt("grade", grade)
        bundle.putInt("klass", klass)
        bundle.putInt("number", number)

        navController.navigate(R.id.action_signUpFragment_to_signUpInfoFragment, bundle)
    }

    private fun init() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.grade,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spnGrade.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.klass,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spnKlass.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.number,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spnNumber.adapter = adapter
        }
    }
}