package com.example.cashfree_poc.ui.api.domain

import com.example.cashfree_poc.ui.api.data.CashFreePaymentInitiateRequest
import com.example.cashfree_poc.ui.api.data.PaymentInitiateResponse
import com.example.cashfree_poc.ui.api.domain.ApiConstant.PAYMENT_INITIATE
import com.example.cashfree_poc.ui.api.domain.ApiConstant.PAYMENT_VERIFY
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PaymentApi {

    @POST(PAYMENT_INITIATE)
    suspend fun paymentInitiate(@Body request: CashFreePaymentInitiateRequest): PaymentInitiateResponse

    @GET(PAYMENT_VERIFY)
    suspend fun verifyPayment(orderId: String): PaymentInitiateResponse
}