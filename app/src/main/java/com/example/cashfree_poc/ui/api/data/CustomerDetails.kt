package com.example.cashfree_poc.ui.api.data


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CustomerDetails(
    @Json(name = "customer_email")
    val customerEmail: String? = null,
    @Json(name = "customer_id")
    val customerId: String? = null,
    @Json(name = "customer_name")
    val customerName: String? = null,
    @Json(name = "customer_phone")
    val customerPhone: String? = null
)