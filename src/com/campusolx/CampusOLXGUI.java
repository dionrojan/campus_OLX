package com.campusolx;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * Enhanced Graphical User Interface (GUI) with distinct Buyer and Seller modes.
 *
 * Demonstrates:
 * 1. Inheritance  : Extends JFrame.
 * 2. Encapsulation: Private UI components and methods.
 * 3. Polymorphism : Buyer and Seller displayInfo() profiles triggered via GUI buttons.
 * 4. JTabbedPane  : Clean switching between Buyer and Seller roles.
 */
public class CampusOLXGUI extends JFrame {

    // In-memory data list
    private ArrayList<Item> items;

    // Buyer Components
    private JTable buyerTable;
    private DefaultTableModel buyerTableModel;
    private JTextField txtBudget;
    private Buyer currentBuyer;

    // Seller Components
    private JTable sellerTable;
    private DefaultTableModel sellerTableModel;
    private JTextField txtTitle, txtCategory, txtPrice, txtDesc, txtSellerName, txtSellerPhone;
    private Seller currentSeller;

    // General Components
    private JTabbedPane tabbedPane;
    private JLabel lblStatus;

    public CampusOLXGUI() {
        // Load initial data from items.json
        items = DataManager.loadItems();

        // Default Demo Users (Demonstrating OOP Models)
        currentBuyer = new Buyer("Rahul", "9988776655", "Block-B", 1000.0);
        currentSeller = new Seller("Aman", "9112233445", "Block-C");

        // Window Setup
        setTitle("🎓 Campus OLX — College Marketplace (Swing GUI)");
        setSize(950, 620);
        setMinimumSize(new Dimension(850, 520));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen
        setLayout(new BorderLayout(5, 5));

        // 1. Header with Role Switcher
        add(createHeaderPanel(), BorderLayout.NORTH);

        // 2. Tabbed Pane (Buyer Mode vs Seller Mode)
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 13));
        tabbedPane.addTab("🛒 Buyer Mode (Browse, Search & Buy)", createBuyerPanel());
        tabbedPane.addTab("🏷️ Seller Mode (Post & Manage Listings)", createSellerPanel());
        tabbedPane.addChangeListener(e -> {
            int selected = tabbedPane.getSelectedIndex();
            lblStatus.setText("Switched to " + (selected == 0 ? "Buyer Mode" : "Seller Mode"));
        });
        add(tabbedPane, BorderLayout.CENTER);

        // 3. Status Bar at the bottom
        lblStatus = new JLabel(" Ready. Switch between Buyer and Seller tabs anytime!");
        lblStatus.setFont(new Font("Arial", Font.ITALIC, 12));
        lblStatus.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        add(lblStatus, BorderLayout.SOUTH);

        // Initial Data Load
        refreshBuyerTable(items);
        refreshSellerTable();
    }

    // -------------------------------------------------------------------------
    // HEADER: Clean Title Header
    // -------------------------------------------------------------------------
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        headerPanel.setBackground(new Color(240, 245, 250));

        JLabel lblTitle = new JLabel("🎓 CAMPUS OLX — College Marketplace");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(new Color(20, 60, 120));

        headerPanel.add(lblTitle);
        return headerPanel;
    }

    // =========================================================================
    // TAB 1: BUYER MODE
    // =========================================================================
    private JPanel createBuyerPanel() {
        JPanel buyerPanel = new JPanel(new BorderLayout(10, 10));
        buyerPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // Top Toolbar: DSA Algorithms (QuickSort & Binary Search)
        JPanel dsaToolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        dsaToolbar.setBorder(BorderFactory.createTitledBorder("Buyer Tools (DSA Algorithms)"));

        JButton btnSort = new JButton("⚡ Sort by Price (QuickSort)");
        btnSort.setBackground(new Color(225, 240, 255));
        btnSort.addActionListener(e -> handleQuickSort());
        dsaToolbar.add(btnSort);

        dsaToolbar.add(new JLabel(" |  Max Budget: ₹"));
        txtBudget = new JTextField(currentBuyer.getBudget() + "", 6);
        dsaToolbar.add(txtBudget);

        JButton btnBinarySearch = new JButton("🔍 Filter Budget (Binary Search)");
        btnBinarySearch.setBackground(new Color(225, 255, 225));
        btnBinarySearch.addActionListener(e -> handleBinarySearch());
        dsaToolbar.add(btnBinarySearch);

        JButton btnReset = new JButton("🔄 Reset List");
        btnReset.addActionListener(e -> {
            items = DataManager.loadItems();
            refreshBuyerTable(items);
            lblStatus.setText("Displaying all available items.");
        });
        dsaToolbar.add(btnReset);

        buyerPanel.add(dsaToolbar, BorderLayout.NORTH);

        // Center Table: Available Items
        buyerTableModel = createNonEditableModel();
        buyerTable = new JTable(buyerTableModel);
        setupTableColumns(buyerTable);
        buyerPanel.add(new JScrollPane(buyerTable), BorderLayout.CENTER);

        // Bottom Actions for Buyer
        JPanel buyerBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));

        JButton btnBuyerProfile = new JButton("👤 View Buyer Profile (OOP Polymorphism)");
        btnBuyerProfile.addActionListener(e -> showProfileDialog(currentBuyer));
        buyerBottom.add(btnBuyerProfile);

        JButton btnBuy = new JButton("🛒 Buy Selected Item");
        btnBuy.setFont(new Font("Arial", Font.BOLD, 12));
        btnBuy.setBackground(new Color(200, 240, 200));
        btnBuy.addActionListener(e -> handleBuySelectedItem());
        buyerBottom.add(btnBuy);

        buyerPanel.add(buyerBottom, BorderLayout.SOUTH);

        return buyerPanel;
    }

    // =========================================================================
    // TAB 2: SELLER MODE
    // =========================================================================
    private JPanel createSellerPanel() {
        JPanel sellerPanel = new JPanel(new BorderLayout(10, 10));
        sellerPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // Top: Form to Post New Items
        JPanel formPanel = new JPanel(new GridLayout(2, 6, 8, 8));
        formPanel.setBorder(BorderFactory.createTitledBorder("Post a New Campus Item for Sale"));

        txtTitle = new JTextField();
        txtCategory = new JTextField();
        txtPrice = new JTextField();
        txtDesc = new JTextField();
        txtSellerName = new JTextField(currentSeller.getName());
        txtSellerPhone = new JTextField(currentSeller.getPhone());

        formPanel.add(new JLabel("Item Title:"));
        formPanel.add(new JLabel("Category:"));
        formPanel.add(new JLabel("Price (₹):"));
        formPanel.add(new JLabel("Short Description:"));
        formPanel.add(new JLabel("Your Name:"));
        formPanel.add(new JLabel("Contact Phone:"));

        formPanel.add(txtTitle);
        formPanel.add(txtCategory);
        formPanel.add(txtPrice);
        formPanel.add(txtDesc);
        formPanel.add(txtSellerName);
        formPanel.add(txtPhoneAndButton(txtSellerPhone));

        sellerPanel.add(formPanel, BorderLayout.NORTH);

        // Center Table: Shows All Items / My Listings
        sellerTableModel = createNonEditableModel();
        sellerTable = new JTable(sellerTableModel);
        setupTableColumns(sellerTable);
        sellerPanel.add(new JScrollPane(sellerTable), BorderLayout.CENTER);

        // Bottom Actions for Seller
        JPanel sellerBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));

        JButton btnSellerProfile = new JButton("👤 View Seller Profile (OOP Polymorphism)");
        btnSellerProfile.addActionListener(e -> showProfileDialog(currentSeller));
        sellerBottom.add(btnSellerProfile);

        JButton btnMarkSold = new JButton("✅ Mark Selected Item as SOLD");
        btnMarkSold.setFont(new Font("Arial", Font.BOLD, 12));
        btnMarkSold.addActionListener(e -> handleMarkItemAsSold());
        sellerBottom.add(btnMarkSold);

        sellerPanel.add(sellerBottom, BorderLayout.SOUTH);

        return sellerPanel;
    }

    private JPanel txtPhoneAndButton(JTextField phoneField) {
        JPanel p = new JPanel(new BorderLayout(5, 0));
        p.add(phoneField, BorderLayout.CENTER);
        JButton btnSubmit = new JButton("➕ Post");
        btnSubmit.setFont(new Font("Arial", Font.BOLD, 11));
        btnSubmit.addActionListener(e -> handlePostItem());
        p.add(btnSubmit, BorderLayout.EAST);
        return p;
    }

    // -------------------------------------------------------------------------
    // DSA ACTIONS (BUYER)
    // -------------------------------------------------------------------------
    private void handleQuickSort() {
        ArrayList<Item> availableItems = getAvailableItemsList();
        if (availableItems.isEmpty()) {
            lblStatus.setText("No available items to sort.");
            return;
        }

        // Apply QuickSort algorithm from scratch
        QuickSort.sort(availableItems, 0, availableItems.size() - 1);
        refreshBuyerTable(availableItems);
        lblStatus.setText("⚡ QuickSort executed: Items sorted by price (Low to High) in O(N log N)!");
    }

    private void handleBinarySearch() {
        String input = txtBudget.getText().trim();
        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a budget amount (₹).", "Missing Budget", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            double budget = Double.parseDouble(input);
            currentBuyer.setBudget(budget);

            ArrayList<Item> availableItems = getAvailableItemsList();
            QuickSort.sort(availableItems, 0, availableItems.size() - 1);

            // Apply BinarySearch algorithm from scratch
            ArrayList<Item> affordable = BinarySearch.searchByBudget(availableItems, budget);
            refreshBuyerTable(affordable);
            lblStatus.setText(String.format("🔍 Binary Search: Located %d item(s) within ₹%.2f budget in O(log N)!", affordable.size(), budget));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid numeric budget.", "Invalid Number", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleBuySelectedItem() {
        int row = buyerTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select an item from the table first.", "Select Item", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int itemId = (int) buyerTableModel.getValueAt(row, 0);
        Item item = findItemById(itemId);

        if (item != null) {
            if (item.isSold()) {
                JOptionPane.showMessageDialog(this, "This item is already sold.", "Sold Out", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Confirm purchase of '" + item.getTitle() + "' for ₹" + item.getPrice() + "?\nSeller: " + item.getSellerName() + " (" + item.getSellerPhone() + ")",
                    "Purchase Item", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                item.setSold(true);
                DataManager.saveItems(items); // Save update to JSON
                refreshBuyerTable(getAvailableItemsList());
                refreshSellerTable();
                lblStatus.setText("🎉 Item [" + itemId + "] purchased! Contact seller: " + item.getSellerPhone());
            }
        }
    }

    // -------------------------------------------------------------------------
    // SELLER ACTIONS
    // -------------------------------------------------------------------------
    private void handlePostItem() {
        String title = txtTitle.getText().trim();
        String cat = txtCategory.getText().trim();
        String priceStr = txtPrice.getText().trim();
        String desc = txtDesc.getText().trim();
        String seller = txtSellerName.getText().trim();
        String phone = txtSellerPhone.getText().trim();

        if (title.isEmpty() || cat.isEmpty() || priceStr.isEmpty() || seller.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in Title, Category, Price, and Name.", "Incomplete Form", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            double price = Double.parseDouble(priceStr);
            if (price <= 0) throw new NumberFormatException();

            int newId = DataManager.getNextItemId(items);
            Item newItem = new Item(newId, title, cat, price, desc, seller, phone);

            items.add(newItem);
            currentSeller.incrementItemsListed();
            DataManager.saveItems(items); // Save to JSON file

            // Clear inputs
            txtTitle.setText("");
            txtCategory.setText("");
            txtPrice.setText("");
            txtDesc.setText("");

            refreshBuyerTable(getAvailableItemsList());
            refreshSellerTable();
            lblStatus.setText("🎉 Item [" + newId + "] posted successfully and saved to items.json!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid price greater than 0.", "Invalid Price", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleMarkItemAsSold() {
        int row = sellerTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select an item from the table first.", "Select Item", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int itemId = (int) sellerTableModel.getValueAt(row, 0);
        Item item = findItemById(itemId);

        if (item != null) {
            item.setSold(true);
            DataManager.saveItems(items); // Persist to JSON
            refreshBuyerTable(getAvailableItemsList());
            refreshSellerTable();
            lblStatus.setText("✅ Item [" + itemId + "] has been marked as SOLD.");
        }
    }

    // -------------------------------------------------------------------------
    // OOP POLYMORPHISM DEMO DIALOG
    // -------------------------------------------------------------------------
    private void showProfileDialog(User user) {
        // Method overriding demo: Buyer displays budget, Seller displays listed items count
        String profileText = String.format(
                "Role        : %s\nName        : %s\nPhone       : %s\nHostel      : %s\n",
                user.getRole(), user.getName(), user.getPhone(), user.getHostel()
        );

        if (user instanceof Buyer b) {
            profileText += "Max Budget  : ₹" + b.getBudget() + "\n(Class: Buyer extends User)";
        } else if (user instanceof Seller s) {
            profileText += "Items Listed: " + s.getItemsListed() + "\n(Class: Seller extends User)";
        }

        JOptionPane.showMessageDialog(this, profileText, "User Profile (OOP Polymorphism)", JOptionPane.INFORMATION_MESSAGE);
    }

    // -------------------------------------------------------------------------
    // TABLE HELPERS
    // -------------------------------------------------------------------------
    private DefaultTableModel createNonEditableModel() {
        String[] columns = {"ID", "Category", "Title", "Price (₹)", "Description", "Seller", "Phone", "Status"};
        return new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
    }

    private void setupTableColumns(JTable table) {
        table.setRowHeight(24);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.getColumnModel().getColumn(0).setPreferredWidth(45);  // ID
        table.getColumnModel().getColumn(1).setPreferredWidth(85);  // Category
        table.getColumnModel().getColumn(2).setPreferredWidth(170); // Title
        table.getColumnModel().getColumn(3).setPreferredWidth(75);  // Price
        table.getColumnModel().getColumn(4).setPreferredWidth(180); // Description
        table.getColumnModel().getColumn(5).setPreferredWidth(95);  // Seller
        table.getColumnModel().getColumn(6).setPreferredWidth(95);  // Phone
        table.getColumnModel().getColumn(7).setPreferredWidth(85);  // Status
    }

    private void refreshBuyerTable(ArrayList<Item> listToDisplay) {
        buyerTableModel.setRowCount(0);
        for (Item item : listToDisplay) {
            if (!item.isSold()) {
                buyerTableModel.addRow(new Object[]{
                        item.getId(), item.getCategory(), item.getTitle(),
                        String.format("%.2f", item.getPrice()),
                        item.getDescription(), item.getSellerName(),
                        item.getSellerPhone(), "AVAILABLE"
                });
            }
        }
    }

    private void refreshSellerTable() {
        sellerTableModel.setRowCount(0);
        for (Item item : items) {
            sellerTableModel.addRow(new Object[]{
                    item.getId(), item.getCategory(), item.getTitle(),
                    String.format("%.2f", item.getPrice()),
                    item.getDescription(), item.getSellerName(),
                    item.getSellerPhone(), item.isSold() ? "❌ SOLD" : "✅ AVAILABLE"
            });
        }
    }

    private ArrayList<Item> getAvailableItemsList() {
        ArrayList<Item> available = new ArrayList<>();
        for (Item item : items) {
            if (!item.isSold()) {
                available.add(item);
            }
        }
        return available;
    }

    private Item findItemById(int id) {
        for (Item item : items) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CampusOLXGUI().setVisible(true));
    }
}
