package com.pact.streamchat

import com.pact.chatui.ChatChunk
import com.pact.chatui.ChatMessage
import com.pact.chatui.ChatService
import com.pact.chatui.MessageSender
import com.pact.streamchat.ui.MockChatData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

// SSE simulation parameters. Chunk sizes are intentionally larger than a
// typical token-by-token LLM stream — modelling a server that batches
// several tokens per event (or a faster-than-real upstream). Bursts model
// an upstream buffer flushing at once. Splits ignore word and markdown
// boundaries — a real SSE byte stream wouldn't know about them.
private const val MIN_CHUNK_CHARS = 8
private const val MAX_CHUNK_CHARS = 32
private const val BURST_PROBABILITY = 0.10f
private const val BURST_MULTIPLIER = 4
private const val MIN_CHUNK_DELAY_MS = 15L
private const val MAX_CHUNK_DELAY_MS = 60L

/**
 * A [ChatService] that returns canned markdown replies from [MockChatData],
 * delivered as SSE-style chunks with randomized sizes and inter-chunk
 * delays. Useful for UI development when no real LLM is wired up.
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
