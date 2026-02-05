package com.ui.scannerapp.pages.scanner

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.ui.scannerapp.pages.scanner.example.Message
import com.ui.scannerapp.pages.scanner.example.MessageCard


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

@Composable
fun CameraFunction(){
    val currentContext = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
}