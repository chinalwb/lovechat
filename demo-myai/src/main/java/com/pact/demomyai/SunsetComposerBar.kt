package com.pact.demomyai

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowUpward
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pact.chatui.LocalChatColors

/**
 * Pill-style composer for the MyAI demo: the text input and send button
 * share a single rounded container with a solid background. Send button
 * sits inside the pill on the right edge. No outer dividers or hints.
 */
@Composable
fun SunsetComposerBar(
    draft: String,
    isStreaming: Boolean,
    onDraftChange: (String) -> Unit,
    onSendClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val sendEnabled = draft.isNotBlank() && !isStreaming
    val colors = LocalChatColors.current

    // ChatScreen's outer frame already applies the IME + nav bar inset,
    // so this Box only owns the screen-edge padding around the pill. The
    // bottom padding is the visible gap between the pill and the keyboard
    // (or nav-bar gesture area when the keyboard is down).
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(28.dp),
            color = colors.composerSurface,
            tonalElevation = 0.dp,
        ) {
            Row(
                modifier = Modifier.padding(start = 4.dp, end = 6.dp, top = 0.dp, bottom = 0.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TextField(
                    value = draft,
                    onValueChange = onDraftChange,
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Ask me anything…") },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                )
                Surface(
                    onClick = { if (sendEnabled) onSendClick() },
                    shape = CircleShape,
                    color = if (sendEnabled) colors.sendButtonBackground else colors.sendButtonDisabledBackground,
                    modifier = Modifier.size(44.dp),
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowUpward,
                            contentDescription = "Send",
                            tint = if (sendEnabled) colors.sendButtonContent else colors.sendButtonDisabledContent,
                        )
                    }
                }
            }
        }
    }
}
