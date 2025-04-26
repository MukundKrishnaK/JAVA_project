package scribber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class BankDetails extends JFrame {
    private String username;
    private JTextField txtName, txtBankName, txtAccountNumber, txtIFSC;
    private JButton btnSubmit;

    public BankDetails(String username) {
        this.username = username;

        setTitle("Scribe - Bank Details");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6, 2, 10, 10));

        JLabel lblName = new JLabel("Account Holder Name:");
        txtName = new JTextField();
        disableNumericKeys(txtName);

        JLabel lblBankName = new JLabel("Bank Name:");
        txtBankName = new JTextField();
        disableNumericKeys(txtBankName);

        JLabel lblAccountNumber = new JLabel("Bank Account Number:");
        txtAccountNumber = new JTextField();
        disableAlphabetKeys(txtAccountNumber, 12);

        JLabel lblIFSC = new JLabel("IFSC Code:");
        txtIFSC = new JTextField();
        limitIFSCInput(txtIFSC, 11);

        btnSubmit = new JButton("Submit");
        btnSubmit.addActionListener(e -> saveBankDetails());

        add(lblName);
        add(txtName);
        add(lblBankName);
        add(txtBankName);
        add(lblAccountNumber);
        add(txtAccountNumber);
        add(lblIFSC);
        add(txtIFSC);
        add(new JLabel()); // Empty space
        add(btnSubmit);

        setVisible(true);
    }

    private void disableNumericKeys(JTextField field) {
        field.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (Character.isDigit(c)) {
                    e.consume();
                }
            }
        });
    }

    private void disableAlphabetKeys(JTextField field, int maxLength) {
        field.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) || field.getText().length() >= maxLength) {
                    e.consume();
                }
            }
        });
    }

    private void limitIFSCInput(JTextField field, int maxLength) {
        field.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (field.getText().length() >= maxLength) {
                    e.consume();
                }
            }
        });
    }

    private void saveBankDetails() {
        String name = txtName.getText().trim();
        String bankName = txtBankName.getText().trim();
        String accountNumber = txtAccountNumber.getText().trim();
        String ifscCode = txtIFSC.getText().trim();

        if (name.isEmpty() || bankName.isEmpty() || accountNumber.isEmpty() || ifscCode.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (accountNumber.length() != 12) {
            JOptionPane.showMessageDialog(this, "Account Number must be 12 digits!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (ifscCode.length() != 11) {
            JOptionPane.showMessageDialog(this, "IFSC Code must be exactly 11 characters!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Conn conn = new Conn();
            Connection connection = conn.getConnection();

            // Fetch first_name & last_name from `scribe` table
            String fetchQuery = "SELECT first_name, last_name FROM scribe WHERE username=?";
            PreparedStatement fetchPst = connection.prepareStatement(fetchQuery);
            fetchPst.setString(1, username);
            ResultSet rs = fetchPst.executeQuery();

            if (!rs.next()) {
                JOptionPane.showMessageDialog(this, "User not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");

            // Insert into `scribe_bank_details`
            String insertQuery = "INSERT INTO scribe_bank_details (username, first_name, last_name, bank_name, account_holder_name, account_number, ifsc_code) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement insertPst = connection.prepareStatement(insertQuery);
            insertPst.setString(1, username);
            insertPst.setString(2, firstName);
            insertPst.setString(3, lastName);
            insertPst.setString(4, bankName);
            insertPst.setString(5, name);
            insertPst.setString(6, accountNumber);
            insertPst.setString(7, ifscCode);

            int rowsAffected = insertPst.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Bank details saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error saving details!", "Error", JOptionPane.ERROR_MESSAGE);
            }

            rs.close();
            fetchPst.close();
            insertPst.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BankDetails("scribeUser"));
    }
}
