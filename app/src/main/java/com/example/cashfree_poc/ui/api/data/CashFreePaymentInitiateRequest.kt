package com.example.cashfree_poc.ui.api.data


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CashFreePaymentInitiateRequest(
    @Json(name = "amount")
    val amount: Int? = null,
    @Json(name = "email")
    val email: String? = null,
    @Json(name = "name")
    val name: String? = null,
    @Json(name = "note")
    val note: String? = null,
    @Json(name = "phone")
    val phone: String? = null
)