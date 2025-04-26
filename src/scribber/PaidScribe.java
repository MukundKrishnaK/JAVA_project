package scribber;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class PaidScribe extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;

    public PaidScribe(String adminUsername) {
        setTitle("Paid Scribe Details");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Table model with column names
        String[] columnNames = {"Username", "Bank Name", "Account Holder", "Account Number", "IFSC Code", "Total Amount", "Payment Date"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setEnabled(false);

        // Load data into the table
        loadPaidScribeDetails();

        // Adding table to scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void loadPaidScribeDetails() {
        try {
            Conn conn = new Conn();
            Connection connection = conn.getConnection();
            String query = "SELECT username, bank_name, account_holder_name, account_number, ifsc_code, total_amount, payment_date FROM scribe_payment";
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String username = rs.getString("username");
                String bankName = rs.getString("bank_name");
                String accountHolder = rs.getString("account_holder_name");
                String accountNumber = rs.getString("account_number");
                String ifscCode = rs.getString("ifsc_code");
                String totalAmount = rs.getString("total_amount");
                String paymentDate = rs.getString("payment_date");

                tableModel.addRow(new Object[]{username, bankName, accountHolder, accountNumber, ifscCode, totalAmount, paymentDate});
            }

            rs.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading payment details!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PaidScribe("admin123"));
    }
}
