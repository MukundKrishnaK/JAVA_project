package scribber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Payments extends JFrame {
    private String username;
    private JTextField cardholderNameField, cardNumberField, cvvField, amountField;
    private JFormattedTextField expirationDateField;
    private JButton submitButton;
    
    public Payments(String username) {
        this.username = username;

        setTitle("Payment Portal");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(7, 2, 10, 10));

        add(new JLabel("Cardholder Name:"));
        cardholderNameField = new JTextField();
        cardholderNameField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (Character.isDigit(e.getKeyChar())) {
                    e.consume();
                }
            }
        });
        add(cardholderNameField);

        add(new JLabel("Card Number:"));
        cardNumberField = new JTextField();
        cardNumberField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) || cardNumberField.getText().length() >= 16) {
                    e.consume();
                }
            }
        });
        add(cardNumberField);

        add(new JLabel("Expiration Date (YYYY-MM-DD):"));
        expirationDateField = new JFormattedTextField();
        add(expirationDateField);

        add(new JLabel("CVV:"));
        cvvField = new JTextField();
        cvvField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) || cvvField.getText().length() >= 3) {
                    e.consume();
                }
            }
        });
        add(cvvField);

        add(new JLabel("Amount:"));
        amountField = new JTextField();
        amountField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar())) {
                    e.consume();
                }
            }
        });
        add(amountField);

        submitButton = new JButton("Submit Payment");
        submitButton.addActionListener(e -> processPayment());
        add(new JLabel(""));
        add(submitButton);
    }

    private void processPayment() {
        String cardholderName = cardholderNameField.getText().trim();
        String cardNumber = cardNumberField.getText().trim();
        String expirationDate = expirationDateField.getText().trim();
        String cvv = cvvField.getText().trim();
        String amount = amountField.getText().trim();

        if (cardholderName.isEmpty() || cardNumber.length() != 16 || expirationDate.isEmpty() || 
            cvv.length() != 3 || amount.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter all fields correctly.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Conn conn = new Conn();
            Connection connection = conn.getConnection();

            // Retrieve student details
            String studentQuery = "SELECT first_name, last_name FROM students WHERE username = ?";
            PreparedStatement studentStmt = connection.prepareStatement(studentQuery);
            studentStmt.setString(1, username);
            ResultSet rs = studentStmt.executeQuery();

            if (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");

                // Insert payment details into studentpayments table
                String insertQuery = "INSERT INTO studentpayments (username, first_name, last_name, cardholder_name, card_number, expiration_date, cvv, amount) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement insertStmt = connection.prepareStatement(insertQuery);
                insertStmt.setString(1, username);
                insertStmt.setString(2, firstName);
                insertStmt.setString(3, lastName);
                insertStmt.setString(4, cardholderName);
                insertStmt.setString(5, cardNumber);
                insertStmt.setString(6, expirationDate);
                insertStmt.setString(7, cvv);
                insertStmt.setString(8, amount);

                int rowsInserted = insertStmt.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "Payment made successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    this.dispose();
                }

                insertStmt.close();
            } else {
                JOptionPane.showMessageDialog(this, "User not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }

            rs.close();
            studentStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error processing payment.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Payments("testUser").setVisible(true));
    }
}
