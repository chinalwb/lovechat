package com.pact.demomyai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pact.chatui.ChatScreen
import com.pact.demomyai.theme.SunsetChatConfig
import com.pact.demomyai.theme.SunsetTheme
import com.pact.demomyai.theme.sunsetChatColors

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            SunsetTheme {
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
        onSendClick = viewModel::send,
        config = SunsetChatConfig,
        colors = sunsetChatColors(),
        topBar = { onHeightChanged -> SunsetTopBar(onHeightChanged) },
        composer = { d, s, c, o -> SunsetComposerBar(d, s, c, o) },
    )
}
