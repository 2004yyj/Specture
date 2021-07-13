package kr.hs.dgsw.hackathon2021.ui.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.hs.dgsw.hackathon2021.R

class LectureRecyclerViewAdapter : RecyclerView.Adapter<LectureRecyclerViewAdapter.ViewHolder>() {

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

    private val list: ArrayList<String> = ArrayList()

    class ViewHolder(v: View): RecyclerView.ViewHolder(v) {
        val tvTitle: TextView = v.findViewById(R.id.tv_title_item_lecture_list)
        val tvUser: TextView = v.findViewById(R.id.tv_user_item_lecture_list)
        val tvProposalEnd: TextView = v.findViewById(R.id.tv_proposal_end_item_lecture_list)
        val tvField: TextView = v.findViewById(R.id.tv_field_item_lecture_list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.item_lecture_list, parent, false)
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTitle.text
        holder.tvUser.text
        holder.tvProposalEnd.text
        holder.tvField.text
    }

    override fun getItemCount(): Int = list.size

    fun setList(list: ArrayList<String>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }
}