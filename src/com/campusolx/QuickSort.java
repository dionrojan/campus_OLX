package com.campusolx;

import java.util.ArrayList;

/**
 * Standard QuickSort algorithm (Divide and Conquer).
 * Built from scratch for Campus OLX.
 *
 * Supports:
 * 1. Sorting by Price (Ascending - Low to High)
 * 2. Sorting by Price (Descending - High to Low)
 * 3. Sorting by Item ID (for Binary Search lookup)
 *
 * Time Complexity:
 * - Average Case: O(N log N)
 * - Worst Case: O(N^2)
 */
public class QuickSort {

    // 1. Sort by Price Ascending (Low to High)
    public static void sort(ArrayList<Item> list, int low, int high) {
        if (low < high) {
            int pivotIndex = partitionAscending(list, low, high);
            sort(list, low, pivotIndex - 1);
            sort(list, pivotIndex + 1, high);
        }
    }

    private static int partitionAscending(ArrayList<Item> list, int low, int high) {
        double pivotPrice = list.get(high).getPrice();
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (list.get(j).getPrice() <= pivotPrice) {
                i++;
                swap(list, i, j);
            }
        }
        swap(list, i + 1, high);
        return i + 1;
    }

    // 2. Sort by Price Descending (High to Low)
    public static void sortDescending(ArrayList<Item> list, int low, int high) {
        if (low < high) {
            int pivotIndex = partitionDescending(list, low, high);
            sortDescending(list, low, pivotIndex - 1);
            sortDescending(list, pivotIndex + 1, high);
        }
    }

    private static int partitionDescending(ArrayList<Item> list, int low, int high) {
        double pivotPrice = list.get(high).getPrice();
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (list.get(j).getPrice() >= pivotPrice) {
                i++;
                swap(list, i, j);
            }
        }
        swap(list, i + 1, high);
        return i + 1;
    }

    // 3. Sort by Item ID (to prepare list for BinarySearch.searchById)
    public static void sortById(ArrayList<Item> list, int low, int high) {
        if (low < high) {
            int pivotIndex = partitionById(list, low, high);
            sortById(list, low, pivotIndex - 1);
            sortById(list, pivotIndex + 1, high);
        }
    }

    private static int partitionById(ArrayList<Item> list, int low, int high) {
        int pivotId = list.get(high).getId();
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (list.get(j).getId() <= pivotId) {
                i++;
                swap(list, i, j);
            }
        }
        swap(list, i + 1, high);
        return i + 1;
    }

    // Helper method to swap two items
    private static void swap(ArrayList<Item> list, int i, int j) {
        Item temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
}
