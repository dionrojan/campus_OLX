package com.campusolx;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Handles saving and loading Item data to and from a JSON file using Gson.
 */
public class DataManager {
    private static final String FILE_PATH = "data/items.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Load all items from items.json
    public static ArrayList<Item> loadItems() {
        ArrayList<Item> items = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return items;
        }

        try (FileReader reader = new FileReader(file)) {
            Item[] array = gson.fromJson(reader, Item[].class);
            if (array != null) {
                items.addAll(Arrays.asList(array));
            }
        } catch (Exception e) {
            System.out.println("⚠️  Could not load items: " + e.getMessage());
        }

        return items;
    }

    // Save all items to items.json
    public static void saveItems(ArrayList<Item> items) {
        File file = new File(FILE_PATH);

        // Ensure parent 'data' directory exists
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }

        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(items, writer);
        } catch (Exception e) {
            System.out.println("⚠️  Could not save items: " + e.getMessage());
        }
    }

    // Generates the next unique integer ID for a new item
    public static int getNextItemId(ArrayList<Item> items) {
        int maxId = 100; // IDs start at 101
        for (Item item : items) {
            if (item.getId() > maxId) {
                maxId = item.getId();
            }
        }
        return maxId + 1;
    }
}
