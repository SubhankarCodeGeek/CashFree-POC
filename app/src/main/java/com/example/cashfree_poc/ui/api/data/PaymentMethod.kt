package com.example.cashfree_poc.ui.api.data

import com.squareup.moshi.Json

class PaymentMethod {
    @Json(name = "upi")
    var upi: Upi? = null
}