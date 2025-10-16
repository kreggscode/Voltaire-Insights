package com.kreggscode.voltairequotes.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
)

@Composable
fun FloatingBottomBar(
    items: List<BottomNavItem>,
    currentRoute: String,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val isDark = MaterialTheme.colorScheme.background == Color(0xFF000000)
    
    // Detect if keyboard is visible
    val imeInsets = WindowInsets.ime
    val density = LocalDensity.current
    val isKeyboardVisible = remember {
        derivedStateOf {
            imeInsets.getBottom(density) > 0
        }
    }.value
    
    // Hide bottom bar when keyboard is visible
    AnimatedVisibility(
        visible = !isKeyboardVisible,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it })
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .background(Color.Transparent),
            contentAlignment = Alignment.BottomCenter
        ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                modifier = Modifier
                    .height(72.dp)
                    .fillMaxWidth(0.95f)
                    .shadow(
                        elevation = 16.dp,
                        shape = RoundedCornerShape(28.dp),
                        ambientColor = Color.Black.copy(alpha = 0.3f),
                        spotColor = Color.Black.copy(alpha = 0.3f)
                    ),
                shape = RoundedCornerShape(28.dp),
                color = if (isDark) {
                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.95f)
                } else {
                    MaterialTheme.colorScheme.surface.copy(alpha = 0.98f)
                },
                tonalElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items.forEach { item ->
                        BottomNavItemView(
                            item = item,
                            isSelected = currentRoute == item.route,
                            onClick = { onItemClick(item.route) }
                        )
                    }
                }
            }
        }
        }
    }
}

@Composable
private fun BottomNavItemView(
    item: BottomNavItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.1f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )
    
    val iconColor by animateColorAsState(
        targetValue = if (isSelected) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        },
        animationSpec = tween(300),
        label = "iconColor"
    )
    
    Column(
        modifier = Modifier
            .width(64.dp)
            .fillMaxHeight()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier.size(40.dp)
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.label,
                tint = iconColor,
                modifier = Modifier.size(24.dp)
            )
        }
        
        if (isSelected) {
            Text(
                text = item.label,
                style = MaterialTheme.typography.labelSmall,
                color = iconColor,
                fontSize = 10.sp,
                maxLines = 1
            )
        }
    }
}
