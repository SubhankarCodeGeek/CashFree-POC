package com.example.cashfree_poc.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*

@Composable
fun HomeScreen() {
    Card{
        Column {
            Text(text = "Hello Priya..", style = MaterialTheme.typography.h2)
        }
    }
}