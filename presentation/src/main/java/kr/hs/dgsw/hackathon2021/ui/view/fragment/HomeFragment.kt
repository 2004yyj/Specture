package kr.hs.dgsw.hackathon2021.ui.view.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kr.hs.dgsw.hackathon2021.R
import kr.hs.dgsw.hackathon2021.databinding.HomeFragmentBinding
import kr.hs.dgsw.hackathon2021.ui.view.adapter.HomeViewPagerAdapter
import kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment.HomeViewModel
import java.text.SimpleDateFormat

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
        private val textArray = arrayOf("모집 중", "진행 중", "종료 된 강의")
    }

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: HomeFragmentBinding

    private val viewPager: ViewPager2 by lazy {
        binding.viewPagerHome
    }
    private val tablayout: TabLayout by lazy {
        binding.tablayoutHome
    }

    private val recruitingClassFragment = RecruitingClassFragment.newInstance()
    private val progressingClassFragment = ProgressingClassFragment.newInstance()
    private val endedClassFragment = EndedClassFragment.newInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

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

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }
}