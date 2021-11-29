package com.dasturchi.newketaylikstore.ui.main.product


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dasturchi.newketaylikstore.model.AllProduct
import com.dasturchi.newketaylikstore.network.repository.MainRepository
import com.dasturchi.newketaylikstore.ui.main.MainListener
import com.dasturchi.newketaylikstore.util.ApiException
import com.dasturchi.newketaylikstore.util.Coroutines
import com.dasturchi.newketaylikstore.util.PreferenceProvider

class ProductViewModel(
    private val repository: MainRepository,
    private val pref: PreferenceProvider
) : ViewModel() {
    var mainListener: MainListener? = null

    private val _products = MutableLiveData<List<AllProduct>>()
    val products: LiveData<List<AllProduct>>
        get() = _products

    fun loadProducts(){
        Coroutines.main {
            try {
                mainListener!!.onStarted()
                val response = repository.loadProducts(pref.getToken()!!)
                if (response.success!!){
                    val list = response.data
                    val muatbleList : MutableList<AllProduct> = ArrayList()
                    for (repro in list!!){
                        muatbleList.addAll(repro.all_products!!)
                    }
                    _products.value = muatbleList!!
                    mainListener!!.onSuccess()
                }else{
                    mainListener!!.onFailure(response.message!!)
                }
            }catch (e: ApiException){
                mainListener!!.onFailure(e.message!!)
            }
        }
    }
}