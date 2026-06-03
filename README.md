# 📱 Shop Management App

An intuitive, lightweight, and robust **Android Application** designed for small to medium-sized business owners to manage their daily shop operations effortlessly. Built using native Java and SQLite, this app tracks inventory, calculates real-time sales analytics, logs customer interactions, and provides detailed financial reports.

---

## ✨ Features

### 🔐 Secure Session Management
* **Persistent Login:** Keeps shop owners logged in securely using local state tracking.
* **Master Logout Fix:** Clears all activity stacks and wipes cached preferences globally upon logging out, preventing unauthorized backward navigation.

### 📦 Inventory Management
* Track total stock, add new products, update prices, and monitor item quantities in real-time.

### 👥 Customer Directory
* Maintain a dedicated directory of customer profiles linked dynamically to their specific purchases.

### 📈 Detailed Sales & Analytics Reports
* **Smart Receipts:** View clean, invoice-style broken-down summaries showing individual Product Prices, Quantities, and Total Bills.
* **Time-Stamps:** Every sale is automatically logged with the exact date and formatted 12-hour time (e.g., `2026-05-21 | 01:15 AM`).
* **Smart Sorting:** Automatically arranges transactions chronologically with the **newest sales appearing at the very top**.

---

## 🛠️ Tech Stack & Architecture

* **Language:** Java (Native Android)
* **Database:** SQLite (Local relational storage with optimized relational `JOIN` queries)
* **UI/UX Layouts:** Custom XML (utilizing nested `LinearLayout`, `GridLayout`, and responsive `SimpleCursorAdapter` layout views)
* **Version Control:** Git & GitHub

---

## 📂 Project Structure

```text
app/src/main/
│
├── java/com/example/shopmanagementapp/
│   ├── MainActivity.java        # Handles application entry & authentication
│   ├── DashboardActivity.java   # Central navigation hub & session controls
│   ├── InventoryActivity.java   # Business logic for stock management
│   ├── SalesActivity.java       # Process transactions & timestamps
│   ├── CustomerActivity.java    # Profiles database management
│   ├── ReportsActivity.java     # Relational SQLite JOIN analytics views
│   └── DatabaseHelper.java      # Advanced SQLite configuration & table queries
│
└── res/
    ├── layout/                  # Responsive XML views (activity_dashboard, activity_reports, etc.)
    └── mipmap-anydpi-v26/       # Premium custom adaptive 3D launcher icons

```

## 🚀 How To Run & Install

1. **Clone the repository:**

```bash
git clone [https://github.com/ichhasaniqbal/Shop-Management-App.git](https://github.com/ichhasaniqbal/Shop-Management-App.git)

```

2. Open the project folder inside **Android Studio**.
3. Make sure you have **Git** configured inside your IDE settings (File -> Settings -> Version Control -> Git).
4. Let **Gradle** sync automatically (`Gradle 9.4.1` or above recommended).
5. Connect your Android device via USB (with USB Debugging enabled) or start an Emulator, then click **Run (Shift + F10)**.

---

## 📝 Future Roadmap

* [ ] Add automated low-stock alert notifications for inventory items.
* [ ] Integrate digital wallet payment gateways (Easypaisa / JazzCash / Visa).
* [ ] Implement secure cloud backup sync using a Firebase backend.

---

Developed with 💪 by [Hassan Iqbal](https://www.google.com/search?q=https://github.com/ichhasaniqbal)

```

```
