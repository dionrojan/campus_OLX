package com.campusolx;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Advanced Graphical User Interface (GUI) for Campus OLX.
 *
 * Advanced Features Implemented:
 * 1. Seller & Buyer Authentication / Session switching.
 * 2. Strict Security: Only the item creator can Mark as Sold or Delete a listing.
 * 3. "My Listings" Filter: Sellers see their own listings by default.
 * 4. Full DSA Integration:
 *    - QuickSort: Low-to-High AND High-to-Low price sorting.
 *    - Binary Search: Budget Range filtering AND exact Item ID lookup.
 * 5. Category Filter: Dropdown filtering by Book, Electronics, Cycle, Hostel, etc.
 * 6. Interactive Details Popup: Double-click any row to view full description and seller card.
 */
public class CampusOLXGUI extends JFrame {

    // Main in-memory data list
    private ArrayList<Item> items;

    // Active User Sessions (Demonstrating OOP Models)
    private Buyer currentBuyer;
    private Seller currentSeller;

    // Buyer UI Components
    private JTable buyerTable;
    private DefaultTableModel buyerTableModel;
    private JTextField txtBudget;
    private JTextField txtSearchId;
    private JComboBox<String> cmbCategory;
    private JComboBox<String> cmbSortOrder;
    private JLabel lblBuyerSession;

    // Seller UI Components
    private JTable sellerTable;
    private DefaultTableModel sellerTableModel;
    private JTextField txtTitle, txtCategory, txtPrice, txtDesc;
    private JLabel lblSellerSession;
    private JCheckBox chkMyListingsOnly;

    // General UI
    private JTabbedPane tabbedPane;
    private JLabel lblStatus;

    public CampusOLXGUI() {
        // Load items from items.json
        items = DataManager.loadItems();

        // Default Student Profiles
        currentBuyer = new Buyer("Rahul", "9988776655", "Block-B", 1000.0);
        currentSeller = new Seller("Rohan Sharma", "9876543210", "Block-A, Room 204");

        // Window Settings
        setTitle("🎓 Campus OLX (Advanced) — College Marketplace");
        setSize(980, 650);
        setMinimumSize(new Dimension(880, 550));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(5, 5));

        // 1. Top Header Banner
        add(createHeaderPanel(), BorderLayout.NORTH);

        // 2. Tabbed Interface (Buyer Mode vs Seller Mode)
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 13));
        tabbedPane.addTab("🛒 Buyer Mode (Browse, Search & Buy)", createBuyerPanel());
        tabbedPane.addTab("🏷️ Seller Mode (Post & Manage Listings)", createSellerPanel());
        tabbedPane.addChangeListener(e -> {
            int selected = tabbedPane.getSelectedIndex();
            lblStatus.setText("Switched to " + (selected == 0 ? "Buyer Mode" : "Seller Mode"));
            refreshAllViews();
        });
        add(tabbedPane, BorderLayout.CENTER);

        // 3. Status Bar at the bottom
        lblStatus = new JLabel(" Ready. Double-click any item in the table to view full details.");
        lblStatus.setFont(new Font("Arial", Font.ITALIC, 12));
        lblStatus.setBorder(BorderFactory.createEmptyBorder(6, 15, 6, 15));
        add(lblStatus, BorderLayout.SOUTH);

        // Initial Data Display
        refreshAllViews();
    }

    // -------------------------------------------------------------------------
    // HEADER BANNER
    // -------------------------------------------------------------------------
    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 8));
        header.setBackground(new Color(235, 243, 252));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 220, 240)));

        JLabel lblTitle = new JLabel("🎓 CAMPUS OLX (ADVANCED)");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setForeground(new Color(15, 50, 110));
        header.add(lblTitle);

        JLabel lblSub = new JLabel("— Secured College Marketplace with DSA & OOPs");
        lblSub.setFont(new Font("Arial", Font.PLAIN, 12));
        lblSub.setForeground(new Color(90, 100, 115));
        header.add(lblSub);

        return header;
    }

    // =========================================================================
    // TAB 1: BUYER DASHBOARD
    // =========================================================================
    private JPanel createBuyerPanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        // Top Toolbar: Buyer Profile + Search Controls
        JPanel topContainer = new JPanel(new BorderLayout(5, 5));

        // Buyer Session Bar
        JPanel sessionBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        sessionBar.setBackground(new Color(245, 248, 255));
        sessionBar.setBorder(BorderFactory.createLineBorder(new Color(220, 230, 245)));

        lblBuyerSession = new JLabel();
        updateBuyerSessionLabel();
        sessionBar.add(lblBuyerSession);

        JButton btnSwitchBuyer = new JButton("🔑 Switch Buyer / Budget");
        btnSwitchBuyer.setFont(new Font("Arial", Font.PLAIN, 11));
        btnSwitchBuyer.addActionListener(e -> handleSwitchBuyerDialog());
        sessionBar.add(btnSwitchBuyer);

        topContainer.add(sessionBar, BorderLayout.NORTH);

        // DSA & Search Controls
        JPanel dsaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        dsaPanel.setBorder(BorderFactory.createTitledBorder("Search & Filter Tools (DSA Algorithms)"));

        // DSA 1: QuickSort Direction
        dsaPanel.add(new JLabel("Sort Price:"));
        cmbSortOrder = new JComboBox<>(new String[]{"Low to High", "High to Low"});
        dsaPanel.add(cmbSortOrder);

        JButton btnSort = new JButton("⚡ QuickSort");
        btnSort.setBackground(new Color(225, 240, 255));
        btnSort.addActionListener(e -> handleQuickSort());
        dsaPanel.add(btnSort);

        dsaPanel.add(new JLabel(" | Budget: ₹"));
        txtBudget = new JTextField(currentBuyer.getBudget() + "", 5);
        dsaPanel.add(txtBudget);

        // DSA 2: Binary Search (Budget Range)
        JButton btnBudgetSearch = new JButton("🔍 Filter Budget (Binary Search)");
        btnBudgetSearch.setBackground(new Color(225, 255, 225));
        btnBudgetSearch.addActionListener(e -> handleBinarySearchBudget());
        dsaPanel.add(btnBudgetSearch);

        // DSA 3: Binary Search by Item ID
        dsaPanel.add(new JLabel(" | Item ID:"));
        txtSearchId = new JTextField(4);
        dsaPanel.add(txtSearchId);

        JButton btnSearchId = new JButton("🎯 Find by ID (Binary Search)");
        btnSearchId.addActionListener(e -> handleBinarySearchById());
        dsaPanel.add(btnSearchId);

        // Category Filter
        dsaPanel.add(new JLabel(" | Category:"));
        cmbCategory = new JComboBox<>(new String[]{"All", "Book", "Electronics", "Cycle", "Hostel", "General"});
        cmbCategory.addActionListener(e -> handleCategoryFilter());
        dsaPanel.add(cmbCategory);

        JButton btnReset = new JButton("🔄 Reset");
        btnReset.addActionListener(e -> {
            items = DataManager.loadItems();
            refreshBuyerTable(getAvailableItemsList());
            lblStatus.setText("Showing all available campus items.");
        });
        dsaPanel.add(btnReset);

        topContainer.add(dsaPanel, BorderLayout.SOUTH);
        panel.add(topContainer, BorderLayout.NORTH);

        // Center Table: Available items
        buyerTableModel = createNonEditableModel();
        buyerTable = new JTable(buyerTableModel);
        setupTableColumns(buyerTable);
        addDoubleClickListener(buyerTable);
        panel.add(new JScrollPane(buyerTable), BorderLayout.CENTER);

        // Bottom Actions
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));

        JButton btnProfile = new JButton("👤 Buyer Profile (OOP Polymorphism)");
        btnProfile.addActionListener(e -> showProfileDialog(currentBuyer));
        bottom.add(btnProfile);

        JButton btnDetails = new JButton("📄 View Item Details");
        btnDetails.addActionListener(e -> showSelectedItemDetails(buyerTable));
        bottom.add(btnDetails);

        JButton btnBuy = new JButton("🛒 Purchase Item");
        btnBuy.setFont(new Font("Arial", Font.BOLD, 12));
        btnBuy.setBackground(new Color(205, 245, 205));
        btnBuy.addActionListener(e -> handleBuySelectedItem());
        bottom.add(btnBuy);

        panel.add(bottom, BorderLayout.SOUTH);
        return panel;
    }

    // =========================================================================
    // TAB 2: SELLER DASHBOARD (WITH AUTHENTICATION & SECURITY CHECKS)
    // =========================================================================
    private JPanel createSellerPanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        // Top Container: Seller Session Bar + Post Item Form
        JPanel topContainer = new JPanel(new BorderLayout(5, 5));

        // 1. Seller Session Bar
        JPanel sessionBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        sessionBar.setBackground(new Color(255, 250, 240));
        sessionBar.setBorder(BorderFactory.createLineBorder(new Color(245, 230, 200)));

        lblSellerSession = new JLabel();
        updateSellerSessionLabel();
        sessionBar.add(lblSellerSession);

        JButton btnSwitchSeller = new JButton("🔑 Switch / Login as Seller");
        btnSwitchSeller.setFont(new Font("Arial", Font.PLAIN, 11));
        btnSwitchSeller.addActionListener(e -> handleSwitchSellerDialog());
        sessionBar.add(btnSwitchSeller);

        topContainer.add(sessionBar, BorderLayout.NORTH);

        // 2. Post New Item Form
        JPanel formPanel = new JPanel(new GridLayout(2, 5, 8, 6));
        formPanel.setBorder(BorderFactory.createTitledBorder("Post a New Campus Item"));

        txtTitle = new JTextField();
        txtCategory = new JTextField();
        txtPrice = new JTextField();
        txtDesc = new JTextField();

        formPanel.add(new JLabel("Item Title (e.g. Drafter):"));
        formPanel.add(new JLabel("Category (Book, Cycle, etc.):"));
        formPanel.add(new JLabel("Price (₹):"));
        formPanel.add(new JLabel("Short Description:"));
        formPanel.add(new JLabel("")); // Spacer

        formPanel.add(txtTitle);
        formPanel.add(txtCategory);
        formPanel.add(txtPrice);
        formPanel.add(txtDesc);

        JButton btnPost = new JButton("➕ Post Item");
        btnPost.setFont(new Font("Arial", Font.BOLD, 12));
        btnPost.setBackground(new Color(225, 245, 225));
        btnPost.addActionListener(e -> handlePostItem());
        formPanel.add(btnPost);

        topContainer.add(formPanel, BorderLayout.CENTER);

        // 3. Filter Options above table
        JPanel tableHeaderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 4));
        chkMyListingsOnly = new JCheckBox("Show ONLY My Listings (" + currentSeller.getName() + ")", true);
        chkMyListingsOnly.setFont(new Font("Arial", Font.BOLD, 12));
        chkMyListingsOnly.addActionListener(e -> refreshSellerTable());
        tableHeaderPanel.add(chkMyListingsOnly);
        topContainer.add(tableHeaderPanel, BorderLayout.SOUTH);

        panel.add(topContainer, BorderLayout.NORTH);

        // Center Table: Seller Items
        sellerTableModel = createNonEditableModel();
        sellerTable = new JTable(sellerTableModel);
        setupTableColumns(sellerTable);
        addDoubleClickListener(sellerTable);
        panel.add(new JScrollPane(sellerTable), BorderLayout.CENTER);

        // Bottom Actions (Guarded by Ownership Verification)
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));

        JButton btnProfile = new JButton("👤 Seller Profile (OOP Polymorphism)");
        btnProfile.addActionListener(e -> showProfileDialog(currentSeller));
        bottom.add(btnProfile);

        JButton btnDelete = new JButton("🗑️ Delete My Listing");
        btnDelete.setForeground(new Color(180, 0, 0));
        btnDelete.addActionListener(e -> handleDeleteListing());
        bottom.add(btnDelete);

        JButton btnMarkSold = new JButton("✅ Mark as SOLD");
        btnMarkSold.setFont(new Font("Arial", Font.BOLD, 12));
        btnMarkSold.addActionListener(e -> handleMarkItemAsSold());
        bottom.add(btnMarkSold);

        panel.add(bottom, BorderLayout.SOUTH);
        return panel;
    }

    // =========================================================================
    // AUTHENTICATION & SESSION MANAGEMENT
    // =========================================================================
    private void updateBuyerSessionLabel() {
        lblBuyerSession.setText(String.format("👤 Logged in as: %s | Phone: %s | Hostel: %s | Budget: ₹%.2f",
                currentBuyer.getName(), currentBuyer.getPhone(), currentBuyer.getHostel(), currentBuyer.getBudget()));
    }

    private void updateSellerSessionLabel() {
        int myItemsCount = getSellerItemsCount(currentSeller.getName());
        lblSellerSession.setText(String.format("👤 Logged in Seller: %s | Phone: %s | Hostel: %s | Active Listings: %d",
                currentSeller.getName(), currentSeller.getPhone(), currentSeller.getHostel(), myItemsCount));
    }

    private void handleSwitchSellerDialog() {
        JTextField nameField = new JTextField(currentSeller.getName());
        JTextField phoneField = new JTextField(currentSeller.getPhone());
        JTextField hostelField = new JTextField(currentSeller.getHostel());

        Object[] message = {
                "Seller Full Name:", nameField,
                "Phone Number:", phoneField,
                "Hostel / Room No:", hostelField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Seller Login / Switch Account", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            String phone = phoneField.getText().trim();
            String hostel = hostelField.getText().trim();

            if (name.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name and Phone cannot be blank.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                return;
            }

            currentSeller = new Seller(name, phone, hostel);
            updateSellerSessionLabel();
            chkMyListingsOnly.setText("Show ONLY My Listings (" + currentSeller.getName() + ")");
            refreshSellerTable();
            lblStatus.setText("Logged in as Seller: " + currentSeller.getName());
        }
    }

    private void handleSwitchBuyerDialog() {
        JTextField nameField = new JTextField(currentBuyer.getName());
        JTextField phoneField = new JTextField(currentBuyer.getPhone());
        JTextField hostelField = new JTextField(currentBuyer.getHostel());
        JTextField budgetField = new JTextField(currentBuyer.getBudget() + "");

        Object[] message = {
                "Buyer Full Name:", nameField,
                "Phone Number:", phoneField,
                "Hostel / Room:", hostelField,
                "Max Budget (₹):", budgetField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Buyer Login / Update Budget", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText().trim();
                String phone = phoneField.getText().trim();
                String hostel = hostelField.getText().trim();
                double budget = Double.parseDouble(budgetField.getText().trim());

                if (name.isEmpty() || phone.isEmpty() || budget <= 0) throw new Exception();

                currentBuyer = new Buyer(name, phone, hostel, budget);
                txtBudget.setText(budget + "");
                updateBuyerSessionLabel();
                lblStatus.setText("Buyer profile updated: " + currentBuyer.getName());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid information and a positive budget.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // =========================================================================
    // DSA LOGIC & BUYER ACTIONS
    // =========================================================================

    // DSA 1: QuickSort (Ascending or Descending)
    private void handleQuickSort() {
        ArrayList<Item> list = getAvailableItemsList();
        if (list.isEmpty()) {
            lblStatus.setText("No items to sort.");
            return;
        }

        boolean ascending = cmbSortOrder.getSelectedIndex() == 0;
        if (ascending) {
            QuickSort.sort(list, 0, list.size() - 1);
            lblStatus.setText("⚡ QuickSort executed: Items sorted by price (Low to High) in O(N log N)!");
        } else {
            QuickSort.sortDescending(list, 0, list.size() - 1);
            lblStatus.setText("⚡ QuickSort executed: Items sorted by price (High to Low) in O(N log N)!");
        }

        refreshBuyerTable(list);
    }

    // DSA 2: Binary Search (Budget Range)
    private void handleBinarySearchBudget() {
        try {
            double budget = Double.parseDouble(txtBudget.getText().trim());
            currentBuyer.setBudget(budget);
            updateBuyerSessionLabel();

            ArrayList<Item> available = getAvailableItemsList();
            // Step 1: QuickSort ascending
            QuickSort.sort(available, 0, available.size() - 1);

            // Step 2: Binary Search for upper bound
            ArrayList<Item> affordable = BinarySearch.searchByBudget(available, budget);
            refreshBuyerTable(affordable);
            lblStatus.setText(String.format("🔍 Binary Search: Found %d items within ₹%.2f budget in O(log N)!", affordable.size(), budget));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid numeric budget.", "Invalid Budget", JOptionPane.ERROR_MESSAGE);
        }
    }

    // DSA 3: Binary Search by Item ID
    private void handleBinarySearchById() {
        String idStr = txtSearchId.getText().trim();
        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an Item ID to search.", "Missing ID", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int targetId = Integer.parseInt(idStr);

            // Create a copy and sort by ID using QuickSort
            ArrayList<Item> sortedById = new ArrayList<>(items);
            QuickSort.sortById(sortedById, 0, sortedById.size() - 1);

            // Execute Binary Search
            int index = BinarySearch.searchById(sortedById, targetId);

            if (index != -1) {
                Item found = sortedById.get(index);
                lblStatus.setText("🎯 Binary Search: Found Item [" + targetId + "] in O(log N)!");
                showItemDetailsCard(found);
            } else {
                JOptionPane.showMessageDialog(this, "No item found with ID: " + targetId, "Not Found", JOptionPane.INFORMATION_MESSAGE);
                lblStatus.setText("🎯 Binary Search: Item [" + targetId + "] not found.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Item ID must be an integer (e.g. 101).", "Invalid ID", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Category Filter
    private void handleCategoryFilter() {
        String selected = (String) cmbCategory.getSelectedItem();
        if (selected == null || selected.equals("All")) {
            refreshBuyerTable(getAvailableItemsList());
            return;
        }

        ArrayList<Item> filtered = new ArrayList<>();
        for (Item item : items) {
            if (!item.isSold() && item.getCategory().equalsIgnoreCase(selected)) {
                filtered.add(item);
            }
        }
        refreshBuyerTable(filtered);
        lblStatus.setText("Showing category: " + selected + " (" + filtered.size() + " items)");
    }

    // Buy Item
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
                JOptionPane.showMessageDialog(this, "This item is already SOLD.", "Sold Out", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    String.format("Confirm purchase of '%s' for ₹%.2f?\nSeller: %s (%s)\nHostel Delivery: %s",
                            item.getTitle(), item.getPrice(), item.getSellerName(), item.getSellerPhone(), currentBuyer.getHostel()),
                    "Confirm Purchase", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                item.setSold(true);
                DataManager.saveItems(items);
                refreshAllViews();
                lblStatus.setText("🎉 Purchased [" + itemId + "]! Contact seller at " + item.getSellerPhone());
            }
        }
    }

    // =========================================================================
    // SELLER ACTIONS (STRICT AUTHORIZATION CHECKS)
    // =========================================================================
    private void handlePostItem() {
        String title = txtTitle.getText().trim();
        String cat = txtCategory.getText().trim();
        String priceStr = txtPrice.getText().trim();
        String desc = txtDesc.getText().trim();

        if (title.isEmpty() || cat.isEmpty() || priceStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in Title, Category, and Price.", "Incomplete Form", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            double price = Double.parseDouble(priceStr);
            if (price <= 0) throw new NumberFormatException();

            int newId = DataManager.getNextItemId(items);
            // Creator identity is locked to the logged-in seller!
            Item newItem = new Item(newId, title, cat, price, desc, currentSeller.getName(), currentSeller.getPhone());

            items.add(newItem);
            currentSeller.incrementItemsListed();
            DataManager.saveItems(items);

            txtTitle.setText("");
            txtCategory.setText("");
            txtPrice.setText("");
            txtDesc.setText("");

            refreshAllViews();
            lblStatus.setText("🎉 Item [" + newId + "] posted successfully by " + currentSeller.getName() + "!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid price greater than 0.", "Invalid Price", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Security Check 1: Mark as Sold
    private void handleMarkItemAsSold() {
        int row = sellerTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select an item from the table first.", "Select Item", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int itemId = (int) sellerTableModel.getValueAt(row, 0);
        Item item = findItemById(itemId);

        if (item != null) {
            // STRICT SECURITY GUARD: Only the creator can mark as sold!
            if (!item.getSellerName().equalsIgnoreCase(currentSeller.getName())) {
                JOptionPane.showMessageDialog(this,
                        "⛔ ACCESS DENIED!\nYou can only edit your own listings.\nThis item was posted by: " + item.getSellerName(),
                        "Security Warning", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (item.isSold()) {
                JOptionPane.showMessageDialog(this, "Item is already marked as SOLD.", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            item.setSold(true);
            DataManager.saveItems(items);
            refreshAllViews();
            lblStatus.setText("✅ Item [" + itemId + "] has been marked as SOLD.");
        }
    }

    // Security Check 2: Delete Listing
    private void handleDeleteListing() {
        int row = sellerTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select an item to delete.", "Select Item", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int itemId = (int) sellerTableModel.getValueAt(row, 0);
        Item item = findItemById(itemId);

        if (item != null) {
            // STRICT SECURITY GUARD: Only the creator can delete!
            if (!item.getSellerName().equalsIgnoreCase(currentSeller.getName())) {
                JOptionPane.showMessageDialog(this,
                        "⛔ ACCESS DENIED!\nYou can only delete your own listings.\nThis item was posted by: " + item.getSellerName(),
                        "Security Warning", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to permanently delete '" + item.getTitle() + "'?",
                    "Confirm Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                items.remove(item);
                DataManager.saveItems(items);
                refreshAllViews();
                lblStatus.setText("🗑️ Item [" + itemId + "] permanently deleted.");
            }
        }
    }

    // =========================================================================
    // ITEM DETAILS POPUP (DOUBLE CLICK)
    // =========================================================================
    private void addDoubleClickListener(JTable table) {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    showSelectedItemDetails(table);
                }
            }
        });
    }

    private void showSelectedItemDetails(JTable table) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select an item first.", "Select Item", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int itemId = (int) table.getValueAt(row, 0);
        Item item = findItemById(itemId);
        if (item != null) {
            showItemDetailsCard(item);
        }
    }

    private void showItemDetailsCard(Item item) {
        String details = String.format(
                "===========================================\n" +
                        "  🎓 CAMPUS OLX ITEM SPECIFICATIONS\n" +
                        "===========================================\n" +
                        "  Item ID     : %d\n" +
                        "  Title       : %s\n" +
                        "  Category    : %s\n" +
                        "  Price       : ₹%.2f\n" +
                        "  Status      : %s\n" +
                        "-------------------------------------------\n" +
                        "  Description : %s\n" +
                        "-------------------------------------------\n" +
                        "  Seller Name : %s\n" +
                        "  Contact No  : %s\n" +
                        "===========================================",
                item.getId(), item.getTitle(), item.getCategory(), item.getPrice(),
                item.isSold() ? "❌ SOLD" : "✅ AVAILABLE",
                item.getDescription(), item.getSellerName(), item.getSellerPhone()
        );

        JTextArea textArea = new JTextArea(details);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setEditable(false);
        textArea.setBackground(new Color(248, 248, 248));

        JOptionPane.showMessageDialog(this, new JScrollPane(textArea), "Item Details: " + item.getTitle(), JOptionPane.PLAIN_MESSAGE);
    }

    // OOP Polymorphism Dialog
    private void showProfileDialog(User user) {
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

    // =========================================================================
    // REFRESH & HELPER METHODS
    // =========================================================================
    private void refreshAllViews() {
        refreshBuyerTable(getAvailableItemsList());
        refreshSellerTable();
        updateBuyerSessionLabel();
        updateSellerSessionLabel();
    }

    private void refreshBuyerTable(ArrayList<Item> list) {
        buyerTableModel.setRowCount(0);
        for (Item item : list) {
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
        boolean myListingsOnly = chkMyListingsOnly != null && chkMyListingsOnly.isSelected();

        for (Item item : items) {
            if (!myListingsOnly || item.getSellerName().equalsIgnoreCase(currentSeller.getName())) {
                sellerTableModel.addRow(new Object[]{
                        item.getId(), item.getCategory(), item.getTitle(),
                        String.format("%.2f", item.getPrice()),
                        item.getDescription(), item.getSellerName(),
                        item.getSellerPhone(), item.isSold() ? "❌ SOLD" : "✅ AVAILABLE"
                });
            }
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

    private int getSellerItemsCount(String sellerName) {
        int count = 0;
        for (Item item : items) {
            if (item.getSellerName().equalsIgnoreCase(sellerName)) {
                count++;
            }
        }
        return count;
    }

    private Item findItemById(int id) {
        for (Item item : items) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CampusOLXGUI().setVisible(true));
    }
}
