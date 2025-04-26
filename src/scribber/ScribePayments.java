package scribber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ScribePayments extends JFrame {
    private JTabbedPane tabbedPane;
    private String adminUsername;

    public ScribePayments(String adminUsername) {
        this.adminUsername = adminUsername;

        setTitle("Scribe Payments");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        tabbedPane = new JTabbedPane();
        loadScribeBankDetails();

        add(tabbedPane);
        setVisible(true);
    }

    private void loadScribeBankDetails() {
        try {
            Conn conn = new Conn();
            Connection connection = conn.getConnection();
            String query = "SELECT username, bank_name, account_holder_name, account_number, ifsc_code FROM scribe_bank_details";
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String username = rs.getString("username");
                String bankName = rs.getString("bank_name");
                String accountHolderName = rs.getString("account_holder_name");
                String accountNumber = rs.getString("account_number");
                String ifscCode = rs.getString("ifsc_code");

                JPanel panel = createPaymentPanel(username, bankName, accountHolderName, accountNumber, ifscCode);
                tabbedPane.addTab(username, panel);
            }

            if (tabbedPane.getTabCount() == 0) {
                JOptionPane.showMessageDialog(this, "No scribe bank details available.", "Info", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }

            rs.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading scribe bank details!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel createPaymentPanel(String username, String bankName, String accountHolderName, String accountNumber, String ifscCode) {
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        addLabelAndField(panel, "Bank Name:", bankName);
        addLabelAndField(panel, "Account Holder Name:", accountHolderName);
        addLabelAndField(panel, "Account Number:", accountNumber);
        addLabelAndField(panel, "IFSC Code:", ifscCode);

        JLabel totalAmountLabel = new JLabel("Total Amount:");
        JTextField totalAmountField = new JTextField();
        panel.add(totalAmountLabel);
        panel.add(totalAmountField);

        JButton submitButton = new JButton("Submit Payment");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String totalAmount = totalAmountField.getText();
                if (totalAmount.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Please enter the amount.", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    processPayment(username, bankName, accountHolderName, accountNumber, ifscCode, totalAmount);
                }
            }
        });

        panel.add(new JLabel());  // Empty space
        panel.add(submitButton);

        return panel;
    }

    private void addLabelAndField(JPanel panel, String labelText, String fieldValue) {
        JLabel label = new JLabel(labelText);
        JTextField field = new JTextField(fieldValue);
        field.setEditable(false);
        panel.add(label);
        panel.add(field);
    }

    private void processPayment(String username, String bankName, String accountHolderName, String accountNumber, String ifscCode, String totalAmount) {
        try {
            Conn conn = new Conn();
            Connection connection = conn.getConnection();
            String query = "INSERT INTO scribe_payment (username, bank_name, account_holder_name, account_number, ifsc_code, total_amount) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, username);
            pst.setString(2, bankName);
            pst.setString(3, accountHolderName);
            pst.setString(4, accountNumber);
            pst.setString(5, ifscCode);
            pst.setBigDecimal(6, new java.math.BigDecimal(totalAmount));

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Amount paid successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Payment failed. Try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error processing payment!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ScribePayments("admin123"));
    }
}
