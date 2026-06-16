
# 🎮 Tic Tac Toe Pro – AI Powered Android Game

## 📌 Overview

Tic Tac Toe Pro is a modern Android game developed using **Java and Android Studio**, featuring both **Player vs Player** and **Player vs AI** modes with multiple difficulty levels. The project is designed with a futuristic **Neon Cyberpunk UI theme**, smooth animations, immersive **sound effects managed through a centralized SoundManager**, and a fully interactive gameplay experience.

The game also includes **score tracking, match statistics, win/loss history, streak system**, and persistent storage using SharedPreferences, making it more than just a simple Tic Tac Toe game.

---

# 🚀 Project Highlights

✅ Player vs Player Mode
✅ Player vs AI Mode (Easy, Medium, Hard)
✅ Neon Cyberpunk UI Theme
✅ Smooth Animations & Transitions
✅ Centralized SoundManager System
✅ Click, Win, Draw & Button Sound Effects
✅ Score Tracking System
✅ Match Statistics Dashboard
✅ Streak System (Current & Best)
✅ Fully Offline Android Game
✅ Lightweight & Fast Performance

---

# ✨ Features

## 👥 Game Modes

### 🎮 Player vs Player

* Two players play on same device
* Turn-based gameplay
* Real-time win detection

### 🤖 Player vs AI

* AI opponent with 3 difficulty levels:

  * Easy → Random moves
  * Medium → Defensive + basic strategy
  * Hard → Optimal + blocking + winning logic

---

## 🎨 UI / UX Features

* Neon Cyberpunk Theme 🌌
* Dark futuristic gaming UI
* Glowing X and O animations
* Smooth button transitions
* Card-based main menu
* Responsive layout for all screen sizes

---

## 🔊 Sound System (SoundManager)

* Centralized sound handling using `SoundManager.java`
* Click sound for moves
* Button sound for navigation
* Win sound for victory
* Draw sound for match tie
* Sound ON/OFF toggle from settings & main menu
* Persistent sound state using SharedPreferences

---

## 📊 Statistics System

* Total games played
* Wins, losses, draws
* Win rate calculation
* Current winning streak
* Best streak tracking
* AI difficulty-wise stats:

  * Easy stats
  * Medium stats
  * Hard stats

---

## ⚙️ Settings System

* Sound ON/OFF toggle
* Theme consistency
* Help section
* Game preferences storage
* Instant sync between:

  * Settings Page
  * Main Menu Sound Button
  * Game screens

---

# 🧠 AI Logic

* Random move selection (Easy)
* Block opponent winning moves (Medium)
* Strategic move selection (Hard)

  * Center priority
  * Corner priority
  * Winning move detection
  * Blocking logic

---

# 🛠 Technologies Used

## 📱 Frontend (Android)

* Java
* XML Layouts
* Material Design Components
* ConstraintLayout
* CardView

## 🎮 Game Engine

* Custom game logic
* Handler-based animations
* Win pattern detection algorithm

## 🔊 Audio System

* SoundPool API
* AudioAttributes
* Central SoundManager architecture

## 💾 Storage

* SharedPreferences (local persistence)

---

# 📂 Project Structure

```text
TicTacToePro
│
├── java/com.example.tictactoepro
│   ├── MainMenuActivity
│   ├── GameActivity
│   ├── AIGameActivity
│   ├── DifficultyActivity
│   ├── SettingsActivity
│   ├── StatisticsActivity
│   ├── SoundManager
│
├── res/layout
│   ├── activity_main_menu.xml
│   ├── activity_game.xml
│   ├── activity_ai_game.xml
│   ├── activity_settings.xml
│
├── res/drawable
│   ├── neon UI assets
│
├── res/raw
│   ├── click.mp3
│   ├── win.mp3
│   ├── draw.mp3
│   ├── button.mp3
```

---

# 📸 Screenshots

Add screenshots of:

* Main Menu (Neon UI)
* Game Board
* AI Mode Selection
* Settings Page (Sound Toggle)
* Statistics Page
* Win/Loss Screen

---

# ⚙️ Setup Instructions (Android Studio)

## 📥 Step 1: Install Required Tools

* Android Studio (Latest Version)
* JDK 17
* Android SDK

---

## 📱 Step 2: Emulator Setup

### Recommended Device:

* Pixel 4 / Pixel 5
* API Level: 24+ (Android 7.0+)
* RAM: 2GB+ emulator recommended

---

## 📲 Step 3: Create Emulator

1. Open Android Studio
2. Go to:

   ```
   Tools → Device Manager
   ```
3. Click:

   ```
   Create Device
   ```
4. Select:

   ```
   Pixel 4 (Recommended)
   ```
5. Choose System Image:

   ```
   Android 12 or 13 (API 31+ recommended)
   ```
6. Download if not available
7. Click Finish

---

## 🚀 Step 4: Run Project

```bash
Run ▶ App
```

---

# 🌐 SDK Requirements

* Compile SDK: 34
* Min SDK: 21
* Target SDK: 34

---

# 🔊 Sound System Setup

* All sounds handled via `SoundManager.java`
* Uses `SoundPool` for low latency
* Global sound state managed using SharedPreferences
* Automatically syncs across all screens

---

# 🔮 Future Enhancements

🚀 Online multiplayer mode
🚀 Firebase leaderboard
🚀 AI difficulty upgrade (Minimax full version)
🚀 Haptic vibration support
🚀 Custom themes (Neon, Light, Retro)
🚀 Bluetooth multiplayer

---

# 👨‍💻 Developed By

**Prasanthi**

Android Developer
Specialized in Java, Android UI/UX, and Game Development

---

# 📚 Project Purpose

This project is developed for learning and demonstration purposes to showcase:

* Android game development skills
* AI logic implementation
* UI/UX design (Cyberpunk theme)
* Sound system architecture
* State management using SharedPreferences
* Real-world mobile app structure


