# 🏕️ Campsite Commander

An outdoor-inspired, dark-themed Android application designed to help campers organize, categorize, and audit their gear and food supplies before heading out into the wild. 

Pack smart, stay safe, and establish a functional base camp with confidence!

---

## 🌲 Features & Functionality

Campsite Commander is built around a seamless, single-activity design that manages three distinct contextual states using layout visibility transitions:

1. **Firelight Splash Screen:** A welcoming 3-second animated entry display featuring a campfire emblem and custom branding while the base environment initializes.
2. **Base Camp (Main Dashboard):** Your control hub. It displays a live running total of all items packed and features a context-aware **Commander Insight Panel** that evaluates your inventory safety profile in real time.
3. **Tactical Checklist (Detailed View):** A beautifully styled list utilizing modern outdoor aesthetics (Midnight Forest & Flame Amber tones) to break down every item by name, quantity category, and specific custom notes.

---

## 🛠️ Technical Implementation (Beginner-Friendly Architecture)

This application was engineered specifically to demonstrate foundational software development and mobile programming principles without complex framework overhead:

* **Data Management (Parallel Arrays):** Uses four synchronized `ArrayList` structures (`itemNames`, `itemCategories`, `itemQuantities`, and `itemNotes`) working together in parallel to form a lightweight relational data table.
* **Algorithmic Loops:** 
  * Computes cumulative totals by dynamically traversing the quantity arrays with an explicit `for` loop.
  * Dynamically populates custom interactive rows on the user interface by looping over stored indices.
* **Robust Error Handling & Input Validation:**
  * Validates text input fields to reject blank entries constructively via explicit `Toast` feedback notifications.
  * Evaluates and normalizes numeric inputs (e.g., automatically assigning a fallback value of `1` if quantity fields are omitted, or rejecting negative numbers/zeros).
* **Creative Insight Engine:** Runs condition checking across array structures to verify if critical safety categories—such as **First Aid**—are missing, warning the user directly on the main dashboard.

---

## 🎨 Visual Design Profile

The app implements a custom modern dark mode layout inspired by deep-forest environments:
* **Base Canvas Background:** Midnight Forest (`#0D1710`)
* **Interactive Container Elements:** Slate Moss Green (`#14261A` / `#18221b`)
* **Primary Accents & Headings:** Flame Amber Gold (`#FFB300`)

---

## 🚀 Getting Started & Setup

### Prerequisites
* Android Studio (Ladybug or newer recommended)
* Android SDK 35 (Compile and Target SDK setup)
* Kotlin-configured Empty Activity template

### Installation
1. Clone or download the source files into your local directory.
2. Place `activity_main.xml` inside your project's layout directory:  
   `app/src/main/res/layout/`
3. Place `MainActivity.kt` inside your primary source package directory:  
   `app/src/main/java/com/example/campsidecomander/`
4. **Crucial Step:** Ensure that the very first line of `MainActivity.kt` (`package ...`) matches your exact project package path.
5. Sync your project with Gradle files and deploy to an Android Virtual Device (AVD) or a physical hardware device.

---

## 📝 Assignment Requirements Checked
* [x] Three screens (Splash, Main, Detailed View) managed via visibility states.
* [x] Data tracking utilizes **Parallel Arrays**.
* [x] Accumulation and UI rendering calculated natively via **Loops**.
* [x] Custom screen navigation with standard interactive buttons.
* [x] Full error handling and constructive user validation.
*
