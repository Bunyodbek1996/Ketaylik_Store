package com.dasturchi.newketaylikstore.network.response

import com.dasturchi.newketaylikstore.model.Order

class OrderResponse (
    var success: Boolean? = null,
    var message: String? = null,
    var error_code: Int? = null,
    var data : List<Order>? = null
)