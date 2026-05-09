package com.pact.demomyai

import com.pact.chatui.DefaultChatViewModel

/**
 * One-line ViewModel: extends the library-provided [DefaultChatViewModel]
 * and wires up [MockChatService] as the data source.
 *
 * To plug in a real LLM, swap [MockChatService] for an implementation that
 * talks to OpenAI / Anthropic / Gemini / etc.
 */
class ChatViewModel : DefaultChatViewModel(MockChatService())
