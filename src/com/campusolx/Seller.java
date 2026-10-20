package com.campusolx;

/**
 * Seller class extending User (Demonstrates Inheritance).
 * Adds specific seller attributes like items listed count.
 */
public class Seller extends User {
    private int itemsListed;

    public Seller(String name, String phone, String hostel) {
        // Calls the superclass (User) constructor
        super(name, phone, hostel, "Seller");
        this.itemsListed = 0;
    }

    public int getItemsListed() {
        return itemsListed;
    }

    public void incrementItemsListed() {
        this.itemsListed++;
    }

    // Demonstrates Polymorphism (Method Overriding)
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Items Listed: " + itemsListed);
        System.out.println("--------------------------------");
    }
}
