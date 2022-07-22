package com.example.cashfree_poc.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cashfree_poc.ui.api.data.CashFreePaymentInitiateRequest
import com.example.cashfree_poc.ui.api.domain.repository.PaymentRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val repo:PaymentRepo
) : ViewModel(){

    fun onClickPaymentInitiate(){
        viewModelScope.launch {
            repo.initiatePayment(CashFreePaymentInitiateRequest())
        }
    }
}