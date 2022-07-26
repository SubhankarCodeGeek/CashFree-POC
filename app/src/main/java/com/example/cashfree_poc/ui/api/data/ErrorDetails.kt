package com.example.cashfree_poc.ui.api.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorDetails(
    @Json(name = "error_code")
    val errorCode: String? = null,
    @Json(name = "error_description")
    val errorDescription: String? = null,
    @Json(name = "error_reason")
    val errorReason: String? = null,
    @Json(name = "error_source")
    val errorSource: String? = null
)
