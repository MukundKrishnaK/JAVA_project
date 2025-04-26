package scribber;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class AdminPayments extends JFrame {
    private String username;
    private JTable table;
    private DefaultTableModel model;

    public AdminPayments(String username) {
        this.username = username;

        setTitle("Admin - Payments Overview");
        setSize(700, 400);
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table Column Headers
        String[] columnNames = {"First Name", "Last Name", "Cardholder Name", "Amount Paid", "Payment Date"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // Fetch Payment Data
        fetchPaymentDetails();

        // Title Label
        JLabel lblTitle = new JLabel("Student Payment Records", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));

        // Add Components to Frame
        add(lblTitle, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void fetchPaymentDetails() {
        try {
            Conn conn = new Conn();
            Connection connection = conn.getConnection();

            // Query to retrieve all payments
            String query = "SELECT first_name, last_name, cardholder_name, amount, payment_date FROM studentpayments";
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            // Populate table with data
            while (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String cardholderName = rs.getString("cardholder_name");
                double amountPaid = rs.getDouble("amount");
                String paymentDate = rs.getTimestamp("payment_date").toString();

                model.addRow(new Object[]{firstName, lastName, cardholderName, "₹" + amountPaid, paymentDate});
            }

            rs.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error retrieving payment records!", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminPayments("adminUser"));
    }
}
