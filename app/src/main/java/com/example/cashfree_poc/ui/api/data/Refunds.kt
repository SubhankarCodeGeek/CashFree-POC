package com.example.cashfree_poc.ui.api.data


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Refunds(
    @Json(name = "url")
    val url: String? = null
)