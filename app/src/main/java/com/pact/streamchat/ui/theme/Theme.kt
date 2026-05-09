package com.pact.streamchat.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val StreamLightColors = lightColorScheme(
    primary = Color(0xFF1E5B52),
    onPrimary = Color(0xFFF6F8F5),
    primaryContainer = Color(0xFFCFEADF),
    onPrimaryContainer = Color(0xFF123B35),
    secondary = Color(0xFF9A6A2F),
    onSecondary = Color(0xFFFFF8F1),
    tertiary = Color(0xFF80543E),
    background = Color(0xFFF5F1E8),
    onBackground = Color(0xFF1C1B18),
    surface = Color(0xFFFFFCF7),
    onSurface = Color(0xFF1C1B18),
    surfaceVariant = Color(0xFFE8E0D2),
    onSurfaceVariant = Color(0xFF5C564A),
    outline = Color(0xFF8A8378),
    outlineVariant = Color(0xFFD1C7B8)
)

private val StreamDarkColors = darkColorScheme(
    primary = Color(0xFF8ED2BF),
    onPrimary = Color(0xFF0D2E29),
    primaryContainer = Color(0xFF174640),
    onPrimaryContainer = Color(0xFFCFEADF),
    secondary = Color(0xFFF0BF84),
    onSecondary = Color(0xFF4C2F08),
    tertiary = Color(0xFFF0BCA2),
    background = Color(0xFF161714),
    onBackground = Color(0xFFE8E2D9),
    surface = Color(0xFF1D1F1B),
    onSurface = Color(0xFFE8E2D9),
    surfaceVariant = Color(0xFF2B2E28),
    onSurfaceVariant = Color(0xFFC9C2B6),
    outline = Color(0xFF948E83),
    outlineVariant = Color(0xFF44483F)
)

private val StreamTypography = Typography(
    headlineSmall = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 30.sp,
        lineHeight = 36.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 30.sp
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 24.sp
    ),
    bodyLarge = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    labelMedium = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp
    )
)

@Composable
fun StreamChatTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme: ColorScheme = if (darkTheme) StreamDarkColors else StreamLightColors

    MaterialTheme(
        colorScheme = colorScheme,
        typography = StreamTypography,
        content = content
    )
}
