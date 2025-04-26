package scribber;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ScribeBankDetails extends JFrame {
    private JTabbedPane tabbedPane;
    private String adminUsername; // Admin username

    public ScribeBankDetails(String adminUsername) {
        this.adminUsername = adminUsername;

        setTitle("Scribe - Bank Details");
        setSize(500, 350);
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
            String query = "SELECT username, first_name, last_name, bank_name, account_holder_name, account_number, ifsc_code FROM scribe_bank_details";
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String username = rs.getString("username");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String bankName = rs.getString("bank_name");
                String accountHolderName = rs.getString("account_holder_name");
                String accountNumber = rs.getString("account_number");
                String ifscCode = rs.getString("ifsc_code");

                JPanel panel = createDetailsPanel(username, firstName, lastName, bankName, accountHolderName, accountNumber, ifscCode);
                tabbedPane.addTab(username, panel);
            }

            if (tabbedPane.getTabCount() == 0) {
                JOptionPane.showMessageDialog(this, "No bank details available.", "Info", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }

            rs.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading bank details!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel createDetailsPanel(String username, String firstName, String lastName, String bankName, String accountHolderName, String accountNumber, String ifscCode) {
        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        addLabelAndField(panel, "Username:", username);
        addLabelAndField(panel, "First Name:", firstName);
        addLabelAndField(panel, "Last Name:", lastName);
        addLabelAndField(panel, "Bank Name:", bankName);
        addLabelAndField(panel, "Account Holder Name:", accountHolderName);
        addLabelAndField(panel, "Account Number:", accountNumber);
        addLabelAndField(panel, "IFSC Code:", ifscCode);

        return panel;
    }

    private void addLabelAndField(JPanel panel, String labelText, String fieldValue) {
        JLabel label = new JLabel(labelText);
        JTextField field = new JTextField(fieldValue);
        field.setEditable(false);
        panel.add(label);
        panel.add(field);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ScribeBankDetails("admin123"));
    }
}
