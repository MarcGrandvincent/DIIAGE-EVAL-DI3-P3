package com.rickandmortylocations.presentation.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

private val RickGreen = Color(0xFF63E6B2)
private val MortyYellow = Color(0xFFF3CD71)
private val PortalBlue = Color(0xFF72D4EE)
private val SpacePurple = Color(0xFF1E2130)
private val DarkBg = Color(0xFF151824)
private val SurfaceDark = Color(0xFF1D2232)

private val WhiteText = Color(0xFFFFFFFF)

private val RickAndMortyDarkScheme = darkColorScheme(
    primary = RickGreen,
    secondary = PortalBlue,
    tertiary = MortyYellow,
    background = DarkBg,
    surface = SurfaceDark,
    surfaceVariant = Color(0xFF252B3D),
    onPrimary = WhiteText,
    onSecondary = WhiteText,
    onTertiary = WhiteText,
    onBackground = WhiteText,
    onSurface = WhiteText,
    onSurfaceVariant = WhiteText,
    onPrimaryContainer = WhiteText,
    onSecondaryContainer = WhiteText,
    onTertiaryContainer = WhiteText
)

private val PortalGradient = Brush.verticalGradient(
    colors = listOf(
        DarkBg,
        SpacePurple.copy(alpha = 0.94f),
        SurfaceDark.copy(alpha = 0.96f)
    )
)

@Composable
fun RickAndMortyTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = RickAndMortyDarkScheme,
        content = {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.Transparent,
                contentColor = RickAndMortyDarkScheme.onBackground
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(PortalGradient)
                ) {
                    content()
                }
            }
        }
    )
}
