package com.ui.scannerapp.pages.scanner

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.ui.scannerapp.pages.example.Message
import com.ui.scannerapp.pages.example.MessageCard

@Composable
fun ScannerView(){
    Conversation(SampleData.conversationSample)
}

@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn {
        items(messages) { message ->
            MessageCard(message)
        }
    }
}