package com.ui.scannerapp.pages.CheckoutScreen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ui.scannerapp.pages.CheckoutScreen.example.Message
import com.ui.scannerapp.pages.CheckoutScreen.example.MessageCard

@Preview(showBackground = true)
@Composable
fun CheckoutScreen() {
    Conversation(SampleData.conversationSample)
}


@Composable
fun ScannerView(){
    Conversation(SampleData.conversationSample)
}

@Composable
fun Layout(){
    LazyColumn() {
    }
}

@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn {
        items(messages) { message ->
            MessageCard(message)
        }
    }
}