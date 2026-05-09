package com.pact.chatui

enum class MessageSender {
    User,
    Assistant
}

enum class StreamState {
    Complete,
    Thinking,
    Streaming
}

data class ChatMessage(
    val id: String,
    val sender: MessageSender,
    val text: String,
    val streamState: StreamState = StreamState.Complete
)
