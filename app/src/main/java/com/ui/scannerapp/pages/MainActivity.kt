package com.ui.scannerapp.pages

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ui.scannerapp.pages.example.Message
import com.ui.scannerapp.pages.example.MessageCard
import com.ui.scannerapp.ui.theme.ScannerAppTheme

// Home page
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ScannerAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Conversation(SampleData.conversationSample)
                }
            }
        }
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
@Preview(showBackground = true, showSystemUi = false)
@Composable
fun GreetingPreview() {
    ScannerAppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Conversation(SampleData.conversationSample)
        }
    }
}