package com.pact.streamchat

import com.pact.chatui.DefaultChatViewModel

/**
 * App-level ViewModel — a thin alias around [DefaultChatViewModel] so the
 * default `viewModel()` factory (which requires a no-arg constructor) can
 * instantiate it. Wires up [MockChatService] as the data source.
 */
class ChatViewModel : DefaultChatViewModel(MockChatService())
