package com.campusolx;

/**
 * Buyer class extending User (Demonstrates Inheritance).
 * Adds specific buyer attributes like college budget.
 */
public class Buyer extends User {
    private double budget;

    public Buyer(String name, String phone, String hostel, double budget) {
        // Calls the superclass (User) constructor
        super(name, phone, hostel, "Buyer");
        this.budget = budget;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    // Demonstrates Polymorphism (Method Overriding)
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.printf("Max Budget: ₹%.2f%n", budget);
        System.out.println("--------------------------------");
    }
}
