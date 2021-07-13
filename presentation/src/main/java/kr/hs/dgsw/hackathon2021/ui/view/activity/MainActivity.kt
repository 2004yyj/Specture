package kr.hs.dgsw.hackathon2021.ui.view.activity

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kr.hs.dgsw.hackathon2021.R
import kr.hs.dgsw.hackathon2021.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bnv = findViewById<BottomNavigationView>(R.id.bnv_main)
        val fragment = findViewById<FragmentContainerView>(R.id.fragment_main).getFragment<NavHostFragment>()!!

        val navController = fragment.findNavController()
        bnv.setupWithNavController(navController)
    }
}