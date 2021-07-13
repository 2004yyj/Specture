package kr.hs.dgsw.hackathon2021.ui.view.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import kr.hs.dgsw.hackathon2021.ui.view.fragment.EndedClassFragment
import kr.hs.dgsw.hackathon2021.ui.view.fragment.ProgressingClassFragment
import kr.hs.dgsw.hackathon2021.ui.view.fragment.RecruitingClassFragment


class HomeViewPagerAdapter(fragment: Fragment, private val list: List<Fragment>): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            position -> list[position]
            else -> throw Exception("위치가 없음")
        }
    }
}