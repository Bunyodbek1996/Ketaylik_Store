package com.dasturchi.newketaylikstore.ui.main

interface MainListener {
    fun onStarted()
    fun onSuccess()
    fun onFailure(message: String)
}