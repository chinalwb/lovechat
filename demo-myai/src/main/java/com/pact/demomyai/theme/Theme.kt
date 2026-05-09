package com.pact.demomyai.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.pact.chatui.ChatColors
import com.pact.chatui.ChatConfig
import com.pact.chatui.ChatDefaults
import androidx.compose.ui.unit.dp

private val LightColors = lightColorScheme(
    primary = SunsetCoral,
    onPrimary = SunsetGoldOn,
    primaryContainer = SunsetPeach,
    onPrimaryContainer = SunsetPeachDeep,
    secondary = SunsetSoftRose,
    tertiary = SunsetGold,
    background = SunsetCream,
    onBackground = SunsetWarmBrown,
    surface = SunsetCream,
    onSurface = SunsetWarmBrown,
    surfaceVariant = SunsetCreamDeep,
    onSurfaceVariant = SunsetPeachDeep,
)

private val DarkColors = darkColorScheme(
    primary = SunsetCoralDeep,
    onPrimary = SunsetGoldOn,
    primaryContainer = SunsetPeachDeep,
    onPrimaryContainer = SunsetPeach,
    secondary = SunsetSoftRose,
    tertiary = SunsetGold,
    background = SunsetDarkBg,
    onBackground = SunsetDarkText,
    surface = SunsetDarkSurface,
    onSurface = SunsetDarkText,
    surfaceVariant = SunsetDarkSurface,
    onSurfaceVariant = SunsetPeach,
)

@Composable
fun SunsetTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = SunsetTypography,
        content = content,
    )
}

/**
 * `ChatColors` overrides specific to the sunset theme. Most fields are
 * fine as MaterialTheme-derived defaults via [ChatDefaults.colors]; we
 * tweak a few that benefit from a non-Material pick (gradient, code
 * keyword color, etc.).
 */
@Composable
fun sunsetChatColors(): ChatColors {
    val base = ChatDefaults.colors()
    return base.copy(
        keywordHighlight = SunsetTerracotta,
        // Fully opaque gradient stops so the rendered gradient is consistent
        // with the composer and nav-bar backdrop colors. Translucent stops
        // would blend with the window backdrop (white) and render as a
        // washed-out peach, visibly different from the saturated peach used
        // by the composer and nav-bar bands.
        backgroundGradient = listOf(
            MaterialTheme.colorScheme.background,
            SunsetCreamDeep,
            SunsetPeach,
        ),
    )
}

/** ChatConfig tuned to feel "slow, deliberate" matching the editorial vibe. */
val SunsetChatConfig = ChatConfig(
    typewriterCharsPerSec = 60,
    bubbleInnerHorizontalPadding = 22.dp,
    bubbleInnerVerticalPadding = 16.dp,
    paragraphSpacing = 12.dp,
    fabHiddenTextThreshold = 30.dp,
)
