package com.id.mobileuts.services

import android.content.Context
import android.content.SharedPreferences
import com.id.mobileuts.BuildConfig
import java.util.*

class SPManager(context: Context) {

    companion object {
        private const val app_id = BuildConfig.APPLICATION_ID
        private const val sp_master = "$app_id.session_master"

        const val SP_USER_EMAIL = "user_email"
        const val SP_USER_PASSWORD = "user_password"
    }

    private val mSP: SharedPreferences =
        context.getSharedPreferences(sp_master, Context.MODE_PRIVATE)

    fun putString(key: String, value: String?) {
        val editor = mSP.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun putBoolean(key: String, value: Boolean) {
        val editor = mSP.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getString(key: String, defValue: String? = null): String? {
        return mSP.getString(key, defValue)
    }

    fun getBoolean(key: String, defValue: Boolean = false): Boolean {
        return mSP.getBoolean(key, defValue)
    }

    fun clearSession() {
        mSP.edit().clear().apply()
    }

}