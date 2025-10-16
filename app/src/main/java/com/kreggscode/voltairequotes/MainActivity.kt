package com.kreggscode.voltairequotes

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kreggscode.voltairequotes.navigation.NavGraph
import com.kreggscode.voltairequotes.navigation.Screen
import com.kreggscode.voltairequotes.notifications.NotificationScheduler
import com.kreggscode.voltairequotes.ui.components.BottomNavItem
import com.kreggscode.voltairequotes.ui.components.FloatingBottomBar
import com.kreggscode.voltairequotes.ui.screens.SplashScreen
import com.kreggscode.voltairequotes.ui.theme.VoltaireTheme
import com.kreggscode.voltairequotes.viewmodel.ChatViewModel
import com.kreggscode.voltairequotes.viewmodel.QuoteViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    
    private val quoteViewModel: QuoteViewModel by viewModels()
    private val chatViewModel: ChatViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Observe notification settings
        lifecycleScope.launch {
            quoteViewModel.isDailyNotificationEnabled.collect { enabled ->
                if (enabled) {
                    NotificationScheduler.scheduleDailyNotification(this@MainActivity)
                } else {
                    NotificationScheduler.cancelDailyNotification(this@MainActivity)
                }
            }
        }
        
        setContent {
            val isDarkMode by quoteViewModel.isDarkMode.collectAsState()
            var showSplash by remember { mutableStateOf(true) }
            
            // Update system bar colors based on theme
            LaunchedEffect(isDarkMode) {
                if (isDarkMode) {
                    // Dark mode: light icons on dark background
                    enableEdgeToEdge(
                        statusBarStyle = SystemBarStyle.dark(
                            scrim = android.graphics.Color.TRANSPARENT
                        ),
                        navigationBarStyle = SystemBarStyle.dark(
                            scrim = android.graphics.Color.TRANSPARENT
                        )
                    )
                } else {
                    // Light mode: dark icons on light background
                    enableEdgeToEdge(
                        statusBarStyle = SystemBarStyle.light(
                            scrim = android.graphics.Color.TRANSPARENT,
                            darkScrim = android.graphics.Color.TRANSPARENT
                        ),
                        navigationBarStyle = SystemBarStyle.light(
                            scrim = android.graphics.Color.TRANSPARENT,
                            darkScrim = android.graphics.Color.TRANSPARENT
                        )
                    )
                }
            }
            
            VoltaireTheme(darkTheme = isDarkMode) {
                if (showSplash) {
                    SplashScreen(onSplashComplete = { showSplash = false })
                } else {
                    MainScreen(
                        quoteViewModel = quoteViewModel,
                        chatViewModel = chatViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    quoteViewModel: QuoteViewModel,
    chatViewModel: ChatViewModel
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    val bottomNavItems = listOf(
        BottomNavItem(Screen.Home.route, Icons.Default.Home, "Home"),
        BottomNavItem(Screen.Chat.route, Icons.AutoMirrored.Filled.Chat, "Chat"),
        BottomNavItem(Screen.Favorites.route, Icons.Default.Favorite, "Favorites"),
        BottomNavItem(Screen.Works.route, Icons.AutoMirrored.Filled.MenuBook, "Works"),
        BottomNavItem(Screen.Settings.route, Icons.Default.Settings, "Settings")
    )
    
    val showBottomBar = currentRoute in bottomNavItems.map { it.route }
    
    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                FloatingBottomBar(
                    items = bottomNavItems,
                    currentRoute = currentRoute ?: Screen.Home.route,
                    onItemClick = { route ->
                        if (currentRoute != route) {
                            navController.navigate(route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                popUpTo(Screen.Home.route) {
                                    saveState = true
                                    inclusive = false
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                    }
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    start = 0.dp,
                    end = 0.dp,
                    bottom = 0.dp
                )
        ) {
            NavGraph(
                navController = navController,
                quoteViewModel = quoteViewModel,
                chatViewModel = chatViewModel
            )
        }
    }
}
