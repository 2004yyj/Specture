package kr.hs.dgsw.hackathon2021.ui.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import kr.hs.dgsw.domain.usecase.user.AutoLoginUseCase
import kr.hs.dgsw.hackathon2021.R
import kr.hs.dgsw.hackathon2021.di.application.MyDaggerApplication
import kr.hs.dgsw.hackathon2021.ui.view.util.InfoHelper.autoLoginChk
import kr.hs.dgsw.hackathon2021.ui.viewmodel.activity.MainViewModel
import kr.hs.dgsw.hackathon2021.ui.viewmodel.factory.MainViewModelFactory
import javax.inject.Inject

class IntroActivity : AppCompatActivity() {

    @Inject
    lateinit var autoLoginUseCase: AutoLoginUseCase
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        (application as MyDaggerApplication).daggerMyComponent.inject(this)
        init()

        if (autoLoginChk) {
            viewModel.autoLogin()
        }
    }

    private fun init() {
        viewModel = ViewModelProvider(this, MainViewModelFactory(autoLoginUseCase))[MainViewModel::class.java]
        with(viewModel) {
            isSuccess.observe(this@IntroActivity) {
                val intent = Intent(this@IntroActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

            isFailure.observe(this@IntroActivity) {
                Toast.makeText(this@IntroActivity, it, Toast.LENGTH_SHORT).show()
            }
        }
    }
}