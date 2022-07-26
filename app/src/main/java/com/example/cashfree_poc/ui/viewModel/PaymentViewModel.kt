package com.example.cashfree_poc.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cashfree_poc.ui.api.data.CashFreePaymentInitiateRequest
import com.example.cashfree_poc.ui.api.data.PaymentInitiateState
import com.example.cashfree_poc.ui.api.domain.repository.PaymentRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val repo: PaymentRepo
) : ViewModel() {

    private val _paymentInitiateState: MutableLiveData<PaymentInitiateState> =
        MutableLiveData(PaymentInitiateState())
    val paymentInitiateState: LiveData<PaymentInitiateState> get() = _paymentInitiateState

    private val _paymentInitiateEvent = MutableSharedFlow<PaymentInitiateState>()
    val paymentInitiateEvent = _paymentInitiateEvent.asSharedFlow()

    private val _paymentError = MutableSharedFlow<String>()
    val paymentError = _paymentError.asSharedFlow()

    fun onClickPaymentInitiate() {
        viewModelScope.launch {
            _paymentInitiateState.value?.apply {
                val response = repo.initiatePayment(
                    CashFreePaymentInitiateRequest(
                        amount,
                        email,
                        name,
                        note,
                        phone
                    )
                )
                _paymentInitiateState.value = _paymentInitiateState.value?.copy(
                    orderId = response.data?.infoForPayment?.orderId,
                    token = response.data?.infoForPayment?.orderToken
                )
            }
            _paymentInitiateState.value?.let {
                _paymentInitiateEvent.emit(it)
            }
        }
    }

    fun verifyPayment(orderId: String) {
        callPaymentVerifyApi(orderId)
    }

    private fun callPaymentVerifyApi(orderId: String) {
        viewModelScope.launch {
            val response = repo.verifyPayment(orderId)
            if (response.status?.isSuccess == true) {
                _paymentError.emit("Payment Success")
            } else {
                _paymentError.emit(response.status?.message ?: "")
            }
        }
    }

    companion object {
        private const val APP_ID = "279936d5650dead5b88e9bda439972"
        private const val SECRET_ID = "d1a265a86a304ee3a3acfaffbab5df57bd77c276"
    }
}