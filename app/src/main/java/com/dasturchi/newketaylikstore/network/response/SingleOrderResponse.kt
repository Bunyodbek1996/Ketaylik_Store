package com.dasturchi.newketaylikstore.network.response

import com.dasturchi.newketaylikstore.model.SingleOrder
import com.dasturchi.newketaylikstore.model.Token

class SingleOrderResponse(
    var success: Boolean? = null,
    var message: String? = null,
    var data : SingleOrder? = null
)