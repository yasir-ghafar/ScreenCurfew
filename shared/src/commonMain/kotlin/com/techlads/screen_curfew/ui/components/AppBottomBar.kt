package com.techlads.screen_curfew.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.techlads.screen_curfew.ui.navigation.AppDestination
import com.techlads.screen_curfew.ui.theme.ScreenCurfewTheme

@Composable
fun AppBottomBar(
    selected: AppDestination,
    onSelect: (AppDestination) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF101628))
            .navigationBarsPadding(),
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(ScreenCurfewTheme.colors.line),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AppDestination.entries.forEach { destination ->
                BottomBarItem(
                    destination = destination,
                    selected = destination == selected,
                    onClick = { onSelect(destination) },
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Composable
private fun BottomBarItem(
    destination: AppDestination,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val color = if (selected) {
        ScreenCurfewTheme.colors.moon
    } else {
        ScreenCurfewTheme.colors.muted
    }

    Column(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        DestinationIcon(
            destination = destination,
            tint = color,
            modifier = Modifier.size(22.dp),
        )
        Text(
            text = destination.label,
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.sp,
            ),
            color = color,
        )
    }
}

@Composable
private fun DestinationIcon(
    destination: AppDestination,
    tint: Color,
    modifier: Modifier = Modifier,
) {
    Canvas(modifier = modifier) {
        val stroke = Stroke(width = size.minDimension * 0.09f)
        when (destination) {
            AppDestination.Tonight -> {
                val r = size.minDimension * 0.42f
                val center = Offset(size.width * 0.46f, size.height * 0.52f)
                drawCircle(color = tint, radius = r, center = center)
                drawCircle(
                    color = Color(0xFF101628),
                    radius = r * 0.78f,
                    center = Offset(center.x + r * 0.42f, center.y - r * 0.18f),
                )
            }
            AppDestination.Schedules -> {
                val lineWidth = size.width * 0.62f
                val startX = (size.width - lineWidth) / 2f
                listOf(0.28f, 0.5f, 0.72f).forEach { yRatio ->
                    drawRoundRect(
                        color = tint,
                        topLeft = Offset(startX, size.height * yRatio - size.height * 0.04f),
                        size = Size(lineWidth, size.height * 0.08f),
                        cornerRadius = CornerRadius(size.height * 0.04f),
                    )
                }
            }
            AppDestination.Insights -> {
                val path = Path().apply {
                    moveTo(size.width * 0.5f, size.height * 0.12f)
                    lineTo(size.width * 0.88f, size.height * 0.5f)
                    lineTo(size.width * 0.5f, size.height * 0.88f)
                    lineTo(size.width * 0.12f, size.height * 0.5f)
                    close()
                }
                drawPath(path = path, color = tint, style = stroke)
            }
            AppDestination.Family -> {
                val center = Offset(size.width / 2f, size.height / 2f)
                drawCircle(
                    color = tint,
                    radius = size.minDimension * 0.18f,
                    center = center,
                    style = stroke,
                )
                drawCircle(
                    color = tint,
                    radius = size.minDimension * 0.36f,
                    center = center,
                    style = stroke,
                )
            }
        }
    }
}
