package com.techlads.screen_curfew.ui.override

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.techlads.screen_curfew.ui.theme.ScreenCurfewTheme

private const val PinLength = 4

@Composable
fun PauseLockScreen(
    modifier: Modifier = Modifier,
    pinLength: Int = PinLength,
    onPinComplete: (String) -> Unit = {},
    onCancel: () -> Unit = {},
) {
    var pin by remember { mutableStateOf("") }

    fun appendDigit(digit: Char) {
        if (pin.length >= pinLength) return
        val next = pin + digit
        pin = next
        if (next.length == pinLength) {
            onPinComplete(next)
        }
    }

    fun deleteDigit() {
        if (pin.isNotEmpty()) {
            pin = pin.dropLast(1)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeContentPadding()
            .padding(horizontal = 24.dp)
            .padding(top = 8.dp, bottom = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "PAUSE LOCK",
                style = MaterialTheme.typography.labelMedium,
                color = ScreenCurfewTheme.colors.moon,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Enter your PIN",
                style = MaterialTheme.typography.displayLarge,
                color = ScreenCurfewTheme.colors.cream,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "This opens a 15-minute window. The lock returns on its own. No shame — just a pause.",
                style = MaterialTheme.typography.bodyLarge,
                color = ScreenCurfewTheme.colors.muted,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 8.dp),
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        PinDots(
            filledCount = pin.length,
            total = pinLength,
        )

        Spacer(modifier = Modifier.weight(1f))

        PinKeypad(
            onDigit = ::appendDigit,
            onBackspace = ::deleteDigit,
            onCancel = onCancel,
        )
    }
}

@Composable
private fun PinDots(
    filledCount: Int,
    total: Int,
    modifier: Modifier = Modifier,
) {
    val moon = ScreenCurfewTheme.colors.moon
    val line = ScreenCurfewTheme.colors.line

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        repeat(total) { index ->
            val filled = index < filledCount
            if (filled) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(moon, CircleShape),
                )
            } else {
                Canvas(modifier = Modifier.size(16.dp)) {
                    drawCircle(
                        color = line,
                        style = Stroke(width = 2.dp.toPx()),
                    )
                }
            }
        }
    }
}

@Composable
private fun PinKeypad(
    onDigit: (Char) -> Unit,
    onBackspace: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val keys = listOf(
        listOf("1", "2", "3"),
        listOf("4", "5", "6"),
        listOf("7", "8", "9"),
        listOf("back", "0", "cancel"),
    )

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        keys.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                row.forEach { key ->
                    when (key) {
                        "back" -> KeypadAction(
                            onClick = onBackspace,
                            modifier = Modifier.weight(1f),
                        ) {
                            BackspaceIcon(
                                tint = ScreenCurfewTheme.colors.muted,
                                modifier = Modifier.size(22.dp),
                            )
                        }
                        "cancel" -> KeypadAction(
                            onClick = onCancel,
                            modifier = Modifier.weight(1f),
                        ) {
                            Text(
                                text = "Cancel",
                                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp),
                                color = ScreenCurfewTheme.colors.muted,
                            )
                        }
                        else -> KeypadDigit(
                            label = key,
                            onClick = { onDigit(key.first()) },
                            modifier = Modifier.weight(1f),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun KeypadDigit(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .height(56.dp)
            .background(
                color = ScreenCurfewTheme.colors.elevated,
                shape = RoundedCornerShape(16.dp),
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
            ),
            color = ScreenCurfewTheme.colors.cream,
        )
    }
}

@Composable
private fun KeypadAction(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .height(56.dp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
        content = { content() },
    )
}

@Composable
private fun BackspaceIcon(
    tint: Color,
    modifier: Modifier = Modifier,
) {
    Canvas(modifier = modifier) {
        val strokeWidth = size.minDimension * 0.1f
        val path = Path().apply {
            val midY = size.height / 2f
            val tip = size.width * 0.02f
            val bodyLeft = size.width * 0.32f
            val top = size.height * 0.18f
            val bottom = size.height * 0.82f
            val right = size.width * 0.92f
            val radius = size.minDimension * 0.12f

            moveTo(bodyLeft, top)
            lineTo(right - radius, top)
            quadraticTo(right, top, right, top + radius)
            lineTo(right, bottom - radius)
            quadraticTo(right, bottom, right - radius, bottom)
            lineTo(bodyLeft, bottom)
            lineTo(tip, midY)
            close()
        }
        drawPath(path = path, color = tint, style = Stroke(width = strokeWidth))

        val xPad = size.width * 0.12f
        val cx = size.width * 0.62f
        val cy = size.height / 2f
        drawLine(
            color = tint,
            start = Offset(cx - xPad, cy - xPad),
            end = Offset(cx + xPad, cy + xPad),
            strokeWidth = strokeWidth,
        )
        drawLine(
            color = tint,
            start = Offset(cx + xPad, cy - xPad),
            end = Offset(cx - xPad, cy + xPad),
            strokeWidth = strokeWidth,
        )
    }
}

@Preview
@Composable
private fun PauseLockScreenPreview() {
    ScreenCurfewTheme {
        PauseLockScreen()
    }
}
