package com.example.cashfree_poc.ui.api.data


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Status(
    @Json(name = "isSuccess")
    val isSuccess: Boolean? = null,
    @Json(name = "message")
    val message: String? = null,
    @Json(name = "statusCode")
    val statusCode: String? = null
)