package com.dasturchi.newketaylikstore.network.response



class ReceiveOrderResponse (
    var success: Boolean? = null,
    var message: String? = null,
    var error_code: Int? = null,
    var data : Any? = null
)