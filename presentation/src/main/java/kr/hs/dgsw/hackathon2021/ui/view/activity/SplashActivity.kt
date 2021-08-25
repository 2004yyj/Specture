package kr.hs.dgsw.hackathon2021.ui.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import kr.hs.dgsw.domain.usecase.auth.AutoLoginUseCase
import kr.hs.dgsw.hackathon2021.R
import kr.hs.dgsw.hackathon2021.di.application.MyDaggerApplication
import kr.hs.dgsw.hackathon2021.ui.view.util.InfoHelper
import kr.hs.dgsw.hackathon2021.ui.viewmodel.activity.SplashViewModel
import kr.hs.dgsw.hackathon2021.ui.viewmodel.factory.SplashViewModelFactory
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {
    @Inject
    lateinit var autoLoginUseCase: AutoLoginUseCase
    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        (application as MyDaggerApplication).daggerMyComponent.inject(this)
        init()
    }

    private fun init() {
        viewModel = ViewModelProvider(this, SplashViewModelFactory(autoLoginUseCase))[SplashViewModel::class.java]

        if (!InfoHelper.autoLoginChk) {
            val intent = Intent(this@SplashActivity, IntroActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            viewModel.autoLogin()

            with(viewModel) {
                isSuccess.observe(this@SplashActivity) {
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                isFailure.observe(this@SplashActivity) {
                    Toast.makeText(this@SplashActivity, it, Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@SplashActivity, IntroActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}