package kr.hs.dgsw.hackathon2021.ui.view.helper

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

object InfoHelper {
    private lateinit var sharedPreferences: SharedPreferences
    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences("user", MODE_PRIVATE)
    }

    var token: String?
        get() = sharedPreferences.getString("token", "")
        set(value) {
            val edit = sharedPreferences.edit()
            edit.putString("token", value)
            edit.apply()
        }

}