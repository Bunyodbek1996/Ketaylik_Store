package com.dasturchi.newketaylikstore.network.response

import com.dasturchi.newketaylikstore.model.ProStatus
import com.dasturchi.newketaylikstore.model.SmsCode
import com.dasturchi.newketaylikstore.model.Token

class ProStatusResponse(
    var success: Boolean? = null,
    var message: String? = null,
    var data : ProStatus? = null
)