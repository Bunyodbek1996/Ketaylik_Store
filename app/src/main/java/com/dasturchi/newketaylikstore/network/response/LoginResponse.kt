package com.dasturchi.newketaylikstore.network.response

import com.dasturchi.newketaylikstore.model.SmsCode

class LoginResponse(
    var success: Boolean? = null,
    var message: String? = null,
    var data : SmsCode? = null
)