package com.kreggscode.voltairequotes.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kreggscode.voltairequotes.ui.theme.LightPrimary
import com.kreggscode.voltairequotes.ui.theme.DarkPrimary
import kotlin.math.sin

@Composable
fun SplashScreen(
    onSplashComplete: () -> Unit
) {
    val isDark = MaterialTheme.colorScheme.background == Color(0xFF000000)

    // Animation states
    val infiniteTransition = rememberInfiniteTransition(label = "splash")

    // Main logo scale animation
    val logoScale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        ),
        label = "logoScale"
    )

    // Background gradient animation
    val gradientOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "gradientOffset"
    )

    // Text fade animation
    val textAlpha by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(1500, delayMillis = 500),
        label = "textAlpha"
    )

    // Floating particles animation
    val particleOffset1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 50f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "particle1"
    )

    val particleOffset2 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -40f,
        animationSpec = infiniteRepeatable(
            animation = tween(5000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "particle2"
    )

    // Loading dots animation
    val loadingDots by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 3f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "loadingDots"
    )

    // Auto-dismiss after 3 seconds
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(3000)
        onSplashComplete()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = if (isDark) {
                        listOf(
                            Color(0xFF0A0A0A),
                            Color(0xFF1A1A1A),
                            DarkPrimary.copy(alpha = 0.1f * (0.5f + 0.5f * sin(gradientOffset * Math.PI.toFloat())))
                        )
                    } else {
                        listOf(
                            Color(0xFFF8F9FA),
                            Color(0xFFFFFFFF),
                            LightPrimary.copy(alpha = 0.1f * (0.5f + 0.5f * sin(gradientOffset * Math.PI.toFloat())))
                        )
                    }
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // Floating background elements
        Box(
            modifier = Modifier
                .offset(x = 150.dp, y = (-100 + particleOffset1).dp)
                .size(120.dp)
                .alpha(0.3f)
                .blur(40.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            (if (isDark) DarkPrimary else LightPrimary).copy(alpha = 0.6f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )

        Box(
            modifier = Modifier
                .offset(x = (-120).dp, y = (200 + particleOffset2).dp)
                .size(100.dp)
                .alpha(0.25f)
                .blur(35.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF9C88FF).copy(alpha = 0.5f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )

        // Main content
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(32.dp)
        ) {
            // Voltaire emoji with animated scale
            Text(
                text = "🧙‍♂️",
                fontSize = 120.sp,
                modifier = Modifier
                    .scale(logoScale)
                    .padding(bottom = 24.dp)
            )

            // App title with fade animation
            Text(
                text = "Voltaire",
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.ExtraBold,
                color = if (isDark) DarkPrimary else LightPrimary,
                textAlign = TextAlign.Center,
                fontSize = 48.sp,
                modifier = Modifier.alpha(textAlpha)
            )

            Text(
                text = "Insights",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                textAlign = TextAlign.Center,
                fontSize = 36.sp,
                modifier = Modifier
                    .alpha(textAlpha)
                    .padding(bottom = 16.dp)
            )

            // Tagline
            Text(
                text = "Timeless Wisdom for Modern Minds",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                modifier = Modifier
                    .alpha(textAlpha)
                    .padding(bottom = 48.dp)
            )

            // Animated loading dots
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(3) { index ->
                    val dotAlpha = when {
                        loadingDots >= index + 1 -> 1f
                        loadingDots >= index -> loadingDots - index
                        else -> 0f
                    }

                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(
                                color = (if (isDark) DarkPrimary else LightPrimary).copy(alpha = dotAlpha),
                                shape = CircleShape
                            )
                    )
                }
            }
        }
    }
}
