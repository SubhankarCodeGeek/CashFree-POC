package com.example.cashfree_poc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.cashfree.pg.api.CFPaymentGatewayService
import com.cashfree.pg.core.api.CFSession
import com.cashfree.pg.core.api.CFSession.CFSessionBuilder
import com.cashfree.pg.core.api.CFTheme.CFThemeBuilder
import com.cashfree.pg.core.api.callback.CFCheckoutResponseCallback
import com.cashfree.pg.core.api.exception.CFException
import com.cashfree.pg.core.api.utils.CFErrorResponse
import com.cashfree.pg.ui.api.CFDropCheckoutPayment.CFDropCheckoutPaymentBuilder
import com.cashfree.pg.ui.api.CFPaymentComponent
import com.cashfree.pg.ui.api.CFPaymentComponent.CFPaymentComponentBuilder
import com.example.cashfree_poc.ui.api.data.PaymentInitiateState
import com.example.cashfree_poc.ui.home.AmountFiled
import com.example.cashfree_poc.ui.home.TopBar
import com.example.cashfree_poc.ui.util.hideKeyboard
import com.example.cashfree_poc.ui.util.toast
import com.example.cashfree_poc.ui.viewModel.PaymentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity(), CFCheckoutResponseCallback {

    var cfEnvironment: CFSession.Environment = CFSession.Environment.PRODUCTION
    private val viewModel: PaymentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    HomeScreen(viewModel = viewModel)
                }
            }
        }
        observePaymentInitiateState()
    }

    @Composable
    fun HomeScreen(viewModel: PaymentViewModel) {
        Scaffold(topBar = { TopBar() }) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center
            ) {
                AmountFiled(
                    modifier = Modifier
                        .sizeIn(minHeight = 70.dp)
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    text = viewModel.paymentText,
                    onTextChange = { viewModel.onValueChange(it) },
                    { viewModel.onClickPaymentInitiate() },
                    enable = viewModel.apiCallOnProgress.value
                )
                Button(
                    onClick = { viewModel.onClickPaymentInitiate() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .sizeIn(minHeight = 46.dp),
                    enabled = viewModel.apiCallOnProgress.value
                ) {
                    Text(text = "Pay")
                }
                if (viewModel.apiCallOnProgress.value.not()) {
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth(),
                    )
                } else {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }

    private fun observePaymentInitiateState() {
        lifecycleScope.launchWhenStarted {
            launch {
                viewModel.paymentInitiateEvent.collectLatest {
                    if (it.orderId != null && it.token != null) {
                        initCashFreeFlow(it.token, it.orderId)
                    }
                }
            }
            launch { viewModel.paymentError.collectLatest { toast(it) } }
            launch { viewModel.interNotAvailableError.collectLatest { toast(it) } }
            launch { viewModel.softInputVisibility.collectLatest { hideKeyboard() } }
        }
    }

    private fun initCashFreeFlow(token: String, orderID: String) {
        try {
            CFPaymentGatewayService.getInstance().setCheckoutCallback(this)
            doDropCheckoutPayment(token, orderID)
        } catch (e: CFException) {
            e.printStackTrace()
        }
    }

    private fun doDropCheckoutPayment(token: String, orderID: String) {
        try {
            val cfSession = CFSessionBuilder()
                .setEnvironment(cfEnvironment)
                .setOrderToken(token)
                .setOrderId(orderID)
                .build()
            val cfPaymentComponent = CFPaymentComponentBuilder()
                .add(CFPaymentComponent.CFPaymentModes.CARD)
                .add(CFPaymentComponent.CFPaymentModes.UPI)
                .build()
            val cfTheme = CFThemeBuilder()
                .setNavigationBarBackgroundColor("#006EE1")
                .setNavigationBarTextColor("#ffffff")
                .setButtonBackgroundColor("#006EE1")
                .setButtonTextColor("#ffffff")
                .setPrimaryTextColor("#000000")
                .setSecondaryTextColor("#000000")
                .build()
            val cfDropCheckoutPayment = CFDropCheckoutPaymentBuilder()
                .setSession(cfSession) //By default all modes are enabled. If you want to restrict the payment modes uncomment the next line
                //                        .setCFUIPaymentModes(cfPaymentComponent)
                .setCFNativeCheckoutUITheme(cfTheme)
                .build()
            val gatewayService = CFPaymentGatewayService.getInstance()
            gatewayService.doPayment(this@MainActivity, cfDropCheckoutPayment)
        } catch (exception: CFException) {
            exception.printStackTrace()
        }
    }

    override fun onPaymentVerify(orderId: String?) {
        orderId?.let {
            viewModel.verifyPayment(it)
        }
    }

    override fun onPaymentFailure(cfErrorResponse: CFErrorResponse?, orderId: String?) {
        toast(cfErrorResponse?.message)
    }

}