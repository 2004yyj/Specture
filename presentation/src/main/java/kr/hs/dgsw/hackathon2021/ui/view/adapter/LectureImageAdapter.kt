package kr.hs.dgsw.hackathon2021.ui.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.hs.dgsw.domain.entity.response.Lecture
import kr.hs.dgsw.hackathon2021.R
import kr.hs.dgsw.hackathon2021.di.util.Address.BASE_URL

class LectureImageAdapter: RecyclerView.Adapter<LectureImageAdapter.ViewHolder>() {

    private val list: ArrayList<String> = ArrayList()

    class ViewHolder(v: View): RecyclerView.ViewHolder(v) {
        val img: ImageView = v.findViewById(R.id.img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.item_lecture_img, parent, false)
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val url = "$BASE_URL${list[position]}"
        holder.img.apply {
            Glide.with(this.context).load("$BASE_URL/image/${list[position]}").into(this)
        }
    }

    override fun getItemCount(): Int = list.size

    fun setList(list: ArrayList<String>) {
        this.list.clear()
        this.list.addAll(list)
    }
}