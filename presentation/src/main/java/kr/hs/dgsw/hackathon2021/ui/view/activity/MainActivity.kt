package kr.hs.dgsw.hackathon2021.ui.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kr.hs.dgsw.hackathon2021.R

class MainActivity : AppCompatActivity() {

    lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bnv = findViewById<BottomNavigationView>(R.id.bnv_main)
        val fragment = findViewById<FragmentContainerView>(R.id.fragment_main).getFragment<NavHostFragment>()!!

        val navController = fragment.findNavController()
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.calenderFragment,
                R.id.userInfoFragment
            )
            ,null
        )
        bnv.setupWithNavController(navController)
    }
}