package com.pact.chatui


import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowUpward
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pact.chatui.ChatMessage
import com.pact.chatui.MessageSender
import com.pact.chatui.StreamState
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

// Tuning constants (typewriter speed, padding, FAB threshold, etc.) live on
// ChatConfig now and are read via LocalChatConfig.current inside composables.

// ---------------------------------------------------------------------------
// Markdown colors — passed to pure functions so they can use theme colors
// ---------------------------------------------------------------------------

data class MarkdownColors(
    val codeBg: Color,
    val codeText: Color,
    val keywordColor: Color,
    val fenceColor: Color
)

// ---------------------------------------------------------------------------
// Character-scanning inline markdown parser — NO regex
// ---------------------------------------------------------------------------

/** Find closing multi-char delimiter (e.g. "**") starting from [startIndex]. Returns -1 if not found. */
private fun findClosing(text: String, startIndex: Int, delimiter: String): Int {
    val delimLen = delimiter.length
    var i = startIndex
    while (i <= text.length - delimLen) {
        if (text.regionMatches(i, delimiter, 0, delimLen)) return i
        i++
    }
    return -1
}

/** Find closing single-char delimiter starting from [startIndex]. Returns -1 if not found. */
private fun findClosingSingle(text: String, startIndex: Int, delimiter: Char): Int {
    var i = startIndex
    while (i < text.length) {
        if (text[i] == delimiter) return i
        i++
    }
    return -1
}

/**
 * Append [line] with inline markdown styles (bold, italic, inline code).
 * Character-scanning approach — no regex in the hot path.
 */
fun AnnotatedString.Builder.appendInlineMarkdown(line: String, colors: MarkdownColors) {
    var i = 0
    val len = line.length

    while (i < len) {
        when {
            // Bold: **...**
            i + 1 < len && line[i] == '*' && line[i + 1] == '*' -> {
                val closeIdx = findClosing(line, i + 2, "**")
                if (closeIdx != -1) {
                    pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                    // Recursive parse for nested italic inside bold
                    appendInlineMarkdown(line.substring(i + 2, closeIdx), colors)
                    pop()
                    i = closeIdx + 2
                } else {
                    append(line[i])
                    i++
                }
            }
            // Italic: *...* (single asterisk, not **)
            line[i] == '*' && (i + 1 >= len || line[i + 1] != '*') -> {
                val closeIdx = findClosingSingle(line, i + 1, '*')
                if (closeIdx != -1) {
                    pushStyle(SpanStyle(fontStyle = FontStyle.Italic))
                    append(line.substring(i + 1, closeIdx))
                    pop()
                    i = closeIdx + 1
                } else {
                    append(line[i])
                    i++
                }
            }
            // Inline code: `...`
            line[i] == '`' -> {
                val closeIdx = findClosingSingle(line, i + 1, '`')
                if (closeIdx != -1) {
                    pushStyle(
                        SpanStyle(
                            fontFamily = FontFamily.Monospace,
                            background = colors.codeBg,
                            color = colors.codeText
                        )
                    )
                    append(line.substring(i + 1, closeIdx))
                    pop()
                    i = closeIdx + 1
                } else {
                    append(line[i])
                    i++
                }
            }
            else -> {
                append(line[i])
                i++
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Code block rendering with keyword coloring
// ---------------------------------------------------------------------------

private val KOTLIN_KEYWORDS = setOf(
    "fun", "val", "var", "class", "object", "if", "else", "when",
    "return", "import", "package", "interface", "data", "sealed",
    "private", "internal", "override", "suspend", "launch", "true", "false"
)

/** Append a single code line with monospace font and keyword highlighting. */
fun AnnotatedString.Builder.appendStyledCodeLine(line: String, colors: MarkdownColors) {
    pushStyle(SpanStyle(fontFamily = FontFamily.Monospace, background = colors.codeBg, fontSize = 14.sp))
    var i = 0
    while (i < line.length) {
        if (line[i].isLetter() || line[i] == '_') {
            val wordStart = i
            while (i < line.length && (line[i].isLetterOrDigit() || line[i] == '_')) i++
            val word = line.substring(wordStart, i)
            if (word in KOTLIN_KEYWORDS) {
                pushStyle(SpanStyle(color = colors.keywordColor, fontWeight = FontWeight.SemiBold))
                append(word)
                pop()
            } else {
                append(word)
            }
        } else {
            append(line[i])
            i++
        }
    }
    pop()
}