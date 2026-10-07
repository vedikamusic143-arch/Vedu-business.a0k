package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun VeduRibbonSymbol(
    size: Dp = 48.dp,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height

        // Left Wing Path
        val leftPath = Path().apply {
            moveTo(w * 0.16f, h * 0.12f)
            lineTo(w * 0.44f, h * 0.12f)
            lineTo(w * 0.50f, h * 0.82f)
            lineTo(w * 0.40f, h * 0.82f)
            close()
        }

        drawPath(
            path = leftPath,
            brush = Brush.linearGradient(
                colors = listOf(Color(0xFF38BDF8), Color(0xFF2563EB), Color(0xFF1D4ED8)),
                start = Offset(w * 0.16f, h * 0.12f),
                end = Offset(w * 0.50f, h * 0.82f)
            ),
            style = Fill
        )

        // Bottom Fold Apex
        val apexPath = Path().apply {
            moveTo(w * 0.38f, h * 0.65f)
            cubicTo(w * 0.42f, h * 0.78f, w * 0.48f, h * 0.88f, w * 0.50f, h * 0.92f)
            cubicTo(w * 0.52f, h * 0.88f, w * 0.58f, h * 0.78f, w * 0.62f, h * 0.65f)
            lineTo(w * 0.53f, h * 0.80f)
            cubicTo(w * 0.51f, h * 0.82f, w * 0.49f, h * 0.82f, w * 0.47f, h * 0.80f)
            close()
        }

        drawPath(
            path = apexPath,
            brush = Brush.linearGradient(
                colors = listOf(Color(0xFF1E40AF), Color(0xFF3B82F6), Color(0xFF60A5FA)),
                start = Offset(w * 0.38f, h * 0.65f),
                end = Offset(w * 0.62f, h * 0.92f)
            )
        )

        // Right Wing Path (Overlapping folded ribbon)
        val rightPath = Path().apply {
            moveTo(w * 0.84f, h * 0.12f)
            lineTo(w * 0.56f, h * 0.12f)
            lineTo(w * 0.44f, h * 0.70f)
            cubicTo(w * 0.47f, h * 0.80f, w * 0.50f, h * 0.88f, w * 0.51f, h * 0.90f)
            lineTo(w * 0.60f, h * 0.65f)
            close()
        }

        drawPath(
            path = rightPath,
            brush = Brush.linearGradient(
                colors = listOf(Color(0xFF22D3EE), Color(0xFF00F0FF), Color(0xFF0284C7), Color(0xFF1E3A8A)),
                start = Offset(w * 0.84f, h * 0.12f),
                end = Offset(w * 0.44f, h * 0.90f)
            ),
            style = Fill
        )

        // Top glossy highlight
        val glossPath = Path().apply {
            moveTo(w * 0.84f, h * 0.12f)
            lineTo(w * 0.66f, h * 0.12f)
            lineTo(w * 0.57f, h * 0.35f)
            cubicTo(w * 0.66f, h * 0.24f, w * 0.76f, h * 0.16f, w * 0.84f, h * 0.12f)
            close()
        }

        drawPath(
            path = glossPath,
            brush = Brush.linearGradient(
                colors = listOf(Color(0xFFA5F3FC), Color(0xFF06B6D4)),
                start = Offset(w * 0.66f, h * 0.12f),
                end = Offset(w * 0.84f, h * 0.35f)
            )
        )
    }
}

@Composable
fun VeduBrandWordmark(
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        // "V"
        Text(
            text = "V",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Black,
            color = Color.White,
            fontSize = 22.sp,
            letterSpacing = 2.sp
        )

        // "E" with glowing cyan middle bar
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = "E",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Black,
                color = Color.White,
                fontSize = 22.sp,
                letterSpacing = 2.sp
            )
            // Cyan bar accent on the middle stroke of E
            Box(
                modifier = Modifier
                    .padding(start = 2.dp, top = 1.dp)
                    .width(9.dp)
                    .height(3.dp)
                    .background(VeduCyan)
            )
        }

        // "DU"
        Text(
            text = "DU",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Black,
            color = Color.White,
            fontSize = 22.sp,
            letterSpacing = 2.sp
        )
    }
}

@Composable
fun VeduHeroBrandBanner(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF060B12))
            .padding(vertical = 12.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        VeduRibbonSymbol(size = 64.dp)
        Spacer(modifier = Modifier.height(6.dp))
        VeduBrandWordmark()
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = "Intelligence That Moves Business.",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Medium,
            color = Color(0xFFE2E8F0),
            letterSpacing = 1.sp
        )
        Text(
            text = "AI-Powered Business Operating & Trust Platform",
            style = MaterialTheme.typography.labelSmall,
            color = VeduCyan,
            fontSize = 9.sp
        )
    }
}
