package com.example.cashfree_poc.ui.api.data

import com.squareup.moshi.Json

data class Upi(
    @Json(name = "channel")
    val channel: String? = null,
    @Json(name = "upi_id")
    val upiId: String? = null
)