package com.campusolx;

import java.util.ArrayList;

/**
 * Standard QuickSort algorithm (Divide and Conquer).
 * Sorts an ArrayList of Items by Price in ascending order.
 *
 * Time Complexity:
 * - Average Case: O(N log N)
 * - Worst Case: O(N^2)
 */
public class QuickSort {

    // Main sorting method called from outside
    public static void sort(ArrayList<Item> list, int low, int high) {
        if (low < high) {
            // Find pivot index such that elements left of pivot are smaller,
            // and elements right of pivot are larger
            int pivotIndex = partition(list, low, high);

            // Recursively sort left subarray
            sort(list, low, pivotIndex - 1);

            // Recursively sort right subarray
            sort(list, pivotIndex + 1, high);
        }
    }

    // Standard Lomuto Partition function (takes last element as pivot)
    private static int partition(ArrayList<Item> list, int low, int high) {
        double pivotPrice = list.get(high).getPrice();
        int i = low - 1; // Index of smaller element

        for (int j = low; j < high; j++) {
            // If current item's price is less than or equal to pivot price
            if (list.get(j).getPrice() <= pivotPrice) {
                i++;
                swap(list, i, j);
            }
        }

        // Place the pivot element in its correct sorted position
        swap(list, i + 1, high);
        return i + 1;
    }

    // Helper method to swap two items in the ArrayList
    private static void swap(ArrayList<Item> list, int i, int j) {
        Item temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
}
