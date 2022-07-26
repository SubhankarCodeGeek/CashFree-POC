package com.example.cashfree_poc.ui.api.data

data class PaymentInitiateState(
    val amount: Double? = 1.00,
    val email: String? = "subhankar.bag@codelogicx.com",
    val name: String? = "Subhankar Bag",
    val note: String? = "test transaction",
    val phone: String? = "8240859090",
    val orderId:String? = "",
    val token:String? = ""
)