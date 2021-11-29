package com.dasturchi.newketaylikstore.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dasturchi.newketaylikstore.model.SingleOrder
import com.dasturchi.newketaylikstore.network.repository.MainRepository
import com.dasturchi.newketaylikstore.network.response.FCMResponse
import com.dasturchi.newketaylikstore.util.ApiException
import com.dasturchi.newketaylikstore.util.Coroutines
import com.dasturchi.newketaylikstore.util.PreferenceProvider


class MainViewModel(
    private val repository: MainRepository,
    private val pref: PreferenceProvider
) : ViewModel() {
    var listener: MainListener? = null

    private val _status = MutableLiveData<Int>()
    val status: LiveData<Int>
        get() = _status

    private val _name = MutableLiveData<String>()
    val name: LiveData<String>
        get() = _name

    private val _singleOrder = MutableLiveData<SingleOrder>()
    val singleOrder: LiveData<SingleOrder>
        get() = _singleOrder

    private val _isReceived = MutableLiveData<Boolean>()
    val isReceived: LiveData<Boolean>
        get() = _isReceived

    private val _isReady = MutableLiveData<Boolean>()
    val isReady: LiveData<Boolean>
        get() = _isReady

    private val _isCancel = MutableLiveData<Boolean>()
    val isCancel: LiveData<Boolean>
        get() = _isCancel


    fun setStoreStatus(){
        Coroutines.main {
            try {
                val response = repository.setStoreStatus(pref.getToken()!!)
                if (response.success!!){
                    val status = response.data!!.status
                    _status.value = status ?: 1
                }else{
                }
            }catch (e: ApiException){
            }
        }
    }
    fun getStoreStatus(){
        Coroutines.main {
            try {
                val response = repository.getStoreStatus(pref.getToken()!!)
                if (response.success!!){
                    val status = response.data!!.restaurant?.status
                    _status.value = status ?: 1
                    val name = response.data!!.restaurant?.name_uz
                    _name.value = name!!
                }else{

                }
            }catch (e: ApiException){

            }
        }
    }

    fun loadSingleOrder(id: String){
        listener?.onStarted()
        Coroutines.main {
            try {
                val response = repository.loadSingleOrder(pref.getToken()!!,id)
                if (response.success!!){
                    val singleOrder = response.data
                    _singleOrder.value = singleOrder!!
                    listener?.onSuccess()
                }else{
                    listener?.onFailure(response.message!!)
                }
            }catch (e: ApiException){
               listener?.onFailure(e.message!!)
            }
        }
    }

    fun senFCM(token: String?) {
        Coroutines.main {
            try {
                val response: FCMResponse = repository.senFCM(pref.getToken()!!,token)
                if (response.success!!){
                    pref.saveFCM("send")
                }
            }catch (e: ApiException){}
        }
    }

    fun receiveOrder(id: String){
        listener?.onStarted()
        Coroutines.main {
            try {
                val response = repository.receiveOrder(pref.getToken()!!,id)
                if (response.success!!){
                    _isReceived.value = true
                    listener?.onSuccess()
                }else{
                    listener?.onFailure(response.message!!)
                }
            }catch (e:ApiException){
                listener?.onFailure(e.message!!)
            }
        }
    }

    fun readyOrder(id: String){
        listener?.onStarted()
        Coroutines.main {
            try {
                val response = repository.readyOrder(pref.getToken()!!,id)
                if (response.success!!){
                    _isReady.value = true
                    listener?.onSuccess()
                }else{
                    listener?.onFailure(response.message!!)
                }
            }catch (e: ApiException){
                listener?.onFailure(e.message!!)
            }
        }
    }
    fun cancelOrder(id: String){
        listener?.onStarted()
        Coroutines.main {
            try {
                val response = repository.cancelOrder(pref.getToken()!!,id)
                if (response.success!!){
                    _isCancel.value = true
                    listener?.onSuccess()
                }else{
                    listener?.onFailure(response.message!!)
                }
            }catch (e:ApiException){
                listener?.onFailure(e.message!!)
            }
        }
    }
}
