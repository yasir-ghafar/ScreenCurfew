package com.techlads.screen_curfew.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.techlads.screen_curfew.ui.theme.ScreenCurfewColors
import com.techlads.screen_curfew.ui.theme.ScreenCurfewTheme

@Composable
fun CrescentMoon(
    modifier: Modifier = Modifier,
    size: Dp = 120.dp,
) {
    val moon = ScreenCurfewTheme.colors.moon
    val moonDeep = ScreenCurfewTheme.colors.moonDeep
    val night = ScreenCurfewColors.Night

    Box(
        modifier = modifier
            .size(size)
            .drawBehind {
                val radius = this.size.minDimension / 2f
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            moon.copy(alpha = 0.35f),
                            Color.Transparent,
                        ),
                        center = Offset(radius, radius),
                        radius = radius * 1.55f,
                    ),
                )
            },
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .drawBehind {
                    val radius = this.size.minDimension / 2f
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0xFFF3D7A0),
                                moon,
                                moonDeep,
                            ),
                            center = Offset(radius * 0.7f, radius * 0.7f),
                            radius = radius * 1.2f,
                        ),
                    )
                },
        )
        Box(
            modifier = Modifier
                .size(size * 0.73f)
                .offset(x = size * 0.28f, y = size * 0.08f)
                .background(night, CircleShape),
        )
    }
}
