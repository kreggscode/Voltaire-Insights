package com.kreggscode.voltairequotes.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.sin
import com.kreggscode.voltairequotes.model.Category
import com.kreggscode.voltairequotes.ui.components.GlassCard
import com.kreggscode.voltairequotes.ui.components.MorphismCard
import com.kreggscode.voltairequotes.viewmodel.QuoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: QuoteViewModel,
    onCategoryClick: (String) -> Unit,
    onQuoteClick: (Int) -> Unit,
    onAboutClick: () -> Unit,
    onWorksClick: () -> Unit
) {
    val categories by viewModel.categories.collectAsState()
    val allQuotes by viewModel.allQuotes.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    
    // Animated background gradient
    val infiniteTransition = rememberInfiniteTransition(label = "background")
    val animatedOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(10000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "offset"
    )
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.05f * (0.5f + 0.5f * sin(animatedOffset * Math.PI.toFloat())))
                    )
                )
            )
    ) {
        // Floating orbs in background
        FloatingOrbs()
        
        Scaffold(
            topBar = {
                // Glassmorphic header with status bar padding
                Surface(
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f),
                    tonalElevation = 12.dp,
                    shadowElevation = 8.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp, horizontal = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "📚",
                            fontSize = 56.sp,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                        Text(
                            text = "Voltaire Insights",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 32.sp
                        )
                        Text(
                            text = "Timeless Wisdom for Modern Minds",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                            modifier = Modifier.padding(top = 6.dp),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            },
            containerColor = Color.Transparent,
            contentWindowInsets = WindowInsets(0, 0, 0, 0)
        ) { paddingValues ->
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        end = 16.dp,
                        top = 16.dp,
                        bottom = 120.dp // Space for floating bottom navigation bar
                    ),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Enhanced Header cards with gradient backgrounds
                    item {
                        EnhancedHeaderCard(
                            onClick = onAboutClick,
                            icon = "🎭",
                            title = "About Voltaire",
                            gradient = listOf(Color(0xFF667eea), Color(0xFF764ba2))
                        )
                    }
                    
                    item {
                        EnhancedHeaderCard(
                            onClick = onWorksClick,
                            icon = "📖",
                            title = "Works of Voltaire",
                            gradient = listOf(Color(0xFFf093fb), Color(0xFFf5576c))
                        )
                    }
                
                    // Categories with staggered animation
                    items(categories) { category ->
                        CategoryCard(
                            category = category,
                            onClick = { onCategoryClick(category.name) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FloatingOrbs() {
    val infiniteTransition = rememberInfiniteTransition(label = "orbs")
    
    // Orb 1
    val orb1Offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 100f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "orb1"
    )
    
    // Orb 2
    val orb2Offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -80f,
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "orb2"
    )
    
    Box(modifier = Modifier.fillMaxSize()) {
        // Orb 1 - Top Right
        Box(
            modifier = Modifier
                .offset(x = 280.dp, y = (100 + orb1Offset).dp)
                .size(150.dp)
                .alpha(0.3f)
                .blur(50.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF667eea).copy(alpha = 0.4f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )
        
        // Orb 2 - Bottom Left
        Box(
            modifier = Modifier
                .offset(x = 50.dp, y = (500 + orb2Offset).dp)
                .size(200.dp)
                .alpha(0.25f)
                .blur(60.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFFf093fb).copy(alpha = 0.4f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EnhancedHeaderCard(
    onClick: () -> Unit,
    icon: String,
    title: String,
    gradient: List<Color>
) {
    val scale = remember { Animatable(0.8f) }
    
    LaunchedEffect(Unit) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessMediumLow
            )
        )
    }
    
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .scale(scale.value),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(gradient)
                )
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = icon,
                    fontSize = 42.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CategoryCard(
    category: Category,
    onClick: () -> Unit
) {
    // Beautiful gradient colors for each category
    val gradientColors = getCategoryGradient(category.name)
    
    // Shimmer effect
    val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
    val shimmerOffset by infiniteTransition.animateFloat(
        initialValue = -1f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer"
    )
    
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp,
            pressedElevation = 12.dp,
            hoveredElevation = 8.dp
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = gradientColors + listOf(
                            Color.White.copy(alpha = 0.1f * (0.5f + 0.5f * shimmerOffset))
                        )
                    )
                )
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Emoji at top with subtle animation
                Text(
                    text = category.icon,
                    fontSize = 52.sp,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(top = 4.dp)
                )
                
                // Category info at bottom
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = category.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        fontSize = 18.sp,
                        lineHeight = 22.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color.White.copy(alpha = 0.25f)
                    ) {
                        Text(
                            text = "${category.quoteCount} quotes",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun getCategoryGradient(categoryName: String): List<Color> {
    return when (categoryName.lowercase()) {
        // Row 1: Purple & Orange (contrasting)
        "wisdom" -> listOf(Color(0xFF667eea), Color(0xFF764ba2))  // Purple
        "freedom" -> listOf(Color(0xFFf093fb), Color(0xFFf5576c))  // Pink-Red
        
        // Row 2: Deep Purple & Orange (contrasting)
        "politics" -> listOf(Color(0xFF5f27cd), Color(0xFF341f97))  // Deep Purple
        "money" -> listOf(Color(0xFFe67e22), Color(0xFFf39c12))  // Orange
        
        // Row 3: Green & Orange (contrasting)
        "knowledge" -> listOf(Color(0xFF27ae60), Color(0xFF2ecc71))  // Green
        "friendship" -> listOf(Color(0xFFff9a56), Color(0xFFff6a3d))  // Coral Orange
        
        // Row 4: Red & Teal (contrasting)
        "courage" -> listOf(Color(0xFFc0392b), Color(0xFFe74c3c))  // Red
        "freedom of speech" -> listOf(Color(0xFF16a085), Color(0xFF1abc9c))  // Teal
        
        // Row 5: Gray & Light Blue (contrasting)
        "war & peace" -> listOf(Color(0xFF7f8c8d), Color(0xFF95a5a6))  // Gray
        "history" -> listOf(Color(0xFFa1c4fd), Color(0xFfc2e9fb))  // Light Blue
        
        // Row 6: Purple & Teal (contrasting)
        "education" -> listOf(Color(0xFFfbc2eb), Color(0xFFa6c1ee))  // Light Purple
        "justice" -> listOf(Color(0xFF30cfd0), Color(0xFF330867))  // Teal-Purple
        
        // Row 7: Pink-Orange & Teal (contrasting)
        "religion" -> listOf(Color(0xFFfa709a), Color(0xFFfee140))  // Pink-Yellow
        "time" -> listOf(Color(0xFF16a085), Color(0xFF1abc9c))  // Teal
        
        // Row 8: Yellow & Red (contrasting)
        "truth" -> listOf(Color(0xFFf39c12), Color(0xFFf1c40f))  // Yellow-Gold
        "happiness" -> listOf(Color(0xFFe74c3c), Color(0xFFec7063))  // Red-Coral
        
        // Row 9: Blue & Gray (contrasting)
        "philosophy" -> listOf(Color(0xFF3498db), Color(0xFF5dade2))  // Blue
        "death" -> listOf(Color(0xFF868f96), Color(0xFF596164))  // Gray
        
        // Additional categories
        "tolerance" -> listOf(Color(0xFF4facfe), Color(0xFF00f2fe))  // Cyan
        "love" -> listOf(Color(0xFFff9a9e), Color(0xFFfecfef))  // Pink
        "work" -> listOf(Color(0xFFffecd2), Color(0xFFfcb69f))  // Peach
        "science" -> listOf(Color(0xFF89f7fe), Color(0xFF66a6ff))  // Blue
        "government" -> listOf(Color(0xFFfdcbf1), Color(0xFFe6dee9))  // Light Pink
        "morality" -> listOf(Color(0xFFffeaa7), Color(0xFFfdcb6e))  // Yellow
        "society" -> listOf(Color(0xFFff6b6b), Color(0xFFfeca57))  // Red-Yellow
        "women" -> listOf(Color(0xFFfccb90), Color(0xFFd57eeb))  // Orange-Purple
        "men" -> listOf(Color(0xFF2193b0), Color(0xFF6dd5ed))  // Blue
        "humanity" -> listOf(Color(0xFFee9ca7), Color(0xFFffdde1))  // Pink
        "success" -> listOf(Color(0xFFfad0c4), Color(0xFFffd1ff))  // Light Pink
        "art" -> listOf(Color(0xFF8e44ad), Color(0xFF9b59b6))  // Purple
        else -> listOf(Color(0xFF667eea), Color(0xFF764ba2))
    }
}
