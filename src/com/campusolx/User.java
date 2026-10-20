package com.campusolx;

/**
 * Base User class demonstrating Encapsulation.
 * All attributes are private, accessed via getters and setters.
 */
public class User {
    private String name;
    private String phone;
    private String hostel;
    private String role; // "Buyer" or "Seller"

    public User(String name, String phone, String hostel, String role) {
        this.name = name;
        this.phone = phone;
        this.hostel = hostel;
        this.role = role;
    }

    // Getters and Setters (Encapsulation)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHostel() {
        return hostel;
    }

    public void setHostel(String hostel) {
        this.hostel = hostel;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // This method will be overridden by Buyer and Seller (Polymorphism)
    public void displayInfo() {
        System.out.println("--------------------------------");
        System.out.println("Role   : " + role);
        System.out.println("Name   : " + name);
        System.out.println("Phone  : " + phone);
        System.out.println("Hostel : " + hostel);
        System.out.println("--------------------------------");
    }
}
