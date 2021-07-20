package kr.hs.dgsw.hackathon2021.ui.view.fragment

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.TypedValue
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.chip.Chip
import kr.hs.dgsw.domain.entity.request.SignUpRequest
import kr.hs.dgsw.domain.usecase.user.SignUpUseCase
import kr.hs.dgsw.hackathon2021.R
import kr.hs.dgsw.hackathon2021.databinding.FragmentSignUpInfoBinding
import kr.hs.dgsw.hackathon2021.di.application.MyDaggerApplication
import kr.hs.dgsw.hackathon2021.ui.view.activity.MainActivity
import kr.hs.dgsw.hackathon2021.ui.view.util.InfoHelper
import kr.hs.dgsw.hackathon2021.ui.view.util.asMultipart
import kr.hs.dgsw.hackathon2021.ui.viewmodel.factory.SignUpViewModelFactory
import kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment.SignUpViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import javax.inject.Inject
import kotlin.math.roundToInt


class SignUpInfoFragment : Fragment() {

    @Inject
    lateinit var signUpUseCase: SignUpUseCase

    private lateinit var activityResultLauncher: ActivityResultLauncher<String>

    private lateinit var viewModel: SignUpViewModel
    private lateinit var binding: FragmentSignUpInfoBinding

    private lateinit var multipartBody: MultipartBody.Part

    private lateinit var btnSubmit: Button
    private lateinit var btnImageAdd: CardView
    private lateinit var tvName: TextView
    private lateinit var tvSchool: TextView
    private lateinit var etIntroduce: EditText
    private lateinit var etField: EditText
    private lateinit var fbField: FlexboxLayout

    private fun init() {
        btnSubmit = binding.btnSubmitSignUpInfo
        btnImageAdd = binding.btnImageAddSignUpInfo
        tvName = binding.tvNameSignUpInfo
        tvSchool = binding.tvSchoolSignUpInfo
        etIntroduce = binding.etIntroduceSignUpInfo
        etField = binding.etFieldSignUpInfo
        fbField = binding.fbFieldSignUpInfo

        activityResultLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
            if (it != null) {
                val inputStream = requireActivity().contentResolver.openInputStream(it)
                val image = BitmapFactory.decodeStream(inputStream)
                with(requireActivity()) {
                    multipartBody = it.asMultipart("profile", cacheDir, contentResolver)!!
                }
                binding.ivProfileSignUpInfo.setImageBitmap(image)
            }
        }

        with(viewModel) {
            isSuccess.observe(viewLifecycleOwner, {
                InfoHelper.token = it

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
        binding = FragmentSignUpInfoBinding.inflate(inflater)
        (requireActivity().application as MyDaggerApplication).daggerMyComponent.inject(this)
        viewModel = ViewModelProvider(this, SignUpViewModelFactory(signUpUseCase))[SignUpViewModel::class.java]
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        val arguments = requireArguments()

        val userId = arguments.getString("userId")!!
        val password = arguments.getString("password")!!
        val name = arguments.getString("name")!!
        val grade = arguments.getInt("grade")
        val klass = arguments.getInt("klass")
        val number = arguments.getInt("number")

        tvName.text = name
        tvSchool.text = "${grade}학년 ${klass}반 ${number}번"

        etField.doAfterTextChanged { s ->
            val trimmed = s.toString().trim { it <= ' ' }
            if (trimmed.length > 1 && trimmed.endsWith(",")) {
                fbField.addChip(trimmed.substring(0, trimmed.length - 1))
                s?.clear()
            }
        }

        btnImageAdd.setOnClickListener {
            activityResultLauncher.launch("image/*")
        }

        btnSubmit.setOnClickListener {
            val introduce = etIntroduce.text.toString()
            val field = fbField.getAllText()

            if (introduce.isNotBlank() && field.isNotEmpty()) {
                val mediaType = "text/plain".toMediaType()
                val userIdBody = userId.toRequestBody(mediaType)
                val passwordBody = password.toRequestBody(mediaType)
                val nameBody = name.toRequestBody(mediaType)
                val introduceBody = introduce.toRequestBody(mediaType)
                val fieldList = JSONArray()
                fieldList.put(field)
                val fieldBody = fieldList.toString().toRequestBody(mediaType)

                val signUpRequest =
                    if (this::multipartBody.isInitialized) {
                        SignUpRequest(userIdBody, passwordBody, multipartBody, nameBody, grade, klass, number, fieldBody, introduceBody)
                    } else {
                        SignUpRequest(userIdBody, passwordBody, null, nameBody, grade, klass, number, fieldBody, introduceBody)
                    }
                viewModel.signUp(signUpRequest)
            } else {
                Toast.makeText(context, "빈 값이 없는지 확인해 주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun Int.dpToPx(): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), resources.displayMetrics).roundToInt()

    fun FlexboxLayout.addChip(text: String) {
        val inflater = LayoutInflater.from(context)
        val chip = inflater.inflate(R.layout.layout_chip, null) as Chip

        val layoutParams = ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT)
        layoutParams.marginEnd = 4.dpToPx()
        chip.text = text
        chip.isClickable = true
        chip.isCloseIconVisible = true

        fbField.addView(chip, fbField.childCount - 1, layoutParams)
        chip.setOnCloseIconClickListener { fbField.removeView(chip as View) }
    }

    fun FlexboxLayout.getAllText(): List<String> {
        return (0 until childCount - 1).mapNotNull {
            (getChildAt(it) as Chip).text.toString()
        }
    }

}