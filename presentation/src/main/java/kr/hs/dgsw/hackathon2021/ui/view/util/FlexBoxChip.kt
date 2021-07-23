package kr.hs.dgsw.hackathon2021.ui.view.util

import android.content.res.Resources
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.chip.Chip
import kr.hs.dgsw.hackathon2021.R
import kotlin.math.roundToInt

fun FlexboxLayout.addChip(
    resources: Resources,
    isClickable: Boolean,
    isCloseIconVisible: Boolean,
    text: String
) {
    val inflater = LayoutInflater.from(context)
    val chip = inflater.inflate(R.layout.layout_chip, null) as Chip

    val layoutParams = ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT)
    layoutParams.marginEnd = 4.dpToPx(resources)
    chip.text = text
    chip.isClickable = isClickable
    chip.isCloseIconVisible = isCloseIconVisible

    this.addView(chip, this.childCount - 1, layoutParams)
    chip.setOnCloseIconClickListener { this.removeView(chip as View) }
}

fun FlexboxLayout.clear() {
    this.removeAllViews()
}

fun FlexboxLayout.getAllText(): ArrayList<String> {
    val arrayList = ArrayList<String>()
    (0 until childCount - 1).mapNotNull {
        arrayList.add((getChildAt(it) as Chip).text.toString())
    }
    return arrayList
}

fun Int.dpToPx(resources: Resources): Int =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), resources.displayMetrics).roundToInt()