package com.dasturchi.newketaylikstore.network.response

import com.dasturchi.newketaylikstore.model.Token

class ConfirmResponse(
    var success: Boolean? = null,
    var message: String? = null,
    var data : Token? = null
)