# 🎓 Campus OLX CLI — 2nd Year College Project

A simple, easy-to-understand terminal application built for college students to buy and sell second-hand items (textbooks, calculators, cycles, kettles) inside campus.

---

## 📁 Simple Project Structure

```
DSA_OOPS_project/
├── compile.sh          # One-click compile script
├── run.sh              # One-click run script
├── lib/
│   └── gson-2.10.1.jar # Simple library to save/read JSON
├── data/
│   └── items.json      # File where all item details are saved
└── src/com/campusolx/
    ├── Main.java         # Main menu (Buyer & Seller loops)
    ├── User.java         # Base User class (Encapsulation)
    ├── Buyer.java        # Inherits from User (Inheritance)
    ├── Seller.java       # Inherits from User (Inheritance)
    ├── Item.java         # Item details (price, description, status)
    ├── QuickSort.java    # Sorts items by price from scratch
    ├── BinarySearch.java # Searches items by ID and budget from scratch
    └── DataManager.java  # Simple 20-line JSON save and load
```

---

## 🚀 How to Run

1. **Compile:**
   ```bash
   ./compile.sh
   ```

2. **Run:**
   ```bash
   ./run.sh
   ```

---

## 💡 Viva Questions & Explanations (How to Answer Your Teacher)

### 1. Object-Oriented Programming (OOPs)

* **Encapsulation:**
  * In `User.java` and `Item.java`, all data variables (`name`, `price`, `phone`) are made `private`.
  * They can only be accessed or modified using `getName()`, `getPrice()`, `setPrice()`, protecting data from invalid values.

* **Inheritance:**
  * `User` is the parent (base) class containing common fields (`name`, `phone`, `hostel`).
  * `Buyer` and `Seller` both **extend** `User`. They inherit everything from `User` using `super()`, avoiding code duplication.

* **Polymorphism (Method Overriding):**
  * `User` has a method `public void displayInfo()`.
  * `Buyer` overrides it to display the student's **Max Budget**.
  * `Seller` overrides it to display the **Number of Items Listed**.
  * At runtime, calling `.displayInfo()` executes the specific version depending on whether the object is a `Buyer` or a `Seller`.

---

### 2. Data Structures & Algorithms (DSA)

* **Algorithm 1: QuickSort (`QuickSort.java`)**
  * **What it does:** Sorts available campus items by **Price** from lowest to highest.
  * **How it works:**
    1. Uses **Divide and Conquer**.
    2. Takes the last element as the **pivot**.
    3. The `partition()` method rearranges the list so smaller prices go to the left and higher prices go to the right.
    4. Recursively sorts the left and right sides.
  * **Time Complexity:** Average $O(N \log N)$.

* **Algorithm 2: Binary Search (`BinarySearch.java`)**
  * **What it does:** 
    1. Quickly finds an item by its unique `Item ID` in $O(\log N)$.
    2. **Budget Search:** Finds all items within a student's maximum budget in $O(\log N)$ by locating the upper boundary index in the sorted list.
  * **How it works:**
    - Checks the middle element (`mid = (low + high) / 2`).
    - If target is smaller, searches the left half (`high = mid - 1`).
    - If target is larger, searches the right half (`low = mid + 1`).
  * **Time Complexity:** $O(\log N)$.

---

### 3. File Storage (JSON)
* `DataManager.java` uses `FileReader` and `FileWriter` with Google's `Gson` library to save and load all items into `data/items.json`.
* Every new item added is automatically remembered even if you restart the program.
