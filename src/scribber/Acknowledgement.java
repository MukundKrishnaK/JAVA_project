package scribber;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Acknowledgement extends JFrame {
    private String username; // Currently logged-in user

    private JTextField tfUsername, tfFirstName, tfLastName, tfAmount, tfPaymentTime;

    public Acknowledgement(String username) {
        this.username = username;

        setTitle("Payment Acknowledgement");
        setSize(500, 350);
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Spacing

        // UI Components
        JLabel lblTitle = new JLabel("Payment Acknowledgement");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel lblUsername = new JLabel("Username:");
        JLabel lblFirstName = new JLabel("First Name:");
        JLabel lblLastName = new JLabel("Last Name:");
        JLabel lblAmount = new JLabel("Amount Paid:");
        JLabel lblPaymentTime = new JLabel("Payment Time:");

        tfUsername = new JTextField(20);
        tfFirstName = new JTextField(20);
        tfLastName = new JTextField(20);
        tfAmount = new JTextField(20);
        tfPaymentTime = new JTextField(20);

        // Make fields non-editable
        tfUsername.setEditable(false);
        tfFirstName.setEditable(false);
        tfLastName.setEditable(false);
        tfAmount.setEditable(false);
        tfPaymentTime.setEditable(false);

        // Layout Configuration
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(lblTitle, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        add(lblUsername, gbc);
        gbc.gridx = 1;
        add(tfUsername, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(lblFirstName, gbc);
        gbc.gridx = 1;
        add(tfFirstName, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(lblLastName, gbc);
        gbc.gridx = 1;
        add(tfLastName, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(lblAmount, gbc);
        gbc.gridx = 1;
        add(tfAmount, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(lblPaymentTime, gbc);
        gbc.gridx = 1;
        add(tfPaymentTime, gbc);

        // Note Label
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JLabel lblNote = new JLabel("<html><br><b>Note:</b> Please be at the exam center at 9:00 AM on the day of the examination.</html>");
        lblNote.setForeground(Color.RED);
        add(lblNote, gbc);

        // Fetch and display payment details
        fetchPaymentDetails();
    }

    private void fetchPaymentDetails() {
        try {
            Conn conn = new Conn();
            Connection connection = conn.getConnection();

            // Fetch latest payment details for the user
            String query = "SELECT first_name, last_name, amount, payment_date FROM studentpayments WHERE username = ? ORDER BY payment_date DESC LIMIT 1";
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                // Debugging: Print values to console
                System.out.println("Payment Details Retrieved:");
                System.out.println("First Name: " + rs.getString("first_name"));
                System.out.println("Last Name: " + rs.getString("last_name"));
                System.out.println("Amount Paid: " + rs.getDouble("amount"));
                System.out.println("Payment Time: " + rs.getTimestamp("payment_date"));

                // Update UI fields
                tfUsername.setText(username);
                tfFirstName.setText(rs.getString("first_name"));
                tfLastName.setText(rs.getString("last_name"));
                tfAmount.setText("₹" + rs.getDouble("amount"));
                tfPaymentTime.setText(rs.getTimestamp("payment_date").toString());
            } else {
                JOptionPane.showMessageDialog(this, "No payment record found!", "Error", JOptionPane.ERROR_MESSAGE);
                dispose(); // Close window if no record found
            }

            rs.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error retrieving payment details!", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Acknowledgement("testUser").setVisible(true));
    }
}
