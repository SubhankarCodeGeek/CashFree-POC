package com.example.cashfree_poc.ui.viewModel

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.*
import com.example.cashfree_poc.ui.api.data.CashFreePaymentInitiateRequest
import com.example.cashfree_poc.ui.api.data.PaymentInitiateState
import com.example.cashfree_poc.ui.api.domain.NetworkUtil
import com.example.cashfree_poc.ui.api.domain.repository.PaymentRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val repo: PaymentRepo,
    private val connectivityUtil: NetworkUtil
) : ViewModel() {

    private val _paymentInitiateState: MutableLiveData<PaymentInitiateState> =
        MutableLiveData(PaymentInitiateState())
    val paymentInitiateState: LiveData<PaymentInitiateState> get() = _paymentInitiateState

    private val _paymentInitiateEvent = MutableSharedFlow<PaymentInitiateState>()
    val paymentInitiateEvent = _paymentInitiateEvent.asSharedFlow()

    private val _softInputVisibility = MutableSharedFlow<Boolean>()
    val softInputVisibility = _softInputVisibility.asSharedFlow()

    private val _paymentError = MutableSharedFlow<String>()
    val paymentError = _paymentError.asSharedFlow()

    private val _error = MutableSharedFlow<String>()
    val interNotAvailableError = _error.asSharedFlow()

    private val _apiCallOnProgress = MutableSharedFlow<Boolean>()
    val apiCallOnProgress = _apiCallOnProgress.asLiveData()

    fun onClickPaymentInitiate() {
        checkConnectivity {
            callPaymentInitiate()
        }
    }

    private fun checkConnectivity(func: Boolean.() -> Unit = {}) {
        viewModelScope.launch {
            if (connectivityUtil.isInternetAvailable()) {
                _softInputVisibility.emit(false)
                _apiCallOnProgress.emit(true)
                func.invoke(true)
            } else {
                _error.emit("Please check your internet connection !!")
            }
        }
    }

    private fun callPaymentInitiate() {
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
            _apiCallOnProgress.emit(false)
            _paymentInitiateState.value?.let {
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
            _apiCallOnProgress.emit(false)
        }
    }
}