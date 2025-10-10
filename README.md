# 📱 Voltaire Insights

A modern Android app showcasing the wisdom of Voltaire with iOS-inspired design, mass-morphism aesthetics, and beautiful animations.

## ✨ Features

### 🏠 5 Main Tabs (Floating Bottom Navigation)
1. **Home** - Browse quotes by category with stunning card layouts
2. **Chat with Voltaire** - Interactive AI chat interface (Pollination AI ready)
3. **Favorites** - Save and manage your favorite quotes
4. **Works** - Explore Voltaire's literary works
5. **Settings** - Dark/Light mode, notifications, and preferences

### 🎨 Design Highlights
- **iOS-Inspired UI** - Clean, modern interface with Apple-like aesthetics
- **Mass-morphism/Glassmorphism** - Soft blur effects, gradients, and floating cards
- **Floating Bottom Navigation** - Animated tab bar with smooth transitions
- **Dark & Light Modes** - Dynamic theme switching with beautiful color palettes
- **Smooth Animations** - Spring-based animations and transitions

### 📚 Data Features
- Merged dataset from `dataset1.json` and `dataset2.json`
- 500+ quotes from Voltaire's works
- Categories: Wisdom, Freedom, Justice, Religion, Philosophy, and more
- Full quote details: French translation, work, year, context, tags, bio notes

### 🔔 Smart Notifications
- Daily quote notifications (9 AM)
- Toggle on/off from Settings
- Beautiful notification cards

### 💾 Local Storage
- Room database for quotes and favorites
- DataStore for user preferences
- Offline-first architecture

## 🛠️ Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM with ViewModels
- **Navigation**: Compose Navigation
- **Database**: Room
- **Preferences**: DataStore
- **JSON Parsing**: kotlinx.serialization
- **Background Tasks**: WorkManager
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 34 (Android 14)

## 📦 Project Structure

```
app/src/main/java/com/voltaire/insights/
├── data/
│   ├── QuoteDao.kt              # Room DAO
│   ├── QuoteDatabase.kt         # Room database
│   ├── QuoteRepository.kt       # Data repository
│   └── PreferencesManager.kt    # DataStore preferences
├── model/
│   ├── Quote.kt                 # Quote data model
│   └── Category.kt              # Category model
├── navigation/
│   └── NavGraph.kt              # Navigation graph
├── notifications/
│   ├── DailyQuoteWorker.kt      # WorkManager worker
│   └── NotificationScheduler.kt # Notification scheduler
├── ui/
│   ├── components/
│   │   ├── MorphismCard.kt      # Reusable morphism cards
│   │   └── FloatingBottomBar.kt # Bottom navigation
│   ├── screens/
│   │   ├── HomeScreen.kt
│   │   ├── ChatScreen.kt
│   │   ├── FavoritesScreen.kt
│   │   ├── WorksScreen.kt
│   │   ├── SettingsScreen.kt
│   │   ├── QuoteDetailScreen.kt
│   │   ├── CategoryQuotesScreen.kt
│   │   ├── WorkQuotesScreen.kt
│   │   └── AboutScreen.kt
│   └── theme/
│       ├── Color.kt             # iOS-inspired colors
│       ├── Theme.kt             # Theme configuration
│       └── Type.kt              # Typography
├── viewmodel/
│   ├── QuoteViewModel.kt        # Main ViewModel
│   └── ChatViewModel.kt         # Chat ViewModel
└── MainActivity.kt              # Main activity
```

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog or later
- JDK 17
- Android SDK 34

### Installation

1. **Clone the repository**
   ```bash
   cd voltaire
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an Existing Project"
   - Navigate to the `voltaire` folder

3. **Sync Gradle**
   - Android Studio will automatically sync Gradle
   - Wait for dependencies to download

4. **Run the app**
   - Connect an Android device or start an emulator
   - Click the "Run" button or press Shift+F10

### Dataset Files
The app uses two JSON datasets located in `app/src/main/assets/`:
- `dataset1.json` - Primary quotes dataset
- `dataset2.json` - Extended quotes with bio notes

These files are automatically loaded and merged on first launch.

## 🎨 Design System

### Colors (iOS-Inspired)
- **Light Mode**: Clean whites, soft grays, vibrant blues
- **Dark Mode**: Deep blacks, subtle grays, bright accents
- **Accent Colors**: Blue (#007AFF), Purple (#5856D6), Pink (#FF2D55)

### Typography
iOS-style typography with:
- San Francisco-inspired font metrics
- Proper letter spacing and line heights
- Hierarchical text styles

### Components
- **MorphismCard**: Glassmorphism effect with blur and shadows
- **GlassCard**: Lightweight glass effect
- **FloatingBottomBar**: Animated navigation with spring physics

## 🔮 Future Enhancements

### Chat Integration
The chat feature is ready for **Pollination AI** integration:
- Update `ChatViewModel.kt` to connect to your AI backend
- Replace the placeholder `generateResponse()` function
- Add API key management in Settings

### Suggested Improvements
- [ ] Add search functionality
- [ ] Implement quote sharing with custom images
- [ ] Add quote of the day widget
- [ ] Support for multiple languages
- [ ] Export favorites to PDF
- [ ] Social sharing features
- [ ] Quote collections/playlists

## 📱 Screenshots

The app features:
- Beautiful category cards with emojis
- Smooth transitions between screens
- Neumorphic chat bubbles
- Elegant quote detail views
- Polished settings interface

## 🤝 Contributing

Feel free to:
- Report bugs
- Suggest new features
- Submit pull requests
- Improve documentation

## 📄 License

This project is open source and available for educational purposes.

## 🙏 Acknowledgments

- Quotes sourced from Voltaire's extensive literary works
- Design inspired by iOS and modern material design principles
- Built with love using Jetpack Compose

---

**Made with ❤️ for lovers of philosophy and beautiful design**

*"Judge a man by his questions rather than by his answers." - Voltaire*
