package com.dasturchi.newketaylikstore.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dasturchi.newketaylikstore.network.repository.AuthRepository
import com.dasturchi.newketaylikstore.util.PreferenceProvider

class AuthViewModelFactory(
    private val repository: AuthRepository,
    private val pref: PreferenceProvider
) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(repository,pref) as T
        }
}