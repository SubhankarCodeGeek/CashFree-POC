package com.example.cashfree_poc.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cashfree_poc.ui.api.data.CashFreePaymentInitiateRequest
import com.example.cashfree_poc.ui.api.data.PaymentInitiateState
import com.example.cashfree_poc.ui.api.domain.NetworkUtil
import com.example.cashfree_poc.ui.api.domain.repository.PaymentRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val repo: PaymentRepo,
    private val connectivityUtil: NetworkUtil
) : ViewModel() {

    var paymentInitiateState by mutableStateOf(PaymentInitiateState())
        private set

    var paymentText by mutableStateOf("1.00")

    private val _paymentInitiateEvent = MutableSharedFlow<PaymentInitiateState>()
    val paymentInitiateEvent = _paymentInitiateEvent.asSharedFlow()

    private val _softInputVisibility = MutableSharedFlow<Boolean>()
    val softInputVisibility = _softInputVisibility.asSharedFlow()

    private val _paymentError = MutableSharedFlow<String>()
    val paymentError = _paymentError.asSharedFlow()

    private val _error = MutableSharedFlow<String>()
    val interNotAvailableError = _error.asSharedFlow()

    private val _apiCallOnProgress = mutableStateOf(true)
    val apiCallOnProgress get() = _apiCallOnProgress

    fun onClickPaymentInitiate() {
        viewModelScope.launch {
            if (paymentText.isEmpty() || paymentText == "0.00") {
                _error.emit("Payment amount should be greater than 0.00 !!")
            } else {
                checkConnectivity {
                    callPaymentInitiate()
                }
            }
        }
    }

    private fun checkConnectivity(func: Boolean.() -> Unit = {}) {
        viewModelScope.launch {
            if (connectivityUtil.isInternetAvailable()) {
                _softInputVisibility.emit(false)
                _apiCallOnProgress.value = false
                func.invoke(true)
            } else {
                _apiCallOnProgress.value = true
                _error.emit("Please check your internet connection !!")
            }
        }
    }

    private fun callPaymentInitiate() {
        viewModelScope.launch {
            paymentInitiateState.apply {
                val response = repo.initiatePayment(
                    CashFreePaymentInitiateRequest(
                        amount,
                        email,
                        name,
                        note,
                        phone
                    )
                )
                paymentInitiateState = paymentInitiateState.copy(
                    orderId = response.data?.infoForPayment?.orderId,
                    token = response.data?.infoForPayment?.orderToken
                )
            }
            _apiCallOnProgress.value = true
            paymentInitiateState.let {
                _paymentInitiateEvent.emit(it)
            }
        }
    }

    fun verifyPayment(orderId: String) {
        checkConnectivity {
            callPaymentVerifyApi(orderId)
        }
    }

    private fun callPaymentVerifyApi(orderId: String) {
        viewModelScope.launch {
            val response = repo.verifyPayment(orderId)
            if (response.status?.isSuccess == true) {
                _paymentError.emit("Payment Success")
            } else {
                _paymentError.emit(response.status?.message ?: "")
            }
            _apiCallOnProgress.value = true
        }
    }

    fun onValueChange(value: String) {
        paymentText = value
        paymentInitiateState = paymentInitiateState.copy(amount = paymentText.toDoubleOrNull())
    }
}