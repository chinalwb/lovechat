package com.pact.streamchat.ui

object MockChatData {

    fun replyTextFor(prompt: String): String {
        val normalized = prompt.lowercase()
        return when {
            "dark" in normalized -> """
**Dark theme** support is straightforward with *Material 3*.

The current structure uses `colorScheme` roles, so switching is automatic:

```kotlin
val colorScheme = if (darkTheme) StreamDarkColors else StreamLightColors
```

Key surfaces to verify:
- `background` and `surface` contrast
- `primaryContainer` bubble readability
- `onSurfaceVariant` for secondary text
            """.trimIndent()

            "animation" in normalized -> """
Add subtle *message entrance motion* and a staggered prompt reveal.

The approach uses **Animatable** for two properties:
- `alpha` fades from `0f` to `1f`
- `translationY` slides from `8dp` to `0dp`

Both run in parallel with a `tween(350)` spec. Keep it sparse so the screen still feels like a **productivity tool**, not a toy.

Paragraph-level animation is key — each paragraph fades in independently as it arrives from the server. This avoids the cost of animating *individual characters* and gives a cleaner visual rhythm.
            """.trimIndent()

            "architecture" in normalized -> """
The next step is moving message state into a **ViewModel**.

```kotlin
class ChatViewModel : ViewModel() {
    val messages = mutableStateListOf<ChatMessage>()

    fun send(text: String) {
        messages += ChatMessage(id = "user", sender = User, text = text)
        viewModelScope.launch { streamReply(text) }
    }
}
```

Then swap the mock generator for a *repository interface*:
- `ChatRepository.streamReply(prompt): Flow<String>`
- Each emission appends one token to the assistant message

The `ViewModel` survives configuration changes, so streaming continues across rotation. The `viewModelScope` cancels automatically when the screen is destroyed.
            """.trimIndent()

            "code" in normalized || "kotlin" in normalized -> """
Here's a simple **Compose** greeting example:

```kotlin
@Composable
fun Greeting(name: String) {
    val visible = remember { mutableStateOf(true) }
    if (visible.value) {
        Text(text = "Hello, ${'$'}name!")
    }
}
```

Notice how `remember` preserves state across recompositions. The `mutableStateOf` call creates an *observable* holder that triggers recomposition when its value changes.

You can also use **delegated properties** for cleaner syntax:

```kotlin
var visible by remember { mutableStateOf(true) }
```

This removes the `.value` boilerplate and reads more naturally in Kotlin.
            """.trimIndent()

            "table" in normalized -> """
**Tables** render as a real grid, with column widths sized to content and a horizontal-scroll wrapper for wide tables. Inline markdown — *italics*, **bold**, `code` — works inside cells.

### Narrow: scroll behavior

This one fits in the bubble without scrolling.

| State | Auto-scroll | FAB |
| :--- | :---: | :---: |
| `Idle` | no | no |
| `Streaming` (follow on) | **yes** | no |
| `Streaming` (follow off) | no | **yes** |
| `Complete` | no | *conditional* |

### Wide: streaming chunk knobs

Seven columns — too wide for the bubble, so the renderer wraps it in `horizontalScroll`. Swipe sideways to see the rest.

| Parameter | Min | Max | Default | Unit | Owner | Note |
| :--- | ---: | ---: | ---: | :---: | :--- | :--- |
| `chunkSize` | 2 | 12 | 7 | chars | `MockChatService` | uniform random |
| `burstProb` | 0.0 | 1.0 | 0.10 | ratio | `MockChatService` | per-emit roll |
| `burstMul` | 1 | 8 | 4 | × | `MockChatService` | multiplier on burst |
| `delayMs` | 15 | 60 | 35 | ms | `MockChatService` | inter-chunk pause |
| `thinkingDelay` | 0 | — | 800 | ms | `ChatViewModel` | before first chunk |
| `typewriterCps` | 10 | 200 | 80 | chars/s | `AnimatedParagraph` | reveal rate |

Long cells wrap at the column max (`200dp` by default) — see the `Note` column.
            """.trimIndent()

            "markdown" in normalized || "format" in normalized -> """
This renderer supports several **inline styles**:

- **Bold text** using double asterisks
- *Italic text* using single asterisks
- `Inline code` using backticks

It also handles fenced code blocks with keyword highlighting for `fun`, `val`, `class`, and other Kotlin tokens.

> The parser uses character scanning — *no regex* in the hot path.
            """.trimIndent()

            else -> """
## Building a Chat UI with Jetpack Compose

Creating a production-quality chat interface requires attention to several key areas. Let's walk through the **architecture** and implementation.

### Message State Management

The foundation is a clean state model. Each message tracks its content, sender, and streaming status:

```kotlin
data class ChatMessage(
    val id: String,
    val sender: MessageSender,
    val text: String,
    val streamState: StreamState = StreamState.Complete
)
```

### Streaming Architecture

Real-time message delivery uses **Server-Sent Events** (SSE). The client receives text chunks and appends them to the current message:

- Each SSE event carries a *variable-length* payload
- The UI renders paragraphs as they arrive
- A `Flow<String>` abstraction makes the transport layer swappable

```kotlin
fun streamReply(prompt: String): Flow<String> = flow {
    val response = api.chat(prompt)
    response.body?.source()?.let { source ->
        while (!source.exhausted()) {
            val chunk = source.readUtf8Line() ?: break
            emit(chunk)
        }
    }
}
```

### Paragraph-Level Rendering

Instead of re-rendering the entire message on every update, we split text by paragraph boundaries and **cache stable paragraphs** as `AnnotatedString`. Only the latest paragraph is re-parsed on each frame.

This approach gives us:
- `O(1)` work per incoming chunk (amortized)
- Stable composable keys for completed paragraphs
- Natural animation boundaries — each paragraph fades in independently

### Inline Markdown

The parser handles common inline styles *without regex*:

- **Bold** via `**double asterisks**`
- *Italic* via `*single asterisks*`
- `Code` via backtick pairs

For code blocks, we use a two-pass strategy: first scan for closed fence pairs, then apply **keyword highlighting** for tokens like `fun`, `val`, and `class`. Unclosed blocks render as plain monospace during streaming.

### Scroll Behavior

The scroll system uses a **threshold-based lock**: auto-scroll only engages when the viewport is within `100dp` of the bottom. Scrolling up disables it and reveals a *jump-to-bottom* button.

When the user sends a new message, the list scrolls to **pin that message at the top** of the viewport. The assistant response then streams in below, keeping the conversational context visible.

### Performance Considerations

Compose recomposition is *granular* — if a paragraph's `AnnotatedString` reference hasn't changed, its `Text` composable skips recomposition entirely. Combined with `remember` and `derivedStateOf`, this keeps frame times well under **16ms** even with long conversations.

```kotlin
val isNearBottom by remember {
    derivedStateOf {
        val last = listState.layoutInfo.visibleItemsInfo.lastOrNull()
        last != null && last.index >= totalItems - 1
    }
}
```

This `derivedStateOf` only triggers recomposition when the *result* changes, not on every scroll pixel. It's the right tool for scroll-dependent UI like the jump-to-bottom FAB.
            """.trimIndent()
        }
    }
}
