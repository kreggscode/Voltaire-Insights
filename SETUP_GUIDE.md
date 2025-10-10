# 🚀 Voltaire Insights - Setup Guide

## Quick Start Guide

### Step 1: Prerequisites
Before you begin, ensure you have:
- ✅ **Android Studio** (Hedgehog 2023.1.1 or later)
- ✅ **JDK 17** (bundled with Android Studio)
- ✅ **Android SDK 34** (install via SDK Manager)
- ✅ **Minimum 8GB RAM** (16GB recommended)

### Step 2: Open Project in Android Studio

1. **Launch Android Studio**
2. Click **"Open"** or **"Open an Existing Project"**
3. Navigate to: `c:/Users/kreg9/Downloads/kreggscode/windsurf/voltaire`
4. Click **"OK"**

### Step 3: Configure SDK Path

The `local.properties` file is already created. Verify your Android SDK path:

1. Open `local.properties`
2. Update `sdk.dir` if needed:
   ```properties
   sdk.dir=C\:\\Users\\YOUR_USERNAME\\AppData\\Local\\Android\\Sdk
   ```

### Step 4: Sync Gradle

1. Android Studio will prompt to sync Gradle
2. Click **"Sync Now"** in the notification bar
3. Wait for dependencies to download (first time may take 5-10 minutes)

### Step 5: Build the Project

1. Go to **Build → Make Project** (or press `Ctrl+F9`)
2. Wait for the build to complete
3. Check the **Build** tab for any errors

### Step 6: Run the App

**Option A: Using an Emulator**
1. Click **Device Manager** (phone icon in toolbar)
2. Create a new virtual device:
   - Device: Pixel 6 or newer
   - System Image: Android 14 (API 34) or Android 13 (API 33)
   - Download system image if needed
3. Click **Run** (green play button) or press `Shift+F10`

**Option B: Using a Physical Device**
1. Enable **Developer Options** on your Android device:
   - Go to Settings → About Phone
   - Tap "Build Number" 7 times
2. Enable **USB Debugging**:
   - Settings → Developer Options → USB Debugging
3. Connect device via USB
4. Click **Run** and select your device

## 📱 App Features Overview

### Home Screen
- Browse quotes by category
- Beautiful 2-column grid layout
- Quick access to "About Voltaire" and "Works"

### Chat Screen
- Interactive chat interface
- Placeholder for Pollination AI integration
- Smooth message animations

### Favorites Screen
- Save your favorite quotes
- Swipe to remove
- Persistent storage with Room database

### Works Screen
- Browse all of Voltaire's works
- See quote count per work
- Tap to view quotes from specific works

### Settings Screen
- Toggle Dark/Light mode
- Enable/disable daily notifications
- View app information

## 🎨 Customization Tips

### Change App Colors
Edit `app/src/main/java/com/voltaire/insights/ui/theme/Color.kt`:
```kotlin
val LightPrimary = Color(0xFF007AFF) // Change to your color
```

### Modify Notification Time
Edit `app/src/main/java/com/voltaire/insights/notifications/NotificationScheduler.kt`:
```kotlin
set(java.util.Calendar.HOUR_OF_DAY, 9) // Change hour (0-23)
```

### Add More Categories
The app auto-generates categories from the JSON data. Add category icons in:
`QuoteViewModel.kt` → `getCategoryIcon()` function

## 🔧 Troubleshooting

### Build Errors

**Error: "SDK location not found"**
- Solution: Update `local.properties` with correct SDK path

**Error: "Gradle sync failed"**
- Solution: 
  1. File → Invalidate Caches → Invalidate and Restart
  2. Delete `.gradle` folder and sync again

**Error: "Kotlin version mismatch"**
- Solution: Update Kotlin plugin in Android Studio

### Runtime Errors

**App crashes on launch**
- Check Logcat for error messages
- Ensure JSON files are in `app/src/main/assets/`

**Notifications not working**
- Grant notification permission in device settings
- Check if daily notification is enabled in Settings

**Dark mode not switching**
- Force close and restart the app
- Clear app data if issue persists

## 📊 Dataset Information

The app uses two JSON datasets:
- **dataset1.json**: 500+ quotes with basic metadata
- **dataset2.json**: Extended quotes with bio notes

Both files are automatically merged on first launch.

### JSON Structure
```json
{
  "Number": 1,
  "Category": "Wisdom",
  "Quote": "Judge a man by his questions...",
  "French": "On doit juger un homme...",
  "Work": "Le Dîner du Comte de Boulainvilliers",
  "Year": 1767,
  "Tags": "wisdom,critical thinking",
  "Context": "Emphasizes curiosity...",
  "Popularity": "High",
  "Reference": "OCDV 45.234",
  "Bio": "Optional bio note"
}
```

## 🤖 Integrating Pollination AI

To connect the chat feature to Pollination AI:

1. **Get API Key**
   - Sign up at Pollination AI
   - Copy your API key

2. **Update ChatViewModel.kt**
   ```kotlin
   private suspend fun callPollinationAI(message: String): String {
       // Add your API integration here
       val response = yourApiClient.chat(message)
       return response.text
   }
   ```

3. **Add API Key Storage**
   - Store in `local.properties` (not committed to git)
   - Or use Android Keystore for production

## 🎯 Next Steps

### Recommended Enhancements
1. **Add Search**: Implement quote search functionality
2. **Share Images**: Generate beautiful quote images for sharing
3. **Widget**: Create a home screen widget for daily quotes
4. **Animations**: Add more micro-interactions
5. **Themes**: Create additional color themes

### Performance Optimization
- Enable R8/ProGuard for release builds
- Optimize images and assets
- Implement pagination for large quote lists
- Add caching for better performance

## 📝 Important Notes

### Before Publishing
- [ ] Update `applicationId` in `build.gradle.kts`
- [ ] Create proper launcher icons (use Image Asset Studio)
- [ ] Add privacy policy
- [ ] Test on multiple devices and Android versions
- [ ] Enable ProGuard/R8 for release builds
- [ ] Sign the APK with a release keystore

### Testing Checklist
- [ ] Test on Android 7.0 (API 24) minimum
- [ ] Test dark and light modes
- [ ] Test notification permissions
- [ ] Test offline functionality
- [ ] Test database migrations
- [ ] Test on different screen sizes

## 🆘 Getting Help

If you encounter issues:
1. Check the **Logcat** in Android Studio
2. Review the **Build** output for errors
3. Consult the **README.md** for project overview
4. Check Android Studio's **Event Log** for warnings

## 🎉 You're All Set!

Your Voltaire Insights app is ready to run. Enjoy exploring the wisdom of Voltaire with a beautiful, modern interface!

---

**Happy Coding! 💻✨**
