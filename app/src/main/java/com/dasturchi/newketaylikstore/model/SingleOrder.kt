package com.dasturchi.newketaylikstore.model

class SingleOrder(
    var total_price: Int? = null,
    var status: Int? = null,
    var is_finished: Int? = null,
    var comment: String? = null,
    var pockets: List<ProductWithQuantity>? = null
)