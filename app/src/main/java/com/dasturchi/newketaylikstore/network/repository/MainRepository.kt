package com.dasturchi.newketaylikstore.network.repository

import com.dasturchi.newketaylikstore.network.MyApi
import com.dasturchi.newketaylikstore.network.SafeApiRequest
import com.dasturchi.newketaylikstore.network.response.*
import com.google.gson.JsonObject

class MainRepository(
    private val api: MyApi
) : SafeApiRequest() {

    suspend fun setProductStatus(token: String,id: String) : ProStatusResponse {
        return apiRequest { api.setProductStatus(token,id) }
    }

    suspend fun loadNewOrders(token: String) : OrderResponse {
        return apiRequest { api.loadOrders(token) }
    }
    suspend fun loadOldOrders(token: String) : OrderResponse {
        return apiRequest { api.loadOldOrders(token) }
    }

    suspend fun loadProducts(token: String) : ProductResponse {
        return apiRequest { api.loadProducts(token) }
    }

    suspend fun setStoreStatus(token: String) : StoreResponse {
        return apiRequest { api.setStoreStatus(token) }
    }

    suspend fun getStoreStatus(token: String) : StoreResponse {
        return apiRequest { api.getStoreStatus(token) }
    }

    suspend fun loadSingleOrder(token: String, id: String) : SingleOrderResponse {
        return apiRequest { api.loadSingleOrder(token,id) }
    }

    suspend fun senFCM(token: String, fcmToken: String?): FCMResponse {
        val jsonObject = JsonObject()
        jsonObject.addProperty("fcm_token",fcmToken)
        return apiRequest { api.sendFCM(token,jsonObject) }
    }

    suspend fun receiveOrder(token: String, id: String): ReceiveOrderResponse {
        return apiRequest { api.receiveOrder(token,id) }
    }

    suspend fun readyOrder(token: String, id: String): ReceiveOrderResponse {
        return apiRequest { api.readyOrder(token,id) }
    }

    suspend fun cancelOrder(token: String, id: String): ReceiveOrderResponse {
        return apiRequest { api.cancelOrder(token,id) }
    }

    suspend fun loadBeetween(token: String, from: String, to: String): OrderResponse {
        val jsonObject = JsonObject()
        jsonObject.addProperty("from",from)
        jsonObject.addProperty("to",to)
        return apiRequest { api.loadBeetween(token,jsonObject) }
    }

}