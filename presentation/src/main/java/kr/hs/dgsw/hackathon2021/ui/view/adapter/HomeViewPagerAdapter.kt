package kr.hs.dgsw.hackathon2021.ui.view.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import kr.hs.dgsw.hackathon2021.ui.view.fragment.EndedClassFragment
import kr.hs.dgsw.hackathon2021.ui.view.fragment.ProgressingClassFragment
import kr.hs.dgsw.hackathon2021.ui.view.fragment.RecruitingClassFragment


class HomeViewPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    private val recruitingClassFragment = RecruitingClassFragment.newInstance()
    private val progressingClassFragment = ProgressingClassFragment.newInstance()
    private val endedClassFragment = EndedClassFragment.newInstance()

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> recruitingClassFragment
            1 -> progressingClassFragment
            2 -> endedClassFragment
            else -> throw Exception("위치가 없음")
        }
    }
}