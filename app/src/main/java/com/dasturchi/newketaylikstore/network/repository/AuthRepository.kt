package com.dasturchi.newketaylikstore.network.repository

import com.dasturchi.newketaylikstore.network.MyApi
import com.dasturchi.newketaylikstore.network.SafeApiRequest
import com.dasturchi.newketaylikstore.network.response.ConfirmResponse
import com.dasturchi.newketaylikstore.network.response.LoginResponse
import com.google.gson.JsonObject

class AuthRepository(
    private val api: MyApi
) : SafeApiRequest() {


    suspend fun userLogin(phone: String) : LoginResponse{
        val jsonObject = JsonObject()
        jsonObject.addProperty("phone", phone)
        return apiRequest { api.userLogin(jsonObject) }
    }

    suspend fun userConfirm(phone: String,code : String) : ConfirmResponse{
        val jsonObject = JsonObject()
        jsonObject.addProperty("phone",phone)
        jsonObject.addProperty("code",code)
        return apiRequest { api.userConfirm(jsonObject) }
    }



}