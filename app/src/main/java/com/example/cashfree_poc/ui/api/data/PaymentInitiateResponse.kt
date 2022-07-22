package com.example.cashfree_poc.ui.api.data


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PaymentInitiateResponse(
    @Json(name = "data")
    val `data`: Data? = Data(),
    @Json(name = "status")
    val status: Status? = Status()
)