package com.dasturchi.newketaylikstore.network

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.dasturchi.newketaylikstore.network.response.*
import com.google.gson.JsonObject
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface MyApi {


    @Headers("Content-Type: application/json")
    @POST(LOGIN_URL)
    suspend fun userLogin(@Body() body: JsonObject): Response<LoginResponse>

    @Headers("Content-Type: application/json")
    @POST(CONFIRM_URL)
    suspend fun userConfirm(@Body() body: JsonObject): Response<ConfirmResponse>

    @Headers("Content-Type: application/json")
    @POST(SEND_FCM_URL)
    suspend fun sendFCM(@Header("auth_token") token: String,@Body() body: JsonObject): Response<FCMResponse>

    @Headers("Content-Type: application/json")
    @POST(GET_NEW_ORDERS)
    suspend fun loadOrders(@Header("auth_token") token: String): Response<OrderResponse>

    @Headers("Content-Type: application/json")
    @POST(GET_PRODUCTS)
    suspend fun loadProducts(@Header("auth_token") token: String): Response<ProductResponse>

    @Headers("Content-Type: application/json")
    @POST(SET_PRODUCT_STATUS)
    suspend fun setProductStatus(@Header("auth_token") token: String,@Path(value = "id",encoded = true) id: String): Response<ProStatusResponse>

    @Headers("Content-Type: application/json")
    @POST(GET_ALL_ORDERS)
    suspend fun loadOldOrders(@Header("auth_token") token: String): Response<OrderResponse>

    @Headers("Content-Type: application/json")
    @POST(SET_STORE_STATUS)
    suspend fun setStoreStatus(@Header("auth_token") token: String): Response<StoreResponse>

    @Headers("Content-Type: application/json")
    @POST(GET_STORE_STATUS)
    suspend fun getStoreStatus(@Header("auth_token") token: String): Response<StoreResponse>

    @Headers("Content-Type: application/json")
    @POST(GET_SINGLE_ORDER)
    suspend fun loadSingleOrder(@Header("auth_token") token: String, @Path(value = "id",encoded = true) id: String) : Response<SingleOrderResponse>

    @Headers("Content-Type: application/json")
    @POST(RECEIVE_ORDER)
    suspend fun receiveOrder(@Header("auth_token") token: String, @Path(value = "id",encoded = true) id: String) : Response<ReceiveOrderResponse>

    @Headers("Content-Type: application/json")
    @POST(READY_ORDER)
    suspend fun readyOrder(@Header("auth_token") token: String, @Path(value = "id",encoded = true) id: String) : Response<ReceiveOrderResponse>


    @Headers("Content-Type: application/json")
    @POST(CANCEL_ORDER)
    suspend fun cancelOrder(@Header("auth_token") token: String, @Path(value = "id",encoded = true) id: String) : Response<ReceiveOrderResponse>

    @Headers("Content-Type: application/json")
    @POST(GET_ALL_BETWEEN)
    suspend fun loadBeetween(@Header("auth_token") token: String,@Body() jsonObject: JsonObject) : Response<OrderResponse>


    companion object {
        operator fun invoke(
            networkInterceptor: NetworkInterceptor,
            chuckerInterceptor: ChuckerInterceptor
        ): MyApi {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(chuckerInterceptor)
                .addInterceptor(networkInterceptor)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)

        }
    }
}