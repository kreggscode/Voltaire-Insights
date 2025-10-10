# 🎯 Voltaire Insights - Quick Reference Card

## 🚀 Build & Run Commands

```bash
# Open in Android Studio
# File → Open → voltaire/

# Build from command line (if needed)
./gradlew build

# Install on device
./gradlew installDebug

# Clean build
./gradlew clean build
```

## 📱 App Navigation Flow

```
Home Screen
├── Category Card → Category Quotes Screen → Quote Detail
├── About Voltaire Button → About Screen
└── Works Button → Works Screen → Work Quotes Screen → Quote Detail

Chat Screen (Tab 2)
└── Interactive chat with Voltaire

Favorites Screen (Tab 3)
└── Saved quotes → Quote Detail

Works Screen (Tab 4)
└── Work item → Work Quotes Screen → Quote Detail

Settings Screen (Tab 5)
├── Dark Mode Toggle
├── Daily Notification Toggle
└── About Voltaire → About Screen

Quote Detail Screen
├── Favorite Button (toggle)
├── Share Button (system share)
└── Chat Button → Chat Screen
```

## 🎨 Key Components

### MorphismCard
```kotlin
MorphismCard(
    onClick = { /* action */ },
    cornerRadius = 20.dp,
    elevation = 8.dp
) {
    // Your content
}
```

### GlassCard
```kotlin
GlassCard(
    onClick = { /* action */ },
    cornerRadius = 20.dp
) {
    // Your content
}
```

### FloatingBottomBar
```kotlin
FloatingBottomBar(
    items = bottomNavItems,
    currentRoute = currentRoute,
    onItemClick = { route -> /* navigate */ }
)
```

## 🎨 Theme Colors

### Access in Composables
```kotlin
MaterialTheme.colorScheme.primary      // iOS Blue
MaterialTheme.colorScheme.secondary    // Purple
MaterialTheme.colorScheme.tertiary     // Pink
MaterialTheme.colorScheme.background   // Main background
MaterialTheme.colorScheme.surface      // Card background
MaterialTheme.colorScheme.onSurface    // Text on surface
```

### Typography
```kotlin
MaterialTheme.typography.displayLarge   // 34sp, Bold
MaterialTheme.typography.displayMedium  // 28sp, Bold
MaterialTheme.typography.headlineLarge  // 20sp, SemiBold
MaterialTheme.typography.titleLarge     // 17sp, Medium
MaterialTheme.typography.bodyLarge      // 17sp, Normal
MaterialTheme.typography.bodyMedium     // 15sp, Normal
MaterialTheme.typography.labelSmall     // 11sp, Medium
```

## 💾 Database Queries

### QuoteDao Methods
```kotlin
// Get all quotes
quoteDao.getAllQuotes(): Flow<List<Quote>>

// Get favorites
quoteDao.getFavoriteQuotes(): Flow<List<Quote>>

// Get by category
quoteDao.getQuotesByCategory(category): Flow<List<Quote>>

// Get by work
quoteDao.getQuotesByWork(work): Flow<List<Quote>>

// Get single quote
quoteDao.getQuoteById(id): Quote?

// Update favorite
quoteDao.updateFavoriteStatus(id, isFavorite)
```

### Repository Methods
```kotlin
// Load from assets (call once on init)
repository.loadQuotesFromAssets()

// Access flows
repository.allQuotes: Flow<List<Quote>>
repository.favoriteQuotes: Flow<List<Quote>>

// Toggle favorite
repository.toggleFavorite(quote)
```

## 🔔 Notifications

### Schedule Daily Notification
```kotlin
NotificationScheduler.scheduleDailyNotification(context)
```

### Cancel Notification
```kotlin
NotificationScheduler.cancelDailyNotification(context)
```

### Customize Notification Time
Edit `NotificationScheduler.kt`:
```kotlin
set(java.util.Calendar.HOUR_OF_DAY, 9)  // 9 AM
set(java.util.Calendar.MINUTE, 0)
```

## 🧭 Navigation

### Navigate to Screen
```kotlin
navController.navigate(Screen.Home.route)
navController.navigate(Screen.Chat.route)
navController.navigate(Screen.Favorites.route)
navController.navigate(Screen.Works.route)
navController.navigate(Screen.Settings.route)
```

### Navigate with Arguments
```kotlin
navController.navigate(Screen.QuoteDetail.createRoute(quoteId))
navController.navigate(Screen.CategoryQuotes.createRoute(category))
navController.navigate(Screen.WorkQuotes.createRoute(work))
```

### Go Back
```kotlin
navController.popBackStack()
```

## 🎯 ViewModel Usage

### In Composable
```kotlin
@Composable
fun MyScreen(viewModel: QuoteViewModel = viewModel()) {
    val quotes by viewModel.allQuotes.collectAsState()
    val favorites by viewModel.favoriteQuotes.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val isDarkMode by viewModel.isDarkMode.collectAsState()
    
    // Use the data
}
```

### Toggle Functions
```kotlin
viewModel.toggleFavorite(quote)
viewModel.toggleDarkMode()
viewModel.toggleDailyNotification(enabled)
```

## 📊 Data Models

### Quote
```kotlin
data class Quote(
    val id: Int,
    val Number: Int?,
    val Category: String,
    val Quote: String,
    val French: String,
    val Work: String,
    val Year: Int,
    val Tags: String,
    val Context: String,
    val Popularity: String,
    val Reference: String,
    val Bio: String?,
    val isFavorite: Boolean
)
```

### Category
```kotlin
data class Category(
    val name: String,
    val quoteCount: Int,
    val icon: String
)
```

## 🎨 Common Patterns

### Loading State
```kotlin
val isLoading by viewModel.isLoading.collectAsState()

if (isLoading) {
    CircularProgressIndicator()
} else {
    // Content
}
```

### Empty State
```kotlin
if (items.isEmpty()) {
    // Empty state UI
} else {
    // List UI
}
```

### LazyColumn with Cards
```kotlin
LazyColumn(
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
) {
    items(quotes) { quote ->
        MorphismCard(onClick = { /* ... */ }) {
            // Card content
        }
    }
}
```

## 🔧 Customization Quick Tips

### Change Primary Color
`ui/theme/Color.kt`:
```kotlin
val LightPrimary = Color(0xFF007AFF)  // Your color
val DarkPrimary = Color(0xFF0A84FF)   // Your color
```

### Add New Category Icon
`viewmodel/QuoteViewModel.kt`:
```kotlin
private fun getCategoryIcon(category: String): String {
    return when (category.lowercase()) {
        "yourcategory" -> "🎯"
        else -> "💡"
    }
}
```

### Modify Card Corner Radius
```kotlin
MorphismCard(
    cornerRadius = 24.dp  // Change this
)
```

### Change Bottom Nav Height
`ui/components/FloatingBottomBar.kt`:
```kotlin
.height(72.dp)  // Change this
```

## 📱 Testing on Device

### Enable Developer Mode
1. Settings → About Phone
2. Tap "Build Number" 7 times
3. Settings → Developer Options
4. Enable "USB Debugging"

### Install APK
```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

### View Logs
```bash
adb logcat | grep "Voltaire"
```

## 🐛 Common Issues & Fixes

### Build Error: SDK not found
```
Update local.properties with correct SDK path
```

### Gradle Sync Failed
```
File → Invalidate Caches → Invalidate and Restart
```

### App Crashes on Launch
```
Check Logcat for errors
Verify JSON files are in assets/
```

### Dark Mode Not Working
```
Force close app and restart
Clear app data if needed
```

### Notifications Not Showing
```
Grant notification permission in Settings
Enable daily notification in app Settings
```

## 📦 Build Release APK

```bash
# Generate signed APK
Build → Generate Signed Bundle/APK → APK
Select keystore and sign

# Or from command line
./gradlew assembleRelease
```

## 🎉 Quick Start Checklist

- [ ] Open project in Android Studio
- [ ] Wait for Gradle sync
- [ ] Verify SDK path in local.properties
- [ ] Build project (Ctrl+F9)
- [ ] Run on emulator or device (Shift+F10)
- [ ] Test dark/light mode toggle
- [ ] Test navigation between screens
- [ ] Test favorite functionality
- [ ] Test notifications

---

## 📚 File Locations Reference

| Feature | File Location |
|---------|--------------|
| Colors | `ui/theme/Color.kt` |
| Typography | `ui/theme/Type.kt` |
| Theme | `ui/theme/Theme.kt` |
| Home Screen | `ui/screens/HomeScreen.kt` |
| Chat Screen | `ui/screens/ChatScreen.kt` |
| Settings | `ui/screens/SettingsScreen.kt` |
| Database | `data/QuoteDatabase.kt` |
| Repository | `data/QuoteRepository.kt` |
| ViewModel | `viewmodel/QuoteViewModel.kt` |
| Navigation | `navigation/NavGraph.kt` |
| Notifications | `notifications/DailyQuoteWorker.kt` |
| Main Activity | `MainActivity.kt` |
| Datasets | `app/src/main/assets/*.json` |

---

**Keep this card handy for quick reference! 🚀**
