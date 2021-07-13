package kr.hs.dgsw.hackathon2021.ui.view.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.hs.dgsw.domain.entity.response.Lecture
import kr.hs.dgsw.hackathon2021.R
import kr.hs.dgsw.hackathon2021.databinding.FragmentCalenderBinding
import kr.hs.dgsw.hackathon2021.ui.view.adapter.LectureAdapter
import kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment.CalenderViewModel

class CalenderFragment : Fragment() {

    private lateinit var binding: FragmentCalenderBinding
    private lateinit var viewModel: CalenderViewModel

    private val cvDate: CalendarView by lazy {
        binding.cvDateCalendar
    }

    private val rvDate: RecyclerView by lazy {
        binding.rvDateCalender
    }

    private val lectureAdapter: LectureAdapter by lazy {
        LectureAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalenderBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        cvDate.setOnDateChangeListener { _, year, month, dayOfMonth ->

        }

    }

    private fun init() {
        viewModel = ViewModelProvider(this)[CalenderViewModel::class.java]
        rvDate.adapter = lectureAdapter
        rvDate.layoutManager = LinearLayoutManager(context)
        val lecture = Lecture(1, "asdfsda", "asdfsadf", "adsfasdfaf", "asdfasfaf", 1234124, 1234214, 12341, 124414, 1)
        lectureAdapter.setList(arrayListOf(lecture, lecture, lecture))
    }

}