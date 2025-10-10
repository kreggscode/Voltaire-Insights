package com.kreggscode.voltairequotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kreggscode.voltairequotes.navigation.NavGraph
import com.kreggscode.voltairequotes.navigation.Screen
import com.kreggscode.voltairequotes.notifications.NotificationScheduler
import com.kreggscode.voltairequotes.ui.components.BottomNavItem
import com.kreggscode.voltairequotes.ui.components.FloatingBottomBar
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
            
            VoltaireTheme(darkTheme = isDarkMode) {
                MainScreen(
                    quoteViewModel = quoteViewModel,
                    chatViewModel = chatViewModel
                )
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
        BottomNavItem(Screen.Chat.route, Icons.Default.Chat, "Chat"),
        BottomNavItem(Screen.Favorites.route, Icons.Default.Favorite, "Favorites"),
        BottomNavItem(Screen.Works.route, Icons.Default.MenuBook, "Works"),
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
                        navController.navigate(route) {
                            popUpTo(Screen.Home.route) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            NavGraph(
                navController = navController,
                quoteViewModel = quoteViewModel,
                chatViewModel = chatViewModel
            )
        }
    }
}
