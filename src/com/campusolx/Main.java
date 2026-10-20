package com.campusolx;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main application class providing a student-friendly CLI menu for Campus OLX.
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static ArrayList<Item> items = new ArrayList<>();

    public static void main(String[] args) {
        // Load existing items from JSON file
        items = DataManager.loadItems();

        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                🎓 CAMPUS OLX (COLLEGE CLI)               ║");
        System.out.println("║         Simple DSA (QuickSort & Binary Search) + OOPs    ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");

        while (true) {
            System.out.println("\n--- SELECT YOUR ROLE ---");
            System.out.println("1. I am a Buyer (Search, Browse & Buy Items)");
            System.out.println("2. I am a Seller (List & Sell Items)");
            System.out.println("3. Exit");
            System.out.print("Enter your choice (1-3): ");

            int choice = readInt();

            switch (choice) {
                case 1 -> runBuyerMenu();
                case 2 -> runSellerMenu();
                case 3 -> {
                    System.out.println("\nThank you for using Campus OLX! Goodbye 👋");
                    System.exit(0);
                }
                default -> System.out.println("⚠️  Invalid choice. Please enter 1, 2, or 3.");
            }
        }
    }

    // =========================================================================
    // BUYER WORKFLOW
    // =========================================================================
    private static void runBuyerMenu() {
        System.out.println("\n--- BUYER REGISTRATION ---");
        System.out.print("Enter your Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter your Phone Number: ");
        String phone = scanner.nextLine().trim();
        System.out.print("Enter your Hostel Block & Room: ");
        String hostel = scanner.nextLine().trim();
        System.out.print("Enter your Spending Budget (₹): ");
        double budget = readDouble();

        // Create Buyer object (Inheritance from User)
        Buyer buyer = new Buyer(name, phone, hostel, budget);
        System.out.println("✅ Welcome, " + buyer.getName() + "!");

        boolean inBuyerMenu = true;
        while (inBuyerMenu) {
            System.out.println("\n--- BUYER MENU (" + buyer.getName() + ") ---");
            System.out.println("1. View All Available Items");
            System.out.println("2. Sort Items by Price (DSA: QuickSort)");
            System.out.println("3. Find Items within My Budget (DSA: Binary Search)");
            System.out.println("4. View Item Details & Contact Seller");
            System.out.println("5. Buy an Item (Mark as Sold)");
            System.out.println("6. View My Profile (OOP: Polymorphism)");
            System.out.println("7. Back to Main Menu");
            System.out.print("Enter choice (1-7): ");

            int option = readInt();
            switch (option) {
                case 1 -> displayAvailableItems();
                case 2 -> sortAndDisplayItems();
                case 3 -> searchWithinBudget(buyer.getBudget());
                case 4 -> viewItemDetails();
                case 5 -> buyItem();
                case 6 -> buyer.displayInfo(); // Demonstrates Polymorphism
                case 7 -> inBuyerMenu = false;
                default -> System.out.println("⚠️  Invalid choice, try again.");
            }
        }
    }

    // =========================================================================
    // SELLER WORKFLOW
    // =========================================================================
    private static void runSellerMenu() {
        System.out.println("\n--- SELLER REGISTRATION ---");
        System.out.print("Enter your Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter your Phone Number: ");
        String phone = scanner.nextLine().trim();
        System.out.print("Enter your Hostel Block & Room: ");
        String hostel = scanner.nextLine().trim();

        // Create Seller object (Inheritance from User)
        Seller seller = new Seller(name, phone, hostel);
        System.out.println("✅ Welcome, " + seller.getName() + "!");

        boolean inSellerMenu = true;
        while (inSellerMenu) {
            System.out.println("\n--- SELLER MENU (" + seller.getName() + ") ---");
            System.out.println("1. Post a New Item for Sale");
            System.out.println("2. View My Listed Items");
            System.out.println("3. Mark an Item as Sold");
            System.out.println("4. View My Profile (OOP: Polymorphism)");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter choice (1-5): ");

            int option = readInt();
            switch (option) {
                case 1 -> postItem(seller);
                case 2 -> viewSellerItems(seller.getName());
                case 3 -> markItemAsSold(seller.getName());
                case 4 -> seller.displayInfo(); // Demonstrates Polymorphism
                case 5 -> inSellerMenu = false;
                default -> System.out.println("⚠️  Invalid choice, try again.");
            }
        }
    }

    // =========================================================================
    // CORE ACTIONS & DSA LOGIC
    // =========================================================================

    // Display all items currently marked as available
    private static void displayAvailableItems() {
        System.out.println("\n-------------------------------------------------------------------------");
        System.out.println(" 📋 ALL AVAILABLE ITEMS ON CAMPUS");
        System.out.println("-------------------------------------------------------------------------");

        int count = 0;
        for (Item item : items) {
            if (!item.isSold()) {
                item.printSummary();
                count++;
            }
        }

        if (count == 0) {
            System.out.println("  (No available items found)");
        }
        System.out.println("-------------------------------------------------------------------------");
    }

    // DSA 1: QuickSort (Sort items by price)
    private static void sortAndDisplayItems() {
        if (items.isEmpty()) {
            System.out.println("No items to sort.");
            return;
        }

        // Create a copy of only available items to sort
        ArrayList<Item> availableItems = new ArrayList<>();
        for (Item item : items) {
            if (!item.isSold()) {
                availableItems.add(item);
            }
        }

        if (availableItems.isEmpty()) {
            System.out.println("No available items to sort.");
            return;
        }

        // Apply QuickSort from scratch!
        QuickSort.sort(availableItems, 0, availableItems.size() - 1);

        System.out.println("\n⚡ [DSA QuickSort] Items successfully sorted by price (Low to High):");
        System.out.println("-------------------------------------------------------------------------");
        for (Item item : availableItems) {
            item.printSummary();
        }
        System.out.println("-------------------------------------------------------------------------");
    }

    // DSA 2: Binary Search (Search within budget)
    private static void searchWithinBudget(double budget) {
        if (items.isEmpty()) {
            System.out.println("No items available.");
            return;
        }

        // Step 1: Collect available items and sort them by price using QuickSort
        ArrayList<Item> availableItems = new ArrayList<>();
        for (Item item : items) {
            if (!item.isSold()) {
                availableItems.add(item);
            }
        }

        QuickSort.sort(availableItems, 0, availableItems.size() - 1);

        // Step 2: Use Binary Search to find all affordable items in O(log N)
        ArrayList<Item> affordable = BinarySearch.searchByBudget(availableItems, budget);

        System.out.printf("%n⚡ [DSA Binary Search] Found %d items within your budget of ₹%.2f:%n", affordable.size(), budget);
        System.out.println("-------------------------------------------------------------------------");
        if (affordable.isEmpty()) {
            System.out.println("  (No items found within this price range)");
        } else {
            for (Item item : affordable) {
                item.printSummary();
            }
        }
        System.out.println("-------------------------------------------------------------------------");
    }

    // View full details of a specific item
    private static void viewItemDetails() {
        System.out.print("\nEnter Item ID to view details: ");
        int id = readInt();

        // Sort items by ID so we can use Binary Search
        sortItemsById(items);
        int index = BinarySearch.searchById(items, id);

        if (index != -1) {
            items.get(index).printFullDetails();
        } else {
            System.out.println("❌ No item found with ID: " + id);
        }
    }

    // Buyer buys an item (marks status as SOLD)
    private static void buyItem() {
        System.out.print("\nEnter the Item ID you want to purchase: ");
        int id = readInt();

        sortItemsById(items);
        int index = BinarySearch.searchById(items, id);

        if (index == -1) {
            System.out.println("❌ Item not found with ID: " + id);
            return;
        }

        Item item = items.get(index);
        if (item.isSold()) {
            System.out.println("⚠️  Sorry, this item is already marked as SOLD.");
            return;
        }

        // Mark as sold and update JSON
        item.setSold(true);
        DataManager.saveItems(items);

        System.out.println("\n🎉 Purchase successful!");
        System.out.println("You bought: " + item.getTitle() + " for ₹" + item.getPrice());
        System.out.println("Please contact the seller at: " + item.getSellerPhone() + " (" + item.getSellerName() + ")");
    }

    // Seller posts a new item
    private static void postItem(Seller seller) {
        System.out.println("\n--- POST A NEW ITEM ---");
        System.out.print("Enter Item Title (e.g. Casio Calculator): ");
        String title = scanner.nextLine().trim();

        System.out.print("Enter Category (e.g. Book, Electronics, Cycle, Hostel): ");
        String category = scanner.nextLine().trim();

        System.out.print("Enter Price (₹): ");
        double price = readDouble();

        System.out.print("Enter Short Description: ");
        String desc = scanner.nextLine().trim();

        // Auto-generate next unique ID
        int newId = DataManager.getNextItemId(items);

        Item newItem = new Item(newId, title, category, price, desc, seller.getName(), seller.getPhone());
        items.add(newItem);
        seller.incrementItemsListed();

        // Save to JSON file
        DataManager.saveItems(items);

        System.out.println("\n🎉 Item posted successfully!");
        System.out.println("Assigned Item ID: " + newId);
    }

    // View listings belonging to the logged-in seller
    private static void viewSellerItems(String sellerName) {
        System.out.println("\n-------------------------------------------------------------------------");
        System.out.println(" 📋 MY LISTINGS (" + sellerName + ")");
        System.out.println("-------------------------------------------------------------------------");

        int count = 0;
        for (Item item : items) {
            if (item.getSellerName().equalsIgnoreCase(sellerName)) {
                item.printSummary();
                count++;
            }
        }

        if (count == 0) {
            System.out.println("  (You haven't posted any items yet)");
        }
        System.out.println("-------------------------------------------------------------------------");
    }

    // Seller marks an item as sold
    private static void markItemAsSold(String sellerName) {
        System.out.print("\nEnter Item ID to mark as SOLD: ");
        int id = readInt();

        sortItemsById(items);
        int index = BinarySearch.searchById(items, id);

        if (index == -1) {
            System.out.println("❌ Item not found with ID: " + id);
            return;
        }

        Item item = items.get(index);
        if (!item.getSellerName().equalsIgnoreCase(sellerName)) {
            System.out.println("⚠️  You can only change status for your own items!");
            return;
        }

        item.setSold(true);
        DataManager.saveItems(items);
        System.out.println("✅ Item [" + id + "] " + item.getTitle() + " has been marked as SOLD.");
    }

    // Simple bubble-sort/insertion helper by ID so Binary Search can run
    private static void sortItemsById(ArrayList<Item> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - i - 1; j++) {
                if (list.get(j).getId() > list.get(j + 1).getId()) {
                    Item temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
    }

    // Safe integer reader
    private static int readInt() {
        while (true) {
            try {
                String line = scanner.nextLine().trim();
                return Integer.parseInt(line);
            } catch (Exception e) {
                System.out.print("⚠️  Invalid number. Enter again: ");
            }
        }
    }

    // Safe double reader
    private static double readDouble() {
        while (true) {
            try {
                String line = scanner.nextLine().trim();
                return Double.parseDouble(line);
            } catch (Exception e) {
                System.out.print("⚠️  Invalid amount. Enter again: ");
            }
        }
    }
}
