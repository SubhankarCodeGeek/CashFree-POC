package com.example.cashfree_poc.ui.api.domain.repository

import com.example.cashfree_poc.ui.api.data.CashFreePaymentInitiateRequest
import com.example.cashfree_poc.ui.api.data.PaymentInitiateResponse
import com.example.cashfree_poc.ui.api.domain.PaymentApi
import javax.inject.Inject

class PaymentRepo @Inject constructor(
    private val api: PaymentApi
) {

    suspend fun initiatePayment(request: CashFreePaymentInitiateRequest): PaymentInitiateResponse =
        api.paymentInitiate(request)

    suspend fun verifyPayment(orderId: String): PaymentInitiateResponse = api.verifyPayment(orderId)
}