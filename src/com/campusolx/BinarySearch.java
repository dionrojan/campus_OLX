package com.campusolx;

import java.util.ArrayList;

/**
 * Standard BinarySearch algorithm.
 * Requires the list to be sorted beforehand.
 *
 * Time Complexity: O(log N)
 */
public class BinarySearch {

    /**
     * Binary Search to find an item by its unique ID.
     * Assumes list is sorted by Item ID.
     * Returns index of the item, or -1 if not found.
     */
    public static int searchById(ArrayList<Item> list, int targetId) {
        int low = 0;
        int high = list.size() - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            int midId = list.get(mid).getId();

            if (midId == targetId) {
                return mid; // Item found at index mid!
            } else if (midId < targetId) {
                low = mid + 1; // Target is in right half
            } else {
                high = mid - 1; // Target is in left half
            }
        }

        return -1; // Item not found
    }

    /**
     * Binary Search to find all items within a student's maximum budget.
     * Assumes list is sorted by Price in ascending order.
     */
    public static ArrayList<Item> searchByBudget(ArrayList<Item> sortedList, double maxBudget) {
        ArrayList<Item> affordableItems = new ArrayList<>();
        if (sortedList == null || sortedList.isEmpty()) {
            return affordableItems;
        }

        int low = 0;
        int high = sortedList.size() - 1;
        int lastAffordableIndex = -1;

        // Use binary search to find the highest index where price <= maxBudget
        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (sortedList.get(mid).getPrice() <= maxBudget) {
                lastAffordableIndex = mid;
                low = mid + 1; // Look in right half to see if higher priced items still fit
            } else {
                high = mid - 1; // Price is too high, search in left half
            }
        }

        // Collect all items up to lastAffordableIndex
        if (lastAffordableIndex != -1) {
            for (int i = 0; i <= lastAffordableIndex; i++) {
                affordableItems.add(sortedList.get(i));
            }
        }

        return affordableItems;
    }
}
