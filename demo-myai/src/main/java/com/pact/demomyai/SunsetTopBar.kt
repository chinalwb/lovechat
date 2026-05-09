package com.pact.demomyai

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pact.chatui.LocalChatColors

/**
 * Sunset-themed top bar — the full slot implementation for the MyAI demo.
 *
 * Owns everything: positioning at the top of the chat frame, the glassy
 * translucent gradient backdrop (using sunset palette colors), status-bar
 * padding, and the chrome (Tune / MyAI title / overflow). Reports its
 * measured height to the library via [onHeightChanged] so
 * `LazyColumn.contentPadding.top` reserves space below the bar.
 */
@Composable
fun BoxScope.SunsetTopBar(onHeightChanged: (Int) -> Unit) {
    val backdrop = LocalChatColors.current.backgroundGradient.first()
    Box(
        modifier = Modifier
            .align(Alignment.TopCenter)
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    0.0f to backdrop.copy(alpha = 0.95f),
                    0.1f to backdrop.copy(alpha = 0.95f),
                    0.5f to backdrop.copy(alpha = 0.5f),
                    1.0f to backdrop.copy(alpha = 0.0f),
                )
            )
            .onSizeChanged { onHeightChanged(it.height) },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Left: hamburger + title together. The title sits next to the
            // icon (not centered in the bar) and uses a slightly smaller
            // type ramp than a typical app-bar title.
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Rounded.Menu,
                        contentDescription = "Chat history",
                    )
                }
                Text(
                    text = "MyAI",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                )
            }
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Rounded.MoreVert,
                    contentDescription = "More",
                )
            }
        }
    }
}
