package com.techlads.screen_curfew.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val ScreenCurfewDarkColorScheme = darkColorScheme(
    primary = ScreenCurfewColors.Moon,
    onPrimary = ScreenCurfewColors.Ink,
    primaryContainer = ScreenCurfewColors.Elevated,
    onPrimaryContainer = ScreenCurfewColors.Cream,
    secondary = ScreenCurfewColors.Sage,
    onSecondary = ScreenCurfewColors.Ink,
    secondaryContainer = ScreenCurfewColors.ChipAndroidBg,
    onSecondaryContainer = ScreenCurfewColors.Sage,
    tertiary = ScreenCurfewColors.Rose,
    onTertiary = ScreenCurfewColors.Cream,
    tertiaryContainer = ScreenCurfewColors.PauseBg,
    onTertiaryContainer = ScreenCurfewColors.Rose,
    background = ScreenCurfewColors.Night,
    onBackground = ScreenCurfewColors.Cream,
    surface = ScreenCurfewColors.Surface,
    onSurface = ScreenCurfewColors.Cream,
    surfaceVariant = ScreenCurfewColors.Elevated,
    onSurfaceVariant = ScreenCurfewColors.Muted,
    outline = ScreenCurfewColors.Line,
    outlineVariant = ScreenCurfewColors.Line,
    inverseSurface = ScreenCurfewColors.Cream,
    inverseOnSurface = ScreenCurfewColors.Ink,
    inversePrimary = ScreenCurfewColors.MoonDeep,
)

@Immutable
data class ScreenCurfewExtendedColors(
    val moon: Color = ScreenCurfewColors.Moon,
    val moonDeep: Color = ScreenCurfewColors.MoonDeep,
    val cream: Color = ScreenCurfewColors.Cream,
    val muted: Color = ScreenCurfewColors.Muted,
    val sage: Color = ScreenCurfewColors.Sage,
    val rose: Color = ScreenCurfewColors.Rose,
    val ink: Color = ScreenCurfewColors.Ink,
    val line: Color = ScreenCurfewColors.Line,
    val elevated: Color = ScreenCurfewColors.Elevated,
    val chipAndroidBg: Color = ScreenCurfewColors.ChipAndroidBg,
    val chipIosBg: Color = ScreenCurfewColors.ChipIosBg,
    val pauseBg: Color = ScreenCurfewColors.PauseBg,
)

val LocalScreenCurfewColors = staticCompositionLocalOf { ScreenCurfewExtendedColors() }

object ScreenCurfewTheme {
    val colors: ScreenCurfewExtendedColors
        @Composable
        get() = LocalScreenCurfewColors.current
}

@Composable
fun ScreenCurfewTheme(
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalScreenCurfewColors provides ScreenCurfewExtendedColors(),
    ) {
        MaterialTheme(
            colorScheme = ScreenCurfewDarkColorScheme,
            typography = ScreenCurfewTypography,
            shapes = ScreenCurfewShapes,
            content = content,
        )
    }
}
