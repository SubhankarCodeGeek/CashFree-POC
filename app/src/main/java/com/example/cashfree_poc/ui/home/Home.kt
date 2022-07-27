package com.example.cashfree_poc.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cashfree_poc.R
import com.example.cashfree_poc.ui.theme.Purple200
import com.example.cashfree_poc.ui.theme.Teal200
import com.example.cashfree_poc.ui.viewModel.PaymentViewModel

@Composable
fun TopBar(
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(text = "Cashfree Checkout") },
        modifier = modifier
    )
}

@Composable
fun AmountFiled(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    onClickAction: () -> Unit = {},
    enable: Boolean = true
) {
//    val focusRequester = remember { FocusRequester() }

//    LaunchedEffect(key1 = Unit, block = {
//        focusRequester.requestFocus()
//    })
    TextField(
        modifier = modifier
            .border(width = 2.dp, color = Color.Black)
            .padding(8.dp)
        /*.focusRequester(focusRequester)*/,
        value = text,
        onValueChange = { onTextChange(it) },
        enabled = enable,
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_currency_rupee_24),
                contentDescription = "Currency Icon",
                tint = Color.Black
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = {
            onClickAction.invoke()
        })
    )
}

@Composable
fun PayButton(text: String, textColor: Color, gradient: Brush, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent
        ),
        contentPadding = PaddingValues()
    ) {
        Box(
            modifier = Modifier
                .background(gradient)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(text = text, color = textColor)
        }
    }
}

@Preview
@Composable
fun GradientColorPreView() {
    PayButton(
        text = "Pay", textColor = Color.White,
        gradient = Brush.horizontalGradient(
            colors = listOf(Teal200, Purple200)
        )
    ) {

    }
}