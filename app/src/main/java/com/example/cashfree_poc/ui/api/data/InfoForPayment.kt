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
    val settlements: Settlements? = null,
    @Json(name = "authorization")
    val authorization: String? = null,
    @Json(name = "bank_reference")
    val bankReference: String? = null,
    @Json(name = "cf_payment_id")
    val cfPaymentId: String? = null,
    @Json(name = "error_details")
    val errorDetails: ErrorDetails? = null,
    @Json(name = "is_captured")
    val isCaptured: Boolean? = null,
    @Json(name = "payment_amount")
    val paymentAmount: Double? = null,
    @Json(name = "payment_completion_time")
    val paymentCompletionTime: String? = null,
    @Json(name = "payment_currency")
    val paymentCurrency: String? = null,
    @Json(name = "payment_group")
    val paymentGroup: String? = null,
    @Json(name = "payment_message")
    val paymentMessage: String? = null,
    @Json(name = "payment_method")
    val paymentMethod: PaymentMethod? = null,
    @Json(name = "payment_status")
    val paymentStatus: String? = null,
    @Json(name = "payment_time")
    val paymentTime: String? = null
)