package com.example.cashfree_poc

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
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
import com.example.cashfree_poc.ui.home.HomeScreen
import com.example.cashfree_poc.ui.theme.Cashfree_pocTheme
import com.example.cashfree_poc.ui.viewModel.PaymentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : ComponentActivity(), CFCheckoutResponseCallback {

    var cfEnvironment: CFSession.Environment = CFSession.Environment.PRODUCTION
    private val viewModel: PaymentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Cashfree_pocTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    HomeScreen {
                        viewModel.onClickPaymentInitiate()
                    }
                }
            }
        }
        observePaymentInitiateState()
    }

    private fun observePaymentInitiateState() {
        lifecycleScope.launchWhenStarted {
            viewModel.paymentInitiateEvent.collectLatest {
                if (it.orderId != null && it.token != null) {
                    initCashFreeFlow(it.token, it.orderId)
                }
            }
            viewModel.paymentError.collectLatest {
                Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
            }
            viewModel.interNotAvailableError.collectLatest {
                Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
            }
            viewModel.softInputVisibility.collectLatest {
                hideKeyboard(this@MainActivity)
            }
        }
    }

    private fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
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
        Toast.makeText(this@MainActivity, cfErrorResponse?.message ?: "", Toast.LENGTH_SHORT).show()
    }

}