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

class SignUpFragment: Fragment() {

    private lateinit var binding: FragmentSignUpBinding

    private val navController: NavController by lazy {
        findNavController()
    }

    private lateinit var etName: EditText
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var etPasswordRe: EditText
    private lateinit var spnGrade: Spinner
    private lateinit var spnKlass: Spinner
    private lateinit var spnNumber: Spinner
    private lateinit var btnNext: Button

    private fun init() {

        etName = binding.etNameSignUp
        etUsername = binding.etUsernameSignUp
        etPassword = binding.etPasswordSignUp
        etPasswordRe = binding.etPasswordReSignUp
        spnGrade = binding.spinnerGradeSignUp
        spnKlass = binding.spinnerClassSignUp
        spnNumber = binding.spinnerNumberSignUp
        btnNext = binding.btnNextSignUp

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

            val grade = spnGrade.selectedItemPosition
            val klass = spnKlass.selectedItemPosition
            val number = spnNumber.selectedItemPosition

            val notBlankChk = userId.isNotBlank() && password.isNotBlank() && passwordRe.isNotBlank() && name.isNotBlank()
            val spinnerChk = grade != 0 && klass != 0 && number != 0

            if (notBlankChk && spinnerChk) {
                navigateSignUpToInfo(userId, password, name, grade, klass, number)
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
}