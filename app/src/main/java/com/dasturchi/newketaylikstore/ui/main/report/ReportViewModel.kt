package com.dasturchi.newketaylikstore.ui.main.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dasturchi.newketaylikstore.model.Order
import com.dasturchi.newketaylikstore.network.repository.MainRepository
import com.dasturchi.newketaylikstore.ui.main.MainListener
import com.dasturchi.newketaylikstore.util.ApiException
import com.dasturchi.newketaylikstore.util.Coroutines
import com.dasturchi.newketaylikstore.util.PreferenceProvider


class ReportViewModel(
    private val repository: MainRepository,
    private val pref: PreferenceProvider
) : ViewModel() {
    var mainListener: MainListener? = null

    private val _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>>
        get() = _orders

    fun loadBetween(from: String, to: String){
        Coroutines.main {
            try {
                mainListener!!.onStarted()
                val response = repository.loadBeetween(pref.getToken()!!,from,to)
                if (response.success!!){
                    val list = response.data
                    _orders.value = list!!
                    mainListener!!.onSuccess()
                }else{
                    mainListener!!.onFailure(response.message!!)
                    if (response.error_code == 9){
                        mainListener!!.onFailure("9")
                    }
                }
            }catch (e: ApiException){
                mainListener!!.onFailure(e.message!!)
            }
        }
    }

    fun loadAllOrder(){
        Coroutines.main {
            try {
                mainListener!!.onStarted()
                val response = repository.loadOldOrders(pref.getToken()!!)
                if (response.success!!){
                    val list = response.data
                    _orders.value = list!!
                    mainListener!!.onSuccess()
                }else{
                    mainListener!!.onFailure(response.message!!)
                    if (response.error_code == 9){
                        mainListener!!.onFailure("9")
                    }
                }
            }catch (e: ApiException){
                mainListener!!.onFailure(e.message!!)
            }
        }
    }

}