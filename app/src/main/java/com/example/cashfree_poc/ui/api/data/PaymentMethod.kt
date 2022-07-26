package com.example.cashfree_poc.ui.api.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class PaymentMethod {
    @Json(name = "upi")
    val upi: Upi? = null
}