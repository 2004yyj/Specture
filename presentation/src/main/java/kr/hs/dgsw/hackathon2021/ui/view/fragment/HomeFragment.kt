package kr.hs.dgsw.hackathon2021.ui.view.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kr.hs.dgsw.hackathon2021.databinding.FragmentHomeBinding
import kr.hs.dgsw.hackathon2021.ui.view.activity.MainActivity
import kr.hs.dgsw.hackathon2021.ui.view.adapter.HomeViewPagerAdapter
import kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment.HomeViewModel

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
        private val textArray = arrayOf("모집 중", "진행 중", "종료 된 강의")
    }

    private val navController : NavController by lazy {
        findNavController()
    }

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding

    private lateinit var viewPager: ViewPager2
    private lateinit var tablayout: TabLayout
    private lateinit var toolbar: Toolbar

    private val recruitingClassFragment = RecruitingClassFragment.newInstance()
    private val progressingClassFragment = ProgressingClassFragment.newInstance()
    private val endedClassFragment = EndedClassFragment.newInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        viewPager.adapter = HomeViewPagerAdapter(
            this,
            listOf(
                recruitingClassFragment,
                progressingClassFragment,
                endedClassFragment
            )
        )

        TabLayoutMediator(tablayout, viewPager) { tab, position ->
            tab.text = textArray[position]
        }.attach()
    }

    private fun init() {
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        viewPager = binding.viewPagerHome
        tablayout = binding.tablayoutHome
        toolbar = binding.toolbarHome

        val appBarConfiguration = (requireActivity() as MainActivity).appBarConfiguration
        toolbar.setupWithNavController(navController, appBarConfiguration)

    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }
}