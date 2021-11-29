package com.dasturchi.newketaylikstore.network.response

import com.dasturchi.newketaylikstore.model.Store

class StoreResponse (
    var success: Boolean? = null,
    var message: String? = null,
    var error_code: Int? = null,
    var data: Store? = null
)