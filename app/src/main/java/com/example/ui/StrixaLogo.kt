package com.example.ui

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.dp
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.BullishGreen
import com.example.ui.theme.BearishRed
import com.example.ui.theme.GoldGold

/**
 * A premium, lightweight, fully-vector custom drawn brand logo for STRIXA AI.
 * Depicts futuristic candlestick charts intersecting nested glowing orbital rings
 * representing the "Trading Alignment Matrix". Avoids heavy bitmap crashes.
 */
@Composable
fun StrixaLogo(
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val cx = w / 2f
        val cy = h / 2f
        val minDim = size.minDimension

        // 1. Subtle radial glow behind the logo
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    ElectricBlue.copy(alpha = 0.25f),
                    Color.Transparent
                ),
                center = Offset(cx, cy),
                radius = minDim * 0.5f
            ),
            radius = minDim * 0.5f
        )

        // 2. Draw nested orbital rings (Trading Alignment Matrix)
        // Outer Orbit Ellipse (rotated -25 degrees)
        withTransform({
            rotate(degrees = -25f, pivot = Offset(cx, cy))
        }) {
            drawOval(
                brush = Brush.linearGradient(
                    colors = listOf(ElectricBlue, ElectricBlue.copy(alpha = 0.3f))
                ),
                topLeft = Offset(cx - minDim * 0.45f, cy - minDim * 0.22f),
                size = Size(minDim * 0.90f, minDim * 0.44f),
                style = Stroke(width = 2.5.dp.toPx())
            )
        }

        // Inner Orbit Ellipse (rotated 35 degrees)
        withTransform({
            rotate(degrees = 35f, pivot = Offset(cx, cy))
        }) {
            drawOval(
                brush = Brush.linearGradient(
                    colors = listOf(GoldGold.copy(alpha = 0.9f), GoldGold.copy(alpha = 0.2f))
                ),
                topLeft = Offset(cx - minDim * 0.38f, cy - minDim * 0.18f),
                size = Size(minDim * 0.76f, minDim * 0.36f),
                style = Stroke(width = 1.5.dp.toPx())
            )
        }

        // 3. Central Candlesticks
        val candleWidth = minDim * 0.08f
        val candleSpacing = minDim * 0.16f

        // Left Candle (Bearish - Red)
        val leftCx = cx - candleSpacing
        val leftWickTop = cy - minDim * 0.24f
        val leftWickBottom = cy + minDim * 0.28f
        val leftBodyTop = cy - minDim * 0.10f
        val leftBodyBottom = cy + minDim * 0.16f

        // Wick
        drawLine(
            color = BearishRed,
            start = Offset(leftCx, leftWickTop),
            end = Offset(leftCx, leftWickBottom),
            strokeWidth = 1.5.dp.toPx(),
            cap = StrokeCap.Round
        )
        // Body
        drawRect(
            color = BearishRed,
            topLeft = Offset(leftCx - candleWidth / 2f, leftBodyTop),
            size = Size(candleWidth, leftBodyBottom - leftBodyTop)
        )

        // Center Candle (Bullish - Green, tall and dominant)
        val centerCx = cx
        val centerWickTop = cy - minDim * 0.36f
        val centerWickBottom = cy + minDim * 0.18f
        val centerBodyTop = cy - minDim * 0.24f
        val centerBodyBottom = cy + minDim * 0.08f

        // Wick
        drawLine(
            color = BullishGreen,
            start = Offset(centerCx, centerWickTop),
            end = Offset(centerCx, centerWickBottom),
            strokeWidth = 2.dp.toPx(),
            cap = StrokeCap.Round
        )
        // Body
        drawRect(
            color = BullishGreen,
            topLeft = Offset(centerCx - (candleWidth * 1.1f) / 2f, centerBodyTop),
            size = Size(candleWidth * 1.1f, centerBodyBottom - centerBodyTop)
        )

        // Right Candle (Bullish - Green, mid height)
        val rightCx = cx + candleSpacing
        val rightWickTop = cy - minDim * 0.14f
        val rightWickBottom = cy + minDim * 0.34f
        val rightBodyTop = cy - minDim * 0.04f
        val rightBodyBottom = cy + minDim * 0.22f

        // Wick
        drawLine(
            color = BullishGreen,
            start = Offset(rightCx, rightWickTop),
            end = Offset(rightCx, rightWickBottom),
            strokeWidth = 1.5.dp.toPx(),
            cap = StrokeCap.Round
        )
        // Body
        drawRect(
            color = BullishGreen,
            topLeft = Offset(rightCx - candleWidth / 2f, rightBodyTop),
            size = Size(candleWidth, rightBodyBottom - rightBodyTop)
        )

        // 4. Matrix intersection accent nodes (glowing connector points)
        drawCircle(
            color = Color.White,
            radius = 2.5.dp.toPx(),
            center = Offset(leftCx, leftBodyTop)
        )
        drawCircle(
            color = Color.White,
            radius = 2.5.dp.toPx(),
            center = Offset(centerCx, centerBodyBottom)
        )
        drawCircle(
            color = Color.White,
            radius = 2.5.dp.toPx(),
            center = Offset(rightCx, rightBodyTop)
        )
    }
}
