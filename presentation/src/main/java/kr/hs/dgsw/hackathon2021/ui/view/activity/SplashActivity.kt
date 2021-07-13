package kr.hs.dgsw.hackathon2021.ui.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.hs.dgsw.hackathon2021.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val intent = Intent(this, MainActivity::class.java)
        // TODO IntroActivity로 이동하도록 수정
        startActivity(intent)
        finish()
    }
}