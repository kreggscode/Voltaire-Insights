package com.kreggscode.voltairequotes.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Voltaire Insights",
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
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
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Header cards
                item {
                    MorphismCard(
                        onClick = onAboutClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "About Voltaire",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
                
                item {
                    MorphismCard(
                        onClick = onWorksClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.MenuBook,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Works of Voltaire",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
                
                // Categories
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CategoryCard(
    category: Category,
    onClick: () -> Unit
) {
    // Beautiful gradient colors for each category
    val gradientColors = getCategoryGradient(category.name)
    
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = gradientColors
                    )
                )
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Emoji at top
                Text(
                    text = category.icon,
                    fontSize = 48.sp,
                    modifier = Modifier.align(Alignment.Start)
                )
                
                // Category info at bottom
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = category.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${category.quoteCount} quotes",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun getCategoryGradient(categoryName: String): List<Color> {
    return when (categoryName.lowercase()) {
        "wisdom" -> listOf(Color(0xFF667eea), Color(0xFF764ba2))
        "freedom" -> listOf(Color(0xFFf093fb), Color(0xFFf5576c))
        "tolerance" -> listOf(Color(0xFF4facfe), Color(0xFF00f2fe))
        "religion" -> listOf(Color(0xFFfa709a), Color(0xFFfee140))
        "justice" -> listOf(Color(0xFF30cfd0), Color(0xFF330867))
        "love" -> listOf(Color(0xFFff9a9e), Color(0xFFfecfef))
        "work" -> listOf(Color(0xFFffecd2), Color(0xFFfcb69f))
        "science" -> listOf(Color(0xFF89f7fe), Color(0xFF66a6ff))
        "education" -> listOf(Color(0xFFfbc2eb), Color(0xFFa6c1ee))
        "government" -> listOf(Color(0xFFfdcbf1), Color(0xFFe6dee9))
        "history" -> listOf(Color(0xFFa1c4fd), Color(0xFfc2e9fb))
        "morality" -> listOf(Color(0xFFffeaa7), Color(0xFFfdcb6e))
        "society" -> listOf(Color(0xFFff6b6b), Color(0xFFfeca57))
        "politics" -> listOf(Color(0xFF5f27cd), Color(0xFF341f97))
        "death" -> listOf(Color(0xFF868f96), Color(0xFF596164))
        "women" -> listOf(Color(0xFFfccb90), Color(0xFFd57eeb))
        "men" -> listOf(Color(0xFF2193b0), Color(0xFF6dd5ed))
        "humanity" -> listOf(Color(0xFFee9ca7), Color(0xFFffdde1))
        "success" -> listOf(Color(0xFFfad0c4), Color(0xFFffd1ff))
        "art" -> listOf(Color(0xFF8e44ad), Color(0xFF9b59b6))
        "time" -> listOf(Color(0xFF16a085), Color(0xFF1abc9c))
        "truth" -> listOf(Color(0xFFf39c12), Color(0xFFf1c40f))
        "happiness" -> listOf(Color(0xFFe74c3c), Color(0xFFec7063))
        "philosophy" -> listOf(Color(0xFF3498db), Color(0xFF5dade2))
        "money" -> listOf(Color(0xFFe67e22), Color(0xFFf39c12))
        "knowledge" -> listOf(Color(0xFF27ae60), Color(0xFF2ecc71))
        "friendship" -> listOf(Color(0xFFf39c12), Color(0xFFe67e22))
        "courage" -> listOf(Color(0xFFc0392b), Color(0xFFe74c3c))
        "freedom of speech" -> listOf(Color(0xFF16a085), Color(0xFF27ae60))
        "war & peace" -> listOf(Color(0xFF7f8c8d), Color(0xFF95a5a6))
        else -> listOf(Color(0xFF667eea), Color(0xFF764ba2))
    }
}
