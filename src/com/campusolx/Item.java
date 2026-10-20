package com.campusolx;

/**
 * Item class representing an item listed on Campus OLX.
 * Demonstrates Encapsulation with private variables and getters/setters.
 */
public class Item {
    private int id;
    private String title;
    private String category;
    private double price;
    private String description;
    private String sellerName;
    private String sellerPhone;
    private boolean isSold;

    public Item(int id, String title, String category, double price, String description, String sellerName, String sellerPhone) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.price = price;
        this.description = description;
        this.sellerName = sellerName;
        this.sellerPhone = sellerPhone;
        this.isSold = false; // By default, newly added item is available
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price > 0) {
            this.price = price;
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerPhone() {
        return sellerPhone;
    }

    public void setSellerPhone(String sellerPhone) {
        this.sellerPhone = sellerPhone;
    }

    public boolean isSold() {
        return isSold;
    }

    public void setSold(boolean sold) {
        isSold = sold;
    }

    // Displays a one-line summary in tables
    public void printSummary() {
        System.out.printf("[%d] %-12s | ₹%-8.2f | %-30s | Status: %s%n",
                id, category, price, truncate(title, 30), (isSold ? "SOLD" : "AVAILABLE"));
    }

    // Displays complete details when viewing a specific item
    public void printFullDetails() {
        System.out.println("==================================================");
        System.out.println(" Item ID     : " + id);
        System.out.println(" Title       : " + title);
        System.out.println(" Category    : " + category);
        System.out.printf (" Price       : ₹%.2f%n", price);
        System.out.println(" Status      : " + (isSold ? "❌ SOLD" : "✅ AVAILABLE"));
        System.out.println(" Description : " + description);
        System.out.println(" Seller Name : " + sellerName);
        System.out.println(" Seller Phone: " + sellerPhone);
        System.out.println("==================================================");
    }

    private String truncate(String text, int max) {
        if (text == null) return "";
        return text.length() <= max ? text : text.substring(0, max - 3) + "...";
    }
}
