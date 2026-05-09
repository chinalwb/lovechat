package com.pact.streamchat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pact.chatui.ChatScreen
import com.pact.streamchat.ui.theme.StreamChatTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            StreamChatTheme {
                ChatRoute()
            }
        }
    }
}

@Composable
private fun ChatRoute(viewModel: ChatViewModel = viewModel()) {
    ChatScreen(
        messages = viewModel.messages,
        draft = viewModel.draft,
        isStreaming = viewModel.isStreaming,
        onDraftChange = viewModel::onDraftChange,
        onSendClick = viewModel::send
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F1E8)
@Composable
private fun ChatPreview() {
    StreamChatTheme {
        ChatScreen(
            messages = emptyList(),
            draft = "Make the answer tighter."
        )
    }
}
