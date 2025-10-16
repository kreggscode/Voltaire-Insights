package com.kreggscode.voltairequotes.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.sin
import com.kreggscode.voltairequotes.R
import com.kreggscode.voltairequotes.ui.components.MorphismCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    onBackClick: () -> Unit
) {
    // Animated background
    val infiniteTransition = rememberInfiniteTransition(label = "background")
    val animatedOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
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
                        Color(0xFF764ba2).copy(alpha = 0.08f * (0.5f + 0.5f * sin(animatedOffset * Math.PI.toFloat())))
                    )
                )
            )
    ) {
        // Floating orb
        Box(
            modifier = Modifier
                .offset(x = 100.dp, y = (250 + animatedOffset * 60).dp)
                .size(190.dp)
                .alpha(0.18f)
                .blur(70.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF764ba2).copy(alpha = 0.5f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )
        
        Scaffold(
            topBar = {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f),
                    tonalElevation = 12.dp,
                    shadowElevation = 8.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "🎭",
                                fontSize = 40.sp
                            )
                            Text(
                                text = "About Voltaire",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 24.sp
                            )
                        }
                    }
                }
            },
            containerColor = Color.Transparent,
            contentWindowInsets = WindowInsets(0, 0, 0, 0)
        ) { paddingValues ->
            var selectedTabIndex by remember { mutableStateOf(0) }
            val tabs = listOf("Biography", "Timeline", "Major Works")
            
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // Tabs
                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
                    contentColor = MaterialTheme.colorScheme.primary
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index },
                            text = {
                                Text(
                                    text = title,
                                    fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        )
                    }
                }
                
                // Tab Content
                when (selectedTabIndex) {
                    0 -> BiographyTab()
                    1 -> TimelineTab()
                    2 -> MajorWorksTab()
                }
            }
        }
    }
}

@Composable
private fun BiographyTab() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            MorphismCard(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Voltaire Portrait
                        Image(
                            painter = painterResource(id = R.drawable.voltaire_portrait),
                            contentDescription = "Voltaire Portrait",
                            modifier = Modifier
                                .size(120.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Text(
                            text = "François-Marie Arouet",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        
                        Text(
                            text = "Known as Voltaire",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = "1694 - 1778",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    }
                }
            }
        
        item {
            MorphismCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Biography",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    
                    Text(
                        text = "Voltaire (François-Marie Arouet) was a French Enlightenment writer, historian, and philosopher famous for his wit, his criticism of Christianity—especially the Roman Catholic Church—and his advocacy of freedom of speech, freedom of religion, and separation of church and state.",
                        style = MaterialTheme.typography.bodyLarge,
                        lineHeight = MaterialTheme.typography.bodyLarge.lineHeight
                    )
                    
                    Text(
                        text = "Born in Paris in 1694, Voltaire became one of France's greatest writers and philosophers. He was a versatile and prolific writer, producing works in almost every literary form, including plays, poems, novels, essays, histories, and scientific expositions. He wrote more than 20,000 letters and 2,000 books and pamphlets.",
                        style = MaterialTheme.typography.bodyLarge,
                        lineHeight = MaterialTheme.typography.bodyLarge.lineHeight
                    )
                    
                    Text(
                        text = "Voltaire was an outspoken advocate of civil liberties, and he was at constant risk from the strict censorship laws of the Catholic French monarchy. His polemics witheringly satirized intolerance, religious dogma, and the French institutions of his day. His best-known work, Candide, is a satirical novella that attacks the optimism of Leibnizian philosophy.",
                        style = MaterialTheme.typography.bodyLarge,
                        lineHeight = MaterialTheme.typography.bodyLarge.lineHeight
                    )
                }
            }
        }
        
        item {
            MorphismCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Key Contributions",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    
                    ContributionItem(
                        icon = "✍️",
                        title = "Literary Works",
                        description = "Author of Candide, Zadig, and numerous philosophical treatises"
                    )
                    
                    ContributionItem(
                        icon = "💭",
                        title = "Philosophy",
                        description = "Champion of reason, tolerance, and freedom of thought"
                    )
                    
                    ContributionItem(
                        icon = "⚖️",
                        title = "Social Reform",
                        description = "Advocate for civil liberties and judicial reform"
                    )
                    
                    ContributionItem(
                        icon = "🕊️",
                        title = "Religious Tolerance",
                        description = "Fought against religious intolerance and fanaticism"
                    )
                }
            }
        }
        
        item {
            MorphismCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Legacy",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    
                    Text(
                        text = "Voltaire's ideas influenced the French Revolution and the development of liberal political thought. His advocacy for civil liberties continues to inspire movements for freedom and human rights worldwide. He is remembered as a crusader against tyranny, bigotry, and cruelty.",
                        style = MaterialTheme.typography.bodyLarge,
                        lineHeight = MaterialTheme.typography.bodyLarge.lineHeight
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "\"I disapprove of what you say, but I will defend to the death your right to say it.\"",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
private fun TimelineTab() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Life Timeline",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        
        item { TimelineEvent("1694", "Birth", "Born François-Marie Arouet in Paris, France") }
        item { TimelineEvent("1718", "Voltaire Pen Name", "Adopted the pen name 'Voltaire' and gained fame with his play Œdipe") }
        item { TimelineEvent("1726-1729", "England Exile", "Exiled to England, influenced by English philosophy and politics") }
        item { TimelineEvent("1734", "Lettres Philosophiques", "Published philosophical letters criticizing French institutions") }
        item { TimelineEvent("1751-1753", "Prussia", "Lived at the court of Frederick the Great in Prussia") }
        item { TimelineEvent("1759", "Candide", "Published his masterpiece 'Candide', a satirical novella") }
        item { TimelineEvent("1760-1778", "Ferney Period", "Settled in Ferney, France, his most productive period") }
        item { TimelineEvent("1762", "Calas Affair", "Championed Jean Calas, fighting judicial injustice") }
        item { TimelineEvent("1778", "Death", "Died in Paris at age 83, leaving an immense legacy") }
    }
}

@Composable
private fun MajorWorksTab() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Major Works",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        
        item {
            WorkItem(
                year = "1759",
                title = "Candide",
                description = "A satirical novella that criticizes optimism and explores the problem of evil. One of Voltaire's most famous works.",
                icon = "📖"
            )
        }
        
        item {
            WorkItem(
                year = "1734",
                title = "Lettres Philosophiques (Philosophical Letters)",
                description = "Essays praising English political and religious freedom, criticizing French absolutism.",
                icon = "✉️"
            )
        }
        
        item {
            WorkItem(
                year = "1747",
                title = "Zadig",
                description = "A philosophical tale exploring fate, justice, and the nature of happiness.",
                icon = "📚"
            )
        }
        
        item {
            WorkItem(
                year = "1763",
                title = "Treatise on Tolerance",
                description = "Written after the Calas affair, arguing for religious tolerance and against fanaticism.",
                icon = "🕊️"
            )
        }
        
        item {
            WorkItem(
                year = "1764",
                title = "Philosophical Dictionary",
                description = "An encyclopedic dictionary presenting Voltaire's philosophical views on various topics.",
                icon = "📕"
            )
        }
        
        item {
            WorkItem(
                year = "1756-1763",
                title = "Essay on the Customs and the Spirit of the Nations",
                description = "A pioneering work of cultural history covering world civilizations.",
                icon = "🌍"
            )
        }
        
        item {
            WorkItem(
                year = "1718",
                title = "Œdipe (Oedipus)",
                description = "His first major theatrical success, a tragedy that established his reputation.",
                icon = "🎭"
            )
        }
    }
}

@Composable
private fun TimelineEvent(
    year: String,
    title: String,
    description: String
) {
    MorphismCard(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Year badge
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.size(60.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = year,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        textAlign = TextAlign.Center,
                        fontSize = 11.sp
                    )
                }
            }
            
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Composable
private fun WorkItem(
    year: String,
    title: String,
    description: String,
    icon: String
) {
    MorphismCard(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = icon,
                    style = MaterialTheme.typography.headlineMedium
                )
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = year,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
            Text(
                text = description,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                lineHeight = 22.sp
            )
        }
    }
}

@Composable
private fun ContributionItem(
    icon: String,
    title: String,
    description: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = icon,
            style = MaterialTheme.typography.headlineMedium
        )
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}
