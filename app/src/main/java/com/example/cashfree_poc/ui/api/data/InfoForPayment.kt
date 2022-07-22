package com.example.cashfree_poc.ui.api.data


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class InfoForPayment(
    @Json(name = "cf_order_id")
    val cfOrderId: Int? = null,
    @Json(name = "customer_details")
    val customerDetails: CustomerDetails? = null,
    @Json(name = "entity")
    val entity: String? = null,
    @Json(name = "order_amount")
    val orderAmount: Int? = null,
    @Json(name = "order_currency")
    val orderCurrency: String? = null,
    @Json(name = "order_expiry_time")
    val orderExpiryTime: String? = null,
    @Json(name = "order_id")
    val orderId: String? = null,
    @Json(name = "order_meta")
    val orderMeta: OrderMeta? = null,
    @Json(name = "order_note")
    val orderNote: String? = null,
    @Json(name = "order_splits")
    val orderSplits: List<Any?>? = null,
    @Json(name = "order_status")
    val orderStatus: String? = null,
    @Json(name = "order_tags")
    val orderTags: Any? = null,
    @Json(name = "order_token")
    val orderToken: String? = null,
    @Json(name = "payment_link")
    val paymentLink: String? = null,
    @Json(name = "payments")
    val payments: Payments? = null,
    @Json(name = "refunds")
    val refunds: Refunds? = null,
    @Json(name = "settlements")
    val settlements: Settlements? = null
)