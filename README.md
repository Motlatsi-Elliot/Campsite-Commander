# 🏕️ Campsite Commander

An outdoor-inspired, dark-themed Android application designed to help campers organize, categorize, and audit their gear and food supplies before heading out into the wild. 

Pack smart, stay safe, and establish a functional base camp with confidence!

---

# Screens

##  Splash Screen

<img width="1420" height="900" alt="LabVM2135402 - labvmdnsg4iqqj6ndgazc southafricanorth cloudapp azure com_3389 - Remote Desktop Connection 2026_06_10 14_31_01" src="https://github.com/user-attachments/assets/bf8633f6-8d91-4899-8d97-382a2735cb0e" />

##  The Main Screen

<img width="1420" height="900" alt="LabVM2135402 - labvmdnsg4iqqj6ndgazc southafricanorth cloudapp azure com_3389 - Remote Desktop Connection 2026_06_10 14_33_31" src="https://github.com/user-attachments/assets/451af379-e825-44f9-809a-d5033034c2aa" />

 ### Data input box

 <img width="1420" height="900" alt="LabVM2135402 - labvmdnsg4iqqj6ndgazc southafricanorth cloudapp azure com_3389 - Remote Desktop Connection 2026_06_10 14_45_26" src="https://github.com/user-attachments/assets/2a60de69-49ed-407f-8699-d8d22c992eec" />

#  Detaied View Screen

<img width="1420" height="900" alt="LabVM2135402 - labvmdnsg4iqqj6ndgazc southafricanorth cloudapp azure com_3389 - Remote Desktop Connection 2026_06_10 14_49_46" src="https://github.com/user-attachments/assets/6d20a0df-fb0f-4419-b308-f73a68d4bf44" />


## 🌲 Features & Functionality

Campsite Commander is built around a seamless, single-activity design that manages three distinct contextual states using layout visibility transitions:

1. ** Campingsite Splash Screen:** A welcoming 3-second animated entry display featuring a campfire emblem.
2. **Base Camp (Main Dashboard):** Your control hub. It displays a live running total of all items packed and features a context-aware **Commander Insight Panel** that evaluates your inventory safety profile in real time.
3. **Tactical Checklist (Detailed View):** A beautifully styled list utilizing modern outdoor aesthetics (Midnight Forest & Flame Amber tones) to break down every item by name, quantity category, and specific custom notes.

---

## 🛠️ Technical Implementation (Beginner-Friendly Architecture)

This application was engineered specifically to demonstrate foundational software development and mobile programming principles without complex framework overhead:

* **Data Management (Parallel Arrays):** Uses four synchronized `ArrayList` structures (`itemNames`, `itemCategories`, `itemQuantities`, and `itemNotes`) working together in parallel to form a lightweight relational data table.
* **Algorithmic Loops:** 
  * Computes cumulative totals by dynamically traversing the quantity arrays with an explicit `for` loop.
  * Dynamically populates custom interactive rows on the user interface by looping over stored indices.
* **Error Handling & Input Validation:**
  * Validates text input fields to reject blank entries constructively via explicit `Toast` feedback notifications.
  * Evaluates and normalizes numeric inputs (e.g., automatically assigning a fallback value of `1` if quantity fields are omitted, or rejecting negative numbers/zeros).
* **Creative Insight Engine:** Runs condition checking across array structures to verify if critical safety categories—such as **First Aid**—are missing, warning the user directly on the main dashboard.

---

## 🎨 Visual Design Profile

The app implements a custom modern dark mode layout inspired by deep-forest environments:
* **Base Canvas Background:** Midnight Forest (`#121212`)
* **Interactive Container Elements:** Slate Moss Green (`#1E1E1E`)
* **Primary Accents & Headings:** Flame Amber Gold (`#FFB300`)
