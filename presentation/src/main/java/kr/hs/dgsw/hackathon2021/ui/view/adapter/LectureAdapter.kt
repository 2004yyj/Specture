package kr.hs.dgsw.hackathon2021.ui.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.hs.dgsw.domain.entity.response.Lecture
import kr.hs.dgsw.hackathon2021.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class LectureAdapter : RecyclerView.Adapter<LectureAdapter.ViewHolder>() {

    interface OnLectureListener {
        fun onClick(id: Int)
    }

    private lateinit var onClickLectureListener: OnLectureListener

    fun setOnClickLectureListener(listener: (Int) -> Unit) {
        onClickLectureListener = object : OnLectureListener {
            override fun onClick(id: Int) {
                listener(id)
            }
        }
    }

    private val list: ArrayList<Lecture> = ArrayList()

    class ViewHolder(v: View): RecyclerView.ViewHolder(v) {
        val tvTitle: TextView = v.findViewById(R.id.tv_title_item_lecture_list)
        val tvUser: TextView = v.findViewById(R.id.tv_user_item_lecture_list)
        val tvProposal: TextView = v.findViewById(R.id.tv_proposal_item_lecture_list)
        val tvField: TextView = v.findViewById(R.id.tv_field_item_lecture_list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.item_lecture_list, parent, false)
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        val output = SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA)

        val started = output.format(data.startDate)!!
        val ended = output.format(data.endDate)!!

        holder.tvTitle.text = data.title
        holder.tvUser.text = data.userId
        val fieldString = data.field?.toString()
        val slicedString = fieldString?.slice(1 until fieldString.lastIndex)
        holder.tvField.text = slicedString
        holder.tvProposal.text = "${started} ~ $ended"

    }

    override fun getItemCount(): Int = list.size

    fun setList(list: ArrayList<Lecture>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }
}