package kr.hs.dgsw.hackathon2021.ui.view.adapter

import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.hs.dgsw.domain.entity.response.Lecture
import kr.hs.dgsw.hackathon2021.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class LectureAdapter() : RecyclerView.Adapter<LectureAdapter.ViewHolder>() {

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
        val cardView: CardView = v.findViewById(R.id.cv_lecture_item)
        val tvState: TextView = v.findViewById(R.id.tv_state_item_lecture)
        val img: ImageView = v.findViewById(R.id.img_lecture_item)
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

        val currentTime = System.currentTimeMillis()

        when(data.state) {
            0 -> {
                holder.tvState.setTextColor(Color.parseColor("#747474"))
                holder.tvState.text = "모집 중"
            }
            1 -> {
                holder.tvState.text =
                    if (currentTime < data.startDate) {
                        holder.tvState.setTextColor(Color.parseColor("#FFB300"))
                        "진행 예정"
                    } else {
                        holder.tvState.setTextColor(Color.parseColor("#43A047"))
                        "진행 중"
                    }
            }
            2 -> {
                holder.tvState.setTextColor(Color.parseColor("#F4511E"))
                holder.tvState.text = "종료 됨"
            }
        }

        if (data.attachmentUrl.isNotEmpty()) {
            Glide.with(holder.img.context)
                .load(data.attachmentUrl[0])
                .into(holder.img)
        }

        holder.cardView.setOnClickListener {
            onClickLectureListener.onClick(data.lectureId)
        }
    }

    override fun getItemCount(): Int = list.size

    fun setList(list: ArrayList<Lecture>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

}