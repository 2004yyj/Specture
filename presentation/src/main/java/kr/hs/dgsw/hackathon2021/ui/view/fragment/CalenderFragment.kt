package kr.hs.dgsw.hackathon2021.ui.view.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.hs.dgsw.domain.entity.response.Lecture
import kr.hs.dgsw.domain.usecase.lecture.GetAllLectureByDateUseCase
import kr.hs.dgsw.hackathon2021.databinding.FragmentCalenderBinding
import kr.hs.dgsw.hackathon2021.di.application.MyDaggerApplication
import kr.hs.dgsw.hackathon2021.ui.view.activity.MainActivity
import kr.hs.dgsw.hackathon2021.ui.view.adapter.LectureAdapter
import kr.hs.dgsw.hackathon2021.ui.viewmodel.factory.CalendarViewModelFactory
import kr.hs.dgsw.hackathon2021.ui.viewmodel.fragment.CalendarViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class CalenderFragment : Fragment() {


    @Inject
    lateinit var getAllLectureByDateUseCase: GetAllLectureByDateUseCase

    private lateinit var binding: FragmentCalenderBinding
    private lateinit var viewModel: CalendarViewModel

    private lateinit var cvDate: CalendarView
    private lateinit var rvDate: RecyclerView
    private lateinit var toolbar: Toolbar

    private val lectureAdapter: LectureAdapter by lazy {
        LectureAdapter()
    }

    private val navController: NavController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalenderBinding.inflate(inflater)
        (requireActivity().application as MyDaggerApplication).daggerMyComponent.inject(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        cvDate.setOnDateChangeListener { _, year, month, dayOfMonth ->
            viewModel.getAllLectureByDate(year, month + 1, dayOfMonth)
        }

    }

    private fun init() {
        viewModel = ViewModelProvider(this, CalendarViewModelFactory(getAllLectureByDateUseCase))[CalendarViewModel::class.java]

        cvDate = binding.cvDateCalendar
        rvDate = binding.rvDateCalender
        toolbar = binding.toolbarCalendar

        val year = SimpleDateFormat("yyyy", Locale.KOREA).format(cvDate.date).toInt()
        val month = SimpleDateFormat("MM", Locale.KOREA).format(cvDate.date).toInt()
        val day = SimpleDateFormat("dd", Locale.KOREA).format(cvDate.date).toInt()

        viewModel.getAllLectureByDate(year, month, day)
        rvDate.adapter = lectureAdapter
        rvDate.layoutManager = LinearLayoutManager(context)

        val appBarConfiguration = (requireActivity() as MainActivity).appBarConfiguration
        toolbar.setupWithNavController(navController, appBarConfiguration)

        viewModel.isSuccess.observe(viewLifecycleOwner) {
            lectureAdapter.setList(it as ArrayList<Lecture>)
        }
    }

}