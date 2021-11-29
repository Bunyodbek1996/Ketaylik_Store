package com.dasturchi.newketaylikstore.network.response

import com.dasturchi.newketaylikstore.model.ResProduct

class ProductResponse(
    var success: Boolean? = null,
    var message: String? = null,
    var data : List<ResProduct>? = null
)