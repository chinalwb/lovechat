package com.pact.demomyai

import com.pact.chatui.ChatChunk
import com.pact.chatui.ChatMessage
import com.pact.chatui.ChatService
import com.pact.chatui.MessageSender
import com.pact.demomyai.data.MockChatData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

// SSE simulation parameters. Chunk sizes intentionally larger than a typical
// token-by-token LLM stream. Splits ignore word/markdown boundaries.
private const val MIN_CHUNK_CHARS = 8
private const val MAX_CHUNK_CHARS = 16
private const val BURST_PROBABILITY = 0.10f
private const val BURST_MULTIPLIER = 4
private const val MIN_CHUNK_DELAY_MS = 15L
private const val MAX_CHUNK_DELAY_MS = 60L

/**
 * Mock [ChatService] for the demo — returns canned markdown from
 * [MockChatData] in SSE-style chunks. Production code would replace this
 * with an OpenAI / Anthropic / Gemini implementation.
 */
class MockChatService : ChatService {
    override fun streamReply(history: List<ChatMessage>): Flow<ChatChunk> = flow {
        val prompt = history.lastOrNull { it.sender == MessageSender.User }?.text.orEmpty()
        val full = MockChatData.replyTextFor(prompt)
        var i = 0
        while (i < full.length) {
            val baseSize = Random.nextInt(MIN_CHUNK_CHARS, MAX_CHUNK_CHARS + 1)
            val size = if (Random.nextFloat() < BURST_PROBABILITY) {
                baseSize * BURST_MULTIPLIER
            } else {
                baseSize
            }
            val end = (i + size).coerceAtMost(full.length)
            emit(ChatChunk.Text(full.substring(i, end)))
            i = end
            if (i < full.length) {
                delay(Random.nextLong(MIN_CHUNK_DELAY_MS, MAX_CHUNK_DELAY_MS + 1))
            }
        }
        emit(ChatChunk.Done)
    }
}
