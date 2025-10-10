# 🏗️ Voltaire Insights - App Architecture

## 📐 Architecture Overview

```
┌─────────────────────────────────────────────────────────────┐
│                         UI Layer                             │
│  ┌──────────────────────────────────────────────────────┐   │
│  │              Jetpack Compose Screens                 │   │
│  │  Home │ Chat │ Favorites │ Works │ Settings          │   │
│  └──────────────────────────────────────────────────────┘   │
│                           ↕                                  │
│  ┌──────────────────────────────────────────────────────┐   │
│  │                   ViewModels                         │   │
│  │         QuoteViewModel │ ChatViewModel               │   │
│  └──────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
                            ↕
┌─────────────────────────────────────────────────────────────┐
│                      Domain Layer                            │
│  ┌──────────────────────────────────────────────────────┐   │
│  │                  Repository                          │   │
│  │              QuoteRepository                         │   │
│  └──────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
                            ↕
┌─────────────────────────────────────────────────────────────┐
│                       Data Layer                             │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐      │
│  │ Room Database│  │   DataStore  │  │ JSON Assets  │      │
│  │   QuoteDao   │  │  Preferences │  │  dataset*.json│     │
│  └──────────────┘  └──────────────┘  └──────────────┘      │
└─────────────────────────────────────────────────────────────┘
```

## 🎨 UI Architecture (MVVM Pattern)

### Layer 1: Composable Screens
```
HomeScreen.kt
├── Observes: categories, allQuotes, isLoading
├── Actions: onCategoryClick, onQuoteClick, onAboutClick
└── Components: MorphismCard, LazyVerticalGrid

ChatScreen.kt
├── Observes: messages, isTyping
├── Actions: sendMessage
└── Components: ChatBubble, TypingIndicator

FavoritesScreen.kt
├── Observes: favoriteQuotes
├── Actions: onQuoteClick, toggleFavorite
└── Components: FavoriteQuoteCard

WorksScreen.kt
├── Observes: works, allQuotes
├── Actions: onWorkClick
└── Components: WorkCard

SettingsScreen.kt
├── Observes: isDarkMode, isDailyNotificationEnabled
├── Actions: toggleDarkMode, toggleDailyNotification
└── Components: MorphismCard with Switches
```

### Layer 2: ViewModels
```
QuoteViewModel
├── StateFlows:
│   ├── allQuotes: StateFlow<List<Quote>>
│   ├── favoriteQuotes: StateFlow<List<Quote>>
│   ├── categories: StateFlow<List<Category>>
│   ├── works: StateFlow<List<String>>
│   ├── isDarkMode: StateFlow<Boolean>
│   └── isDailyNotificationEnabled: StateFlow<Boolean>
├── Functions:
│   ├── toggleFavorite(quote)
│   ├── toggleDarkMode()
│   ├── toggleDailyNotification(enabled)
│   ├── getQuotesByCategory(category)
│   └── getQuotesByWork(work)
└── Dependencies:
    ├── QuoteRepository
    └── PreferencesManager

ChatViewModel
├── StateFlows:
│   ├── messages: StateFlow<List<ChatMessage>>
│   └── isTyping: StateFlow<Boolean>
├── Functions:
│   ├── sendMessage(text)
│   ├── generateResponse(text)
│   └── clearChat()
└── Ready for: Pollination AI integration
```

## 💾 Data Flow Architecture

### Read Flow (Database → UI)
```
1. Room Database (SQLite)
   ↓
2. QuoteDao (Flow<List<Quote>>)
   ↓
3. QuoteRepository (exposes Flow)
   ↓
4. QuoteViewModel (StateFlow)
   ↓
5. Composable Screen (collectAsState)
   ↓
6. UI renders automatically on data change
```

### Write Flow (UI → Database)
```
1. User Action (e.g., tap favorite button)
   ↓
2. Composable calls ViewModel function
   ↓
3. ViewModel calls Repository function
   ↓
4. Repository calls DAO function
   ↓
5. DAO updates database
   ↓
6. Flow emits new data
   ↓
7. UI automatically updates
```

## 🗂️ Data Models

### Quote Entity
```kotlin
@Entity(tableName = "quotes")
data class Quote(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val Number: Int? = null,
    val Category: String,
    val Quote: String,
    val French: String,
    val Work: String,
    val Year: Int,
    val Tags: String,
    val Context: String,
    val Popularity: String,
    val Reference: String,
    val Bio: String? = null,
    val isFavorite: Boolean = false
)
```

### Derived Models
```kotlin
// Auto-generated from quotes
Category(name, quoteCount, icon)

// Extracted from quotes
Works: List<String> (unique work titles)
```

## 🧭 Navigation Architecture

### Navigation Graph
```
NavHost
├── Home (start destination)
│   └── Can navigate to:
│       ├── CategoryQuotes/{category}
│       ├── QuoteDetail/{quoteId}
│       ├── About
│       └── Works
├── Chat
│   └── Standalone screen
├── Favorites
│   └── Can navigate to:
│       └── QuoteDetail/{quoteId}
├── Works
│   └── Can navigate to:
│       └── WorkQuotes/{work}
│           └── QuoteDetail/{quoteId}
└── Settings
    └── Can navigate to:
        └── About

Bottom Navigation (5 tabs):
├── Home
├── Chat
├── Favorites
├── Works
└── Settings
```

### Route Definitions
```kotlin
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Chat : Screen("chat")
    object Favorites : Screen("favorites")
    object Works : Screen("works")
    object Settings : Screen("settings")
    object QuoteDetail : Screen("quote_detail/{quoteId}")
    object CategoryQuotes : Screen("category_quotes/{category}")
    object WorkQuotes : Screen("work_quotes/{work}")
    object About : Screen("about")
}
```

## 🎨 Theme Architecture

### Color System
```
Light Theme                 Dark Theme
├── Background: #F5F5F7    ├── Background: #000000
├── Surface: #FFFFFF       ├── Surface: #1C1C1E
├── Primary: #007AFF       ├── Primary: #0A84FF
├── Secondary: #5856D6     ├── Secondary: #5E5CE6
└── Tertiary: #FF2D55      └── Tertiary: #FF375F
```

### Typography Hierarchy
```
Display (Titles)
├── displayLarge: 34sp, Bold
├── displayMedium: 28sp, Bold
└── displaySmall: 22sp, Bold

Headlines (Section Headers)
├── headlineLarge: 20sp, SemiBold
├── headlineMedium: 18sp, SemiBold
└── headlineSmall: 17sp, SemiBold

Body (Content)
├── bodyLarge: 17sp, Normal
├── bodyMedium: 15sp, Normal
└── bodySmall: 13sp, Normal

Labels (Small Text)
├── labelLarge: 15sp, Medium
├── labelMedium: 13sp, Medium
└── labelSmall: 11sp, Medium
```

## 🔔 Notification Architecture

### WorkManager Flow
```
1. User enables notification in Settings
   ↓
2. PreferencesManager updates DataStore
   ↓
3. MainActivity observes preference change
   ↓
4. NotificationScheduler.scheduleDailyNotification()
   ↓
5. WorkManager schedules PeriodicWorkRequest
   ↓
6. DailyQuoteWorker runs at 9 AM daily
   ↓
7. Worker fetches random quote from database
   ↓
8. Creates and shows notification
   ↓
9. User taps notification → Opens MainActivity
```

### Notification Components
```
DailyQuoteWorker
├── Extends: CoroutineWorker
├── Frequency: Every 24 hours
├── Initial Delay: Calculated to 9 AM
├── Constraints: None (works offline)
└── Action: Show notification with random quote

NotificationScheduler
├── scheduleDailyNotification(context)
├── cancelDailyNotification(context)
└── calculateInitialDelay() → 9 AM next day
```

## 📦 Dependency Injection (Manual)

### Application Level
```
MainActivity
├── Creates: QuoteViewModel (by viewModels())
├── Creates: ChatViewModel (by viewModels())
└── Passes to: MainScreen composable

QuoteViewModel (AndroidViewModel)
├── Creates: QuoteDatabase.getDatabase(application)
├── Creates: QuoteRepository(dao, application)
├── Creates: PreferencesManager(application)
└── Lifecycle: Survives configuration changes
```

### Database Singleton
```
QuoteDatabase.getDatabase(context)
├── Pattern: Singleton with synchronized block
├── Instance: Cached in companion object
└── Thread-safe: Yes
```

## 🎯 State Management

### StateFlow Pattern
```
ViewModel                    Composable
├── MutableStateFlow         ├── collectAsState()
│   (private)                │   (observes)
└── StateFlow                └── Recomposes on change
    (public, read-only)
```

### Example Flow
```kotlin
// ViewModel
private val _quotes = MutableStateFlow<List<Quote>>(emptyList())
val quotes: StateFlow<List<Quote>> = _quotes.asStateFlow()

// Composable
val quotes by viewModel.quotes.collectAsState()
```

## 🔄 Data Synchronization

### Initial Load
```
App Launch
├── MainActivity.onCreate()
├── QuoteViewModel.init()
├── Repository.loadQuotesFromAssets()
│   ├── Read dataset1.json
│   ├── Read dataset2.json
│   ├── Merge datasets
│   ├── Clean French text
│   └── Insert into Room database
└── Database emits to UI via Flow
```

### Reactive Updates
```
User Action (e.g., toggle favorite)
├── UI calls viewModel.toggleFavorite(quote)
├── ViewModel calls repository.toggleFavorite(quote)
├── Repository calls dao.updateFavoriteStatus(id, !isFavorite)
├── DAO updates database
├── Database triggers Flow emission
├── Repository Flow emits to ViewModel
├── ViewModel StateFlow updates
└── UI recomposes automatically
```

## 🎨 Component Hierarchy

### Reusable Components
```
MorphismCard
├── Purpose: Main card with mass-morphism effect
├── Features: Shadow, gradient, blur, rounded corners
├── Props: onClick, cornerRadius, elevation
└── Used in: All screens

GlassCard
├── Purpose: Lightweight glass effect
├── Features: Transparency, subtle background
├── Props: onClick, cornerRadius
└── Used in: Overlays, floating elements

FloatingBottomBar
├── Purpose: Animated bottom navigation
├── Features: Spring animations, active state
├── Props: items, currentRoute, onItemClick
└── Used in: MainActivity (global)
```

## 📊 Performance Optimizations

### Lazy Loading
```
LazyColumn / LazyVerticalGrid
├── Only renders visible items
├── Recycles views automatically
└── Smooth scrolling performance
```

### Flow-based Reactivity
```
Room Database Flows
├── Emits only on data change
├── Automatic lifecycle awareness
└── No manual refresh needed
```

### State Preservation
```
Navigation
├── saveState = true
├── restoreState = true
└── Preserves scroll position and state
```

## 🔐 Data Persistence

### Room Database
```
Location: /data/data/com.voltaire.insights/databases/
Tables:
└── quotes
    ├── Stores all quotes
    ├── Tracks favorite status
    └── Indexed by id
```

### DataStore Preferences
```
Location: /data/data/com.voltaire.insights/datastore/
Keys:
├── dark_mode: Boolean
└── daily_notification: Boolean
```

### Assets (Read-only)
```
Location: app/src/main/assets/
Files:
├── dataset1.json (206 KB)
└── dataset2.json (285 KB)
```

---

## 🎯 Key Architectural Decisions

### Why MVVM?
- ✅ Clear separation of concerns
- ✅ Testable business logic
- ✅ Lifecycle-aware components
- ✅ Reactive data flow

### Why Room?
- ✅ Type-safe SQL queries
- ✅ Compile-time verification
- ✅ Flow-based reactivity
- ✅ Offline-first architecture

### Why Jetpack Compose?
- ✅ Declarative UI
- ✅ Less boilerplate
- ✅ Modern animations
- ✅ State-driven rendering

### Why StateFlow?
- ✅ Lifecycle-aware
- ✅ Thread-safe
- ✅ Hot stream (always has value)
- ✅ Perfect for UI state

---

**This architecture ensures scalability, maintainability, and excellent performance! 🚀**
