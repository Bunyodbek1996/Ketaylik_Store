package com.dasturchi.newketaylikstore.util

import android.content.Context

class PreferenceProvider(
    private val context: Context
) {

    val pref = context.getSharedPreferences("MyPref",Context.MODE_PRIVATE)

    fun saveToken(token: String){
        pref.edit().putString("token",token).apply()
    }
    fun getToken() = pref.getString("token",null)

    fun savePhone(phone: String){
        pref.edit().putString("phone",phone).apply()
    }
    fun getPhone() = pref.getString("phone",null)

    fun saveFCM(fcm: String){
        pref.edit().putString("fcm",fcm).apply()
    }
    fun getFCM() = pref.getString("fcm",null)

    fun clear() {
        pref.edit().clear().apply()
    }

}