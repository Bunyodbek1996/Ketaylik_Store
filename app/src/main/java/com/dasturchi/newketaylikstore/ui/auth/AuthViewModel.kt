package com.dasturchi.newketaylikstore.ui.auth

import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModel
import com.dasturchi.newketaylikstore.R
import com.dasturchi.newketaylikstore.network.repository.AuthRepository
import com.dasturchi.newketaylikstore.util.ApiException
import com.dasturchi.newketaylikstore.util.Coroutines
import com.dasturchi.newketaylikstore.util.PreferenceProvider

class  AuthViewModel(
    private val repository: AuthRepository,
    private val pref: PreferenceProvider
) : ViewModel() {

    var phone: String? = null
    var code: String? = null
    var authListener: AuthListener? = null

    fun onLoginClick(view: View){
        authListener?.onStarted()
        if (phone.isNullOrEmpty()){
            authListener?.onFailure(view.context.getString(R.string.phone_number))
            return
        }
        phone = "998$phone"
        Coroutines.main {
            try{
                val loginResponse = repository.userLogin(phone!!)
                if (loginResponse.success!!){
                    pref.savePhone(phone!!)
                    authListener?.onSuccess()
                }else{
                    authListener?.onFailure(loginResponse.message!!)
                }
            }catch (e: ApiException){
                authListener?.onFailure(e.message!!)
            }
        }
    }

    fun onLoginClick(context: Context){
        authListener?.onStarted()
        if (phone.isNullOrEmpty()){
            authListener?.onFailure(context.getString(R.string.phone_number))
            return
        }
        phone = "998$phone"
        Coroutines.main {
            try{
                val loginResponse = repository.userLogin(phone!!)
                if (loginResponse.success!!){
                    pref.savePhone(phone!!)
                    authListener?.onSuccess()
                }else{
                    authListener?.onFailure(loginResponse.message!!)
                }
            }catch (e: ApiException){
                authListener?.onFailure(e.message!!)
            }
        }
    }

    fun onConfirmClick(view: View){
        authListener?.onStarted()
        if (code.isNullOrEmpty()){
            authListener?.onFailure(view.context.getString(R.string.enter_otp_code))
            return
        }
        phone = pref.getPhone()
        Coroutines.main {
            try{
                val confirmResponse = repository.userConfirm(phone!!,code!!)
                if (confirmResponse.success!!){
                    val token = confirmResponse.data?.token
                    pref.saveToken(token!!)
                    authListener?.onSuccess()
                }else{
                    val message = confirmResponse.message
                    authListener?.onFailure(message!!)
                }
            }catch (e: ApiException){
                authListener?.onFailure(e.message!!)
            }
        }
    }

}