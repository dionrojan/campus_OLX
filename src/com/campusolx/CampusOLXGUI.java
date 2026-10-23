package com.campusolx;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * Basic Graphical User Interface (GUI) built with Java Swing.
 *
 * OOP Principles Demonstrated:
 * 1. Inheritance  : Extends JFrame (inherits all desktop window properties).
 * 2. Encapsulation: All GUI components, data lists, and helpers are private.
 * 3. Polymorphism : Event listeners (ActionListener) responding to button clicks.
 */
public class CampusOLXGUI extends JFrame {

    // Private UI Components (Encapsulation)
    private JTable itemTable;
    private DefaultTableModel tableModel;
    private JTextField txtBudget;
    private JLabel lblStatus;

    // In-memory data list
    private ArrayList<Item> currentItems;

    public CampusOLXGUI() {
        // Load initial items from JSON file
        currentItems = DataManager.loadItems();

        // Initialize Window Settings
        setTitle("🎓 Campus OLX — College Marketplace (Swing GUI)");
        setSize(900, 580);
        setMinimumSize(new Dimension(800, 500));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center window on screen
        setLayout(new BorderLayout(10, 10));

        // Build the 3 Main Panels
        add(createTopPanel(), BorderLayout.NORTH);
        add(createCenterTablePanel(), BorderLayout.CENTER);
        add(createBottomActionPanel(), BorderLayout.SOUTH);

        // Load items into the visual table
        displayItemsInTable(currentItems);
    }

    // -------------------------------------------------------------------------
    // TOP PANEL: Title & DSA Controls (QuickSort + Binary Search)
    // -------------------------------------------------------------------------
    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout(10, 5));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 5, 15));

        // Title Header
        JLabel lblHeader = new JLabel("🎓 Campus OLX — Marketplace", JLabel.LEFT);
        lblHeader.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(lblHeader, BorderLayout.NORTH);

        // DSA Controls Panel (QuickSort & Binary Search)
        JPanel dsaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        dsaPanel.setBackground(new Color(245, 245, 245));
        dsaPanel.setBorder(BorderFactory.createTitledBorder("DSA Algorithms"));

        // QuickSort Button
        JButton btnQuickSort = new JButton("⚡ Sort by Price (QuickSort)");
        btnQuickSort.setBackground(new Color(220, 240, 255));
        btnQuickSort.addActionListener(e -> handleQuickSort());
        dsaPanel.add(btnQuickSort);

        // Separator label
        dsaPanel.add(new JLabel(" |  Budget Search: ₹"));

        // Budget Input Field
        txtBudget = new JTextField(6);
        txtBudget.setToolTipText("Enter maximum budget in ₹");
        dsaPanel.add(txtBudget);

        // Binary Search Button
        JButton btnBinarySearch = new JButton("🔍 Filter Budget (Binary Search)");
        btnBinarySearch.setBackground(new Color(220, 255, 220));
        btnBinarySearch.addActionListener(e -> handleBinarySearch());
        dsaPanel.add(btnBinarySearch);

        // Reset / Show All Button
        JButton btnShowAll = new JButton("🔄 Show All Items");
        btnShowAll.addActionListener(e -> {
            currentItems = DataManager.loadItems();
            displayItemsInTable(currentItems);
            lblStatus.setText("Showing all " + currentItems.size() + " items.");
        });
        dsaPanel.add(btnShowAll);

        topPanel.add(dsaPanel, BorderLayout.SOUTH);
        return topPanel;
    }

    // -------------------------------------------------------------------------
    // CENTER PANEL: Table displaying items
    // -------------------------------------------------------------------------
    private JScrollPane createCenterTablePanel() {
        String[] columnNames = {"ID", "Category", "Title", "Price (₹)", "Description", "Seller", "Phone", "Status"};

        // Non-editable table model for clean display
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table cells read-only
            }
        };

        itemTable = new JTable(tableModel);
        itemTable.setRowHeight(24);
        itemTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        itemTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Configure column widths
        itemTable.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
        itemTable.getColumnModel().getColumn(1).setPreferredWidth(90);  // Category
        itemTable.getColumnModel().getColumn(2).setPreferredWidth(160); // Title
        itemTable.getColumnModel().getColumn(3).setPreferredWidth(80);  // Price
        itemTable.getColumnModel().getColumn(4).setPreferredWidth(200); // Description
        itemTable.getColumnModel().getColumn(5).setPreferredWidth(100); // Seller
        itemTable.getColumnModel().getColumn(6).setPreferredWidth(100); // Phone
        itemTable.getColumnModel().getColumn(7).setPreferredWidth(90);  // Status

        JScrollPane scrollPane = new JScrollPane(itemTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        return scrollPane;
    }

    // -------------------------------------------------------------------------
    // BOTTOM PANEL: Action Buttons & Status Bar
    // -------------------------------------------------------------------------
    private JPanel createBottomActionPanel() {
        JPanel bottomContainer = new JPanel(new BorderLayout(5, 5));
        bottomContainer.setBorder(BorderFactory.createEmptyBorder(5, 15, 10, 15));

        // Action Buttons Panel
        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));

        JButton btnPostItem = new JButton("➕ Post New Item");
        btnPostItem.setFont(new Font("Arial", Font.BOLD, 12));
        btnPostItem.addActionListener(e -> handlePostItemDialog());
        actionsPanel.add(btnPostItem);

        JButton btnBuyItem = new JButton("🛒 Buy / Mark as Sold");
        btnBuyItem.setFont(new Font("Arial", Font.BOLD, 12));
        btnBuyItem.addActionListener(e -> handleBuySelectedItem());
        actionsPanel.add(btnBuyItem);

        // Status Bar at the bottom
        lblStatus = new JLabel("Welcome to Campus OLX! Select an action or search above.");
        lblStatus.setFont(new Font("Arial", Font.ITALIC, 12));
        lblStatus.setForeground(new Color(80, 80, 80));

        bottomContainer.add(actionsPanel, BorderLayout.NORTH);
        bottomContainer.add(lblStatus, BorderLayout.SOUTH);

        return bottomContainer;
    }

    // -------------------------------------------------------------------------
    // DSA EVENT HANDLERS
    // -------------------------------------------------------------------------

    // DSA 1: QuickSort (Sort items by price low-to-high)
    private void handleQuickSort() {
        if (currentItems.isEmpty()) {
            lblStatus.setText("No items to sort.");
            return;
        }

        // Apply custom QuickSort from scratch!
        QuickSort.sort(currentItems, 0, currentItems.size() - 1);

        displayItemsInTable(currentItems);
        lblStatus.setText("⚡ QuickSort executed: Items sorted by price (Low to High) in O(N log N)!");
    }

    // DSA 2: Binary Search (Filter items within student budget)
    private void handleBinarySearch() {
        String input = txtBudget.getText().trim();
        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a maximum budget amount (₹).", "Missing Budget", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double budget;
        try {
            budget = Double.parseDouble(input);
            if (budget <= 0) {
                JOptionPane.showMessageDialog(this, "Budget must be greater than 0.", "Invalid Budget", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid numeric budget.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Step 1: Ensure items are sorted by price using QuickSort
        QuickSort.sort(currentItems, 0, currentItems.size() - 1);

        // Step 2: Use BinarySearch to find all affordable items in O(log N)
        ArrayList<Item> affordable = BinarySearch.searchByBudget(currentItems, budget);

        displayItemsInTable(affordable);
        lblStatus.setText(String.format("🔍 Binary Search: Located %d affordable item(s) for ₹%.2f budget in O(log N)!", affordable.size(), budget));
    }

    // Seller: Post New Item Dialog
    private void handlePostItemDialog() {
        JTextField txtTitle = new JTextField();
        JTextField txtCategory = new JTextField();
        JTextField txtPrice = new JTextField();
        JTextField txtDesc = new JTextField();
        JTextField txtSeller = new JTextField();
        JTextField txtPhone = new JTextField();

        Object[] form = {
                "Item Title (e.g. Casio fx-991EX):", txtTitle,
                "Category (e.g. Book, Electronics, Cycle):", txtCategory,
                "Price (₹):", txtPrice,
                "Description:", txtDesc,
                "Your Name:", txtSeller,
                "Phone Number:", txtPhone
        };

        int result = JOptionPane.showConfirmDialog(this, form, "Post New Campus Item", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String title = txtTitle.getText().trim();
            String category = txtCategory.getText().trim();
            String priceStr = txtPrice.getText().trim();
            String desc = txtDesc.getText().trim();
            String seller = txtSeller.getText().trim();
            String phone = txtPhone.getText().trim();

            if (title.isEmpty() || category.isEmpty() || priceStr.isEmpty() || seller.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all required fields.", "Incomplete Form", JOptionPane.WARNING_MESSAGE);
                return;
            }

            double price;
            try {
                price = Double.parseDouble(priceStr);
                if (price <= 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid price greater than 0.", "Invalid Price", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Generate new ID and save
            int newId = DataManager.getNextItemId(currentItems);
            Item newItem = new Item(newId, title, category, price, desc, seller, phone);

            currentItems.add(newItem);
            DataManager.saveItems(currentItems);

            displayItemsInTable(currentItems);
            lblStatus.setText("🎉 Posted item [" + newId + "] " + title + " successfully and saved to JSON!");
        }
    }

    // Buyer: Buy / Mark selected item as SOLD
    private void handleBuySelectedItem() {
        int selectedRow = itemTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an item from the table first.", "No Selection", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int itemId = (int) tableModel.getValueAt(selectedRow, 0);

        // Find item in currentItems
        for (Item item : currentItems) {
            if (item.getId() == itemId) {
                if (item.isSold()) {
                    JOptionPane.showMessageDialog(this, "This item is already marked as SOLD.", "Already Sold", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to purchase '" + item.getTitle() + "' for ₹" + item.getPrice() + "?\nSeller: " + item.getSellerName() + " (" + item.getSellerPhone() + ")",
                        "Confirm Purchase",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    item.setSold(true);
                    DataManager.saveItems(currentItems); // Persist update to JSON
                    displayItemsInTable(currentItems);
                    lblStatus.setText("🎉 Item [" + itemId + "] marked as SOLD! Contact seller: " + item.getSellerPhone());
                }
                return;
            }
        }
    }

    // Helper: Populates JTable with list of items
    private void displayItemsInTable(ArrayList<Item> itemsToDisplay) {
        tableModel.setRowCount(0); // Clear existing rows

        for (Item item : itemsToDisplay) {
            Object[] row = {
                    item.getId(),
                    item.getCategory(),
                    item.getTitle(),
                    String.format("%.2f", item.getPrice()),
                    item.getDescription(),
                    item.getSellerName(),
                    item.getSellerPhone(),
                    item.isSold() ? "❌ SOLD" : "✅ AVAILABLE"
            };
            tableModel.addRow(row);
        }
    }

    // Main entry point for standalone GUI launch
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CampusOLXGUI gui = new CampusOLXGUI();
            gui.setVisible(true);
        });
    }
}
