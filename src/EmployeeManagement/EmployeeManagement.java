package EmployeeManagement;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/*
 * Professional Employee Management System
 * GUI: AWT/Swing
 * Database: JDBC
 *
 * Make sure your existing DBConnection.java provides:
 *
 * public static Connection getConnection()
 *
 */

public class EmployeeManagement extends JFrame {

    private static final long serialVersionUID = 1L;

    // ================= COLORS =================

    private final Color DARK_BLUE = new Color(18, 32, 58);
    private final Color BLUE = new Color(37, 99, 235);
    private final Color LIGHT_BLUE = new Color(59, 130, 246);
    private final Color BACKGROUND = new Color(241, 245, 249);
    private final Color WHITE = Color.WHITE;
    private final Color TEXT = new Color(30, 41, 59);
    private final Color SUCCESS = new Color(16, 185, 129);
    private final Color DANGER = new Color(239, 68, 68);
    private final Color WARNING = new Color(245, 158, 11);

    // ================= COMPONENTS =================

    private JPanel contentPanel;
    private JLabel titleLabel;
    private JLabel statusLabel;

    private Timer animationTimer;
    private int animationX = 0;

    // ================= CONSTRUCTOR =================

    public EmployeeManagement() {

        setTitle("Employee Management System");
        setSize(1200, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        createGUI();
        startHeaderAnimation();
    }

    // ================= CREATE GUI =================

    private void createGUI() {

        // ---------------- HEADER ----------------

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(DARK_BLUE);
        header.setPreferredSize(new Dimension(1200, 100));
        header.setBorder(new EmptyBorder(15, 25, 15, 25));

        titleLabel = new JLabel("EMPLOYEE MANAGEMENT SYSTEM");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));

        JLabel subtitle = new JLabel("Professional Employee Database Management");
        subtitle.setForeground(new Color(191, 219, 254));
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);

        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(subtitle);

        header.add(titlePanel, BorderLayout.WEST);

        JLabel logo = new JLabel("  EMS  ");
        logo.setForeground(Color.WHITE);
        logo.setBackground(BLUE);
        logo.setOpaque(true);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        logo.setBorder(new EmptyBorder(10, 15, 10, 15));

        header.add(logo, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        // ---------------- SIDEBAR ----------------

        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(30, 41, 59));
        sidebar.setPreferredSize(new Dimension(230, 550));
        sidebar.setLayout(new GridLayout(11, 1, 8, 8));
        sidebar.setBorder(new EmptyBorder(20, 12, 20, 12));

        sidebar.add(createMenuButton("Dashboard", e -> showDashboard()));

        sidebar.add(createMenuButton("Add Employee", e -> addEmployee()));

        sidebar.add(createMenuButton("View Employees", e -> viewAllEmployees()));

        sidebar.add(createMenuButton("Search Employee", e -> searchEmployee()));

        sidebar.add(createMenuButton("Department Search", e -> searchByDepartment()));

        sidebar.add(createMenuButton("Add Department", e -> addDepartment()));

        sidebar.add(createMenuButton("Update Employee", e -> updateEmployee()));

        sidebar.add(createMenuButton("Update Salary", e -> updateSalary()));

        sidebar.add(createMenuButton("Delete Employee", e -> deleteEmployee()));

        sidebar.add(createMenuButton("Salary Search", e -> viewEmployeesBySalary()));

        sidebar.add(createMenuButton("Exit", e -> System.exit(0)));

        add(sidebar, BorderLayout.WEST);

        // ---------------- CONTENT ----------------

        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(BACKGROUND);
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        add(contentPanel, BorderLayout.CENTER);

        // ---------------- FOOTER ----------------

        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(DARK_BLUE);
        footer.setPreferredSize(new Dimension(1200, 35));

        statusLabel = new JLabel("  Ready");
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JLabel copyright = new JLabel("Employee Management System  |  JDBC + AWT/Swing  ");
        copyright.setForeground(new Color(203, 213, 225));

        footer.add(statusLabel, BorderLayout.WEST);
        footer.add(copyright, BorderLayout.EAST);

        add(footer, BorderLayout.SOUTH);

        showDashboard();
    }

    // ================= ANIMATION =================

    private void startHeaderAnimation() {

        animationTimer = new Timer(80, e -> {

            animationX += 2;

            if (animationX > 300) {
                animationX = 0;
            }

            titleLabel.setForeground(
                    new Color(
                            100 + (animationX % 100),
                            180 + (animationX % 50),
                            255
                    )
            );

            titleLabel.repaint();
        });

        animationTimer.start();
    }

    // ================= MENU BUTTON =================

    private JButton createMenuButton(String text, ActionListener action) {

        JButton button = new JButton(text);

        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(51, 65, 85));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addActionListener(action);

        button.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(BLUE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(51, 65, 85));
            }
        });

        return button;
    }

    // ================= DASHBOARD =================

    private void showDashboard() {

        contentPanel.removeAll();

        JPanel dashboard = new JPanel(new BorderLayout());
        dashboard.setBackground(BACKGROUND);

        JLabel heading = new JLabel("Dashboard");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 30));
        heading.setForeground(TEXT);

        JLabel description = new JLabel(
                "Manage employees, departments, salaries and database records."
        );

        description.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        description.setForeground(new Color(100, 116, 139));

        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setOpaque(false);

        top.add(heading);
        top.add(Box.createVerticalStrut(5));
        top.add(description);

        dashboard.add(top, BorderLayout.NORTH);

        JPanel cards = new JPanel(new GridLayout(1, 3, 20, 20));
        cards.setOpaque(false);
        cards.setBorder(new EmptyBorder(40, 0, 20, 0));

        cards.add(createDashboardCard(
                "Total Employees",
                getEmployeeCount(),
                BLUE
        ));

        cards.add(createDashboardCard(
                "Departments",
                getDepartmentCount(),
                SUCCESS
        ));

        cards.add(createDashboardCard(
                "System Status",
                "ONLINE",
                WARNING
        ));

        dashboard.add(cards, BorderLayout.CENTER);

        contentPanel.add(dashboard);

        contentPanel.revalidate();
        contentPanel.repaint();

        statusLabel.setText("  Dashboard loaded");
    }

    // ================= DASHBOARD CARD =================

    private JPanel createDashboardCard(
            String title,
            String value,
            Color color) {

        JPanel card = new JPanel(new BorderLayout());

        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(226, 232, 240)
                ),
                new EmptyBorder(25, 25, 25, 25)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(new Color(71, 85, 105));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 34));
        valueLabel.setForeground(color);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    // ================= ADD EMPLOYEE =================

    private void addEmployee() {

        JPanel panel = createFormPanel();

        JTextField name = new JTextField();
        JTextField email = new JTextField();
        JTextField phone = new JTextField();
        JTextField salary = new JTextField();
        JTextField departmentId = new JTextField();

        addFormField(panel, "Employee Name", name);
        addFormField(panel, "Email", email);
        addFormField(panel, "Phone", phone);
        addFormField(panel, "Salary", salary);
        addFormField(panel, "Department ID", departmentId);

        JButton save = createActionButton(
                "Add Employee",
                SUCCESS
        );

        save.addActionListener(e -> {

            try {

                String sql =
                        "INSERT INTO Employee " +
                        "(name, email, phone, salary, department_id) " +
                        "VALUES (?, ?, ?, ?, ?)";

                try (Connection con = DBConnection.getConnection();
                     PreparedStatement ps = con.prepareStatement(sql)) {

                    ps.setString(1, name.getText());
                    ps.setString(2, email.getText());
                    ps.setString(3, phone.getText());
                    ps.setDouble(4, Double.parseDouble(salary.getText()));
                    ps.setInt(5, Integer.parseInt(departmentId.getText()));

                    ps.executeUpdate();

                    showMessage(
                            "Employee added successfully!",
                            SUCCESS
                    );

                    clearFields(name, email, phone, salary, departmentId);

                    statusLabel.setText("  Employee added successfully");
                }

            } catch (Exception ex) {

                showMessage(
                        "Error: " + ex.getMessage(),
                        DANGER
                );
            }
        });

        panel.add(save);

        displayForm("Add New Employee", panel);
    }

    // ================= VIEW EMPLOYEES =================

    private void viewAllEmployees() {

        String sql =
                "SELECT e.employee_id, e.name, e.email, " +
                "e.phone, e.salary, d.department_name, e.status " +
                "FROM Employee e " +
                "LEFT JOIN Department d " +
                "ON e.department_id = d.department_id";

        showEmployeeTable(sql, null);
    }

    // ================= SEARCH EMPLOYEE =================

    private void searchEmployee() {

        String id = JOptionPane.showInputDialog(
                this,
                "Enter Employee ID:",
                "Search Employee",
                JOptionPane.QUESTION_MESSAGE
        );

        if (id == null || id.trim().isEmpty()) {
            return;
        }

        String sql =
                "SELECT e.employee_id, e.name, e.email, " +
                "e.phone, e.salary, d.department_name, e.status " +
                "FROM Employee e " +
                "LEFT JOIN Department d " +
                "ON e.department_id = d.department_id " +
                "WHERE e.employee_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, Integer.parseInt(id));

            showEmployeeTable(sql, Integer.parseInt(id));

        } catch (Exception ex) {

            showMessage(
                    "Error: " + ex.getMessage(),
                    DANGER
            );
        }
    }

    // ================= SEARCH DEPARTMENT =================

    private void searchByDepartment() {

        String department = JOptionPane.showInputDialog(
                this,
                "Enter Department Name:",
                "Department Search",
                JOptionPane.QUESTION_MESSAGE
        );

        if (department == null || department.trim().isEmpty()) {
            return;
        }

        String sql =
                "SELECT e.employee_id, e.name, e.email, " +
                "e.phone, e.salary, d.department_name, e.status " +
                "FROM Employee e " +
                "JOIN Department d " +
                "ON e.department_id = d.department_id " +
                "WHERE d.department_name = ?";

        showEmployeeTable(sql, department);
    }

    // ================= EMPLOYEE TABLE =================

    private void showEmployeeTable(String sql, Object parameter) {

        contentPanel.removeAll();

        String[] columns = {
                "ID",
                "Name",
                "Email",
                "Phone",
                "Salary",
                "Department",
                "Status"
        };

        DefaultTableModel model =
                new DefaultTableModel(columns, 0) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column) {
                        return false;
                    }
                };

        JTable table = new JTable(model);

        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(30);
        table.setSelectionBackground(
                new Color(219, 234, 254)
        );
        table.setSelectionForeground(TEXT);

        table.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 14)
        );

        table.getTableHeader().setBackground(DARK_BLUE);
        table.getTableHeader().setForeground(Color.WHITE);

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (parameter != null) {

                if (parameter instanceof Integer) {
                    ps.setInt(1, (Integer) parameter);
                } else {
                    ps.setString(1, parameter.toString());
                }
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                model.addRow(new Object[]{
                        rs.getInt("employee_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getDouble("salary"),
                        rs.getString("department_name"),
                        rs.getString("status")
                });
            }

        } catch (Exception ex) {

            showMessage(
                    "Error: " + ex.getMessage(),
                    DANGER
            );
        }

        JScrollPane scrollPane = new JScrollPane(table);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND);

        JLabel heading =
                createHeading("Employee Records");

        panel.add(heading, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        contentPanel.add(panel);

        contentPanel.revalidate();
        contentPanel.repaint();

        statusLabel.setText(
                "  Employee records loaded"
        );
    }

    // ================= ADD DEPARTMENT =================

    private void addDepartment() {

        JPanel panel = createFormPanel();

        JTextField name = new JTextField();
        JTextField location = new JTextField();

        addFormField(panel, "Department Name", name);
        addFormField(panel, "Location", location);

        JButton save = createActionButton(
                "Add Department",
                SUCCESS
        );

        save.addActionListener(e -> {

            String sql =
                    "INSERT INTO Department " +
                    "(department_name, location) " +
                    "VALUES (?, ?)";

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps =
                         con.prepareStatement(sql)) {

                ps.setString(1, name.getText());
                ps.setString(2, location.getText());

                ps.executeUpdate();

                showMessage(
                        "Department added successfully!",
                        SUCCESS
                );

                clearFields(name, location);

            } catch (Exception ex) {

                showMessage(
                        "Error: " + ex.getMessage(),
                        DANGER
                );
            }
        });

        panel.add(save);

        displayForm("Add Department", panel);
    }

    // ================= UPDATE EMPLOYEE =================

    private void updateEmployee() {

        JPanel panel = createFormPanel();

        JTextField id = new JTextField();
        JTextField name = new JTextField();
        JTextField email = new JTextField();
        JTextField phone = new JTextField();

        addFormField(panel, "Employee ID", id);
        addFormField(panel, "New Name", name);
        addFormField(panel, "New Email", email);
        addFormField(panel, "New Phone", phone);

        JButton update = createActionButton(
                "Update Employee",
                BLUE
        );

        update.addActionListener(e -> {

            String sql =
                    "UPDATE Employee SET " +
                    "name = ?, email = ?, phone = ? " +
                    "WHERE employee_id = ?";

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps =
                         con.prepareStatement(sql)) {

                ps.setString(1, name.getText());
                ps.setString(2, email.getText());
                ps.setString(3, phone.getText());
                ps.setInt(4, Integer.parseInt(id.getText()));

                int result = ps.executeUpdate();

                if (result > 0) {

                    showMessage(
                            "Employee updated successfully!",
                            SUCCESS
                    );

                } else {

                    showMessage(
                            "Employee not found.",
                            WARNING
                    );
                }

            } catch (Exception ex) {

                showMessage(
                        "Error: " + ex.getMessage(),
                        DANGER
                );
            }
        });

        panel.add(update);

        displayForm("Update Employee Information", panel);
    }

    // ================= UPDATE SALARY =================

    private void updateSalary() {

        JPanel panel = createFormPanel();

        JTextField id = new JTextField();
        JTextField salary = new JTextField();

        addFormField(panel, "Employee ID", id);
        addFormField(panel, "New Salary", salary);

        JButton update = createActionButton(
                "Update Salary",
                BLUE
        );

        update.addActionListener(e -> {

            String sql =
                    "UPDATE Employee SET salary = ? " +
                    "WHERE employee_id = ?";

            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps =
                         con.prepareStatement(sql)) {

                ps.setDouble(
                        1,
                        Double.parseDouble(salary.getText())
                );

                ps.setInt(
                        2,
                        Integer.parseInt(id.getText())
                );

                int result = ps.executeUpdate();

                if (result > 0) {

                    showMessage(
                            "Salary updated successfully!",
                            SUCCESS
                    );

                } else {

                    showMessage(
                            "Employee not found.",
                            WARNING
                    );
                }

            } catch (Exception ex) {

                showMessage(
                        "Error: " + ex.getMessage(),
                        DANGER
                );
            }
        });

        panel.add(update);

        displayForm("Update Employee Salary", panel);
    }

    // ================= DELETE EMPLOYEE =================

    private void deleteEmployee() {

        String id = JOptionPane.showInputDialog(
                this,
                "Enter Employee ID to delete:",
                "Delete Employee",
                JOptionPane.WARNING_MESSAGE
        );

        if (id == null || id.trim().isEmpty()) {
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete employee " + id + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmation != JOptionPane.YES_OPTION) {
            return;
        }

        String sql =
                "DELETE FROM Employee " +
                "WHERE employee_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql)) {

            ps.setInt(1, Integer.parseInt(id));

            int result = ps.executeUpdate();

            if (result > 0) {

                showMessage(
                        "Employee deleted successfully!",
                        SUCCESS
                );

            } else {

                showMessage(
                        "Employee not found.",
                        WARNING
                );
            }

        } catch (Exception ex) {

            showMessage(
                    "Error: " + ex.getMessage(),
                    DANGER
            );
        }
    }

    // ================= SALARY SEARCH =================

    private void viewEmployeesBySalary() {

        String salary = JOptionPane.showInputDialog(
                this,
                "Enter minimum salary:",
                "Salary Search",
                JOptionPane.QUESTION_MESSAGE
        );

        if (salary == null || salary.trim().isEmpty()) {
            return;
        }

        String sql =
                "SELECT e.employee_id, e.name, e.email, " +
                "e.phone, e.salary, d.department_name, e.status " +
                "FROM Employee e " +
                "LEFT JOIN Department d " +
                "ON e.department_id = d.department_id " +
                "WHERE e.salary > ?";

        showEmployeeTable(sql, Double.parseDouble(salary));
    }

    // ================= FORM PANEL =================

    private JPanel createFormPanel() {

        JPanel panel = new JPanel();

        panel.setLayout(
                new BoxLayout(panel, BoxLayout.Y_AXIS)
        );

        panel.setBackground(Color.WHITE);

        panel.setBorder(
                new EmptyBorder(30, 40, 30, 40)
        );

        return panel;
    }

    // ================= ADD FORM FIELD =================

    private void addFormField(
            JPanel panel,
            String label,
            JTextField field) {

        JLabel lbl = new JLabel(label);

        lbl.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        lbl.setForeground(TEXT);

        field.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        40
                )
        );

        field.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        panel.add(lbl);
        panel.add(Box.createVerticalStrut(5));
        panel.add(field);
        panel.add(Box.createVerticalStrut(15));
    }

    // ================= DISPLAY FORM =================

    private void displayForm(
            String title,
            JPanel form) {

        contentPanel.removeAll();

        JPanel container =
                new JPanel(new BorderLayout());

        container.setBackground(BACKGROUND);

        JLabel heading = createHeading(title);

        container.add(
                heading,
                BorderLayout.NORTH
        );

        container.add(
                form,
                BorderLayout.CENTER
        );

        contentPanel.add(container);

        contentPanel.revalidate();
        contentPanel.repaint();

        statusLabel.setText(
                "  " + title
        );
    }

    // ================= HEADING =================

    private JLabel createHeading(String text) {

        JLabel heading = new JLabel(text);

        heading.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        27
                )
        );

        heading.setForeground(TEXT);

        heading.setBorder(
                new EmptyBorder(
                        0,
                        0,
                        20,
                        0
                )
        );

        return heading;
    }

    // ================= ACTION BUTTON =================

    private JButton createActionButton(
            String text,
            Color color) {

        JButton button = new JButton(text);

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        button.setForeground(Color.WHITE);
        button.setBackground(color);

        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        button.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        button.setMaximumSize(
                new Dimension(220, 45)
        );

        button.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            MouseEvent e) {

                        button.setBackground(
                                color.brighter()
                        );
                    }

                    @Override
                    public void mouseExited(
                            MouseEvent e) {

                        button.setBackground(color);
                    }
                }
        );

        return button;
    }

    // ================= EMPLOYEE COUNT =================

    private String getEmployeeCount() {

        String sql =
                "SELECT COUNT(*) AS total " +
                "FROM Employee";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return String.valueOf(
                        rs.getInt("total")
                );
            }

        } catch (Exception e) {
            return "0";
        }

        return "0";
    }

    // ================= DEPARTMENT COUNT =================

    private String getDepartmentCount() {

        String sql =
                "SELECT COUNT(*) AS total " +
                "FROM Department";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return String.valueOf(
                        rs.getInt("total")
                );
            }

        } catch (Exception e) {
            return "0";
        }

        return "0";
    }

    // ================= CLEAR FIELDS =================

    private void clearFields(
            JTextField... fields) {

        for (JTextField field : fields) {
            field.setText("");
        }
    }

    // ================= MESSAGE =================

    private void showMessage(
            String message,
            Color color) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Employee Management System",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // ================= MAIN =================

    public static void main(String[] args) {

        /*
         * Modern Java Swing Look & Feel
         */

        try {

            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName()
            );

        } catch (Exception e) {

            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {

            EmployeeManagement app =
                    new EmployeeManagement();

            app.setVisible(true);
        });
    }
}