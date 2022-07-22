package com.example.cashfree_poc.ui.api.data


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OrderMeta(
    @Json(name = "notify_url")
    val notifyUrl: Any? = null,
    @Json(name = "payment_methods")
    val paymentMethods: String? = null,
    @Json(name = "return_url")
    val returnUrl: Any? = null
)