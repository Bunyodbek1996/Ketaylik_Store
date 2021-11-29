package com.dasturchi.newketaylikstore.ui.auth

import com.dasturchi.newketaylikstore.network.response.LoginResponse

interface AuthListener {
    fun onStarted()
    fun onSuccess()
    fun onFailure(message: String)
}