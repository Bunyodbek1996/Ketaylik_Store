package com.dasturchi.newketaylikstore.ui.main.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dasturchi.newketaylikstore.network.repository.MainRepository
import com.dasturchi.newketaylikstore.util.PreferenceProvider

class OrderViewModelFactory(
    private val repository: MainRepository,
    private val pref: PreferenceProvider
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return OrderViewModel(repository,pref) as T
    }
}