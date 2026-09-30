package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = ForestGreen,
    onPrimary = PureWhite,
    primaryContainer = ForestGreenLight,
    onPrimaryContainer = ForestGreenDark,
    secondary = LeafGreen,
    onSecondary = PureWhite,
    secondaryContainer = LeafGreenLight,
    onSecondaryContainer = ForestGreenDark,
    tertiary = HarvestYellow,
    onTertiary = PureWhite,
    tertiaryContainer = HarvestYellowLight,
    onTertiaryContainer = HarvestOrange,
    error = AlertRed,
    onError = PureWhite,
    errorContainer = AlertRedLight,
    onErrorContainer = AlertRed,
    background = PureWhite,
    onBackground = TextPrimary,
    surface = PureWhite,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = TextSecondary,
    outline = CardBorder
)

private val DarkColorScheme = darkColorScheme(
    primary = ForestGreenDarkTheme,
    onPrimary = DarkBackground,
    primaryContainer = ForestGreenDark,
    onPrimaryContainer = DarkTextPrimary,
    secondary = LeafGreen,
    onSecondary = DarkBackground,
    tertiary = HarvestYellow,
    onTertiary = DarkBackground,
    background = DarkBackground,
    onBackground = DarkTextPrimary,
    surface = DarkSurface,
    onSurface = DarkTextPrimary,
    surfaceVariant = DarkCard,
    onSurfaceVariant = DarkTextSecondary
)

@Composable
fun AgriNetAITheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            window?.let {
                it.statusBarColor = if (darkTheme) DarkBackground.toArgb() else ForestGreen.toArgb()
                WindowCompat.getInsetsController(it, view).isAppearanceLightStatusBars = darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
