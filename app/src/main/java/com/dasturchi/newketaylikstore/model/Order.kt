package com.dasturchi.newketaylikstore.model

data class Order (
    val id: Int? = null,
    val customer_id: Int? = null,
    val name: String? = null,
    val restaurant_id: Int? = null,
    val city_id: Int? = null,
    val delivery_price: Int? = null,
    val total_price: Int? = null,
    val address: String? = null,
    val phone: Long? = null,
    val comment: String? = null,
    val status: Int? = null,
    val is_finished: Int? = null,
    val created_at: String? = null,
)