package scribber;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ScribePaymentAck extends JFrame {
    public ScribePaymentAck(String username) {
        setTitle("Scribe Payment Acknowledgement");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(7, 2, 10, 10));

        // Labels and text fields (non-editable)
        JLabel lblBankName = new JLabel("Bank Name:");
        JTextField txtBankName = new JTextField();
        txtBankName.setEditable(false);

        JLabel lblAccountHolder = new JLabel("Account Holder:");
        JTextField txtAccountHolder = new JTextField();
        txtAccountHolder.setEditable(false);

        JLabel lblAccountNumber = new JLabel("Account Number:");
        JTextField txtAccountNumber = new JTextField();
        txtAccountNumber.setEditable(false);

        JLabel lblIfscCode = new JLabel("IFSC Code:");
        JTextField txtIfscCode = new JTextField();
        txtIfscCode.setEditable(false);

        JLabel lblTotalAmount = new JLabel("Total Amount:");
        JTextField txtTotalAmount = new JTextField();
        txtTotalAmount.setEditable(false);

        JLabel lblPaymentDate = new JLabel("Payment Date:");
        JTextField txtPaymentDate = new JTextField();
        txtPaymentDate.setEditable(false);

        // Fetch data from the database
        try {
            Conn conn = new Conn();
            Connection connection = conn.getConnection();
            String query = "SELECT bank_name, account_holder_name, account_number, ifsc_code, total_amount, payment_date FROM scribe_payment WHERE username = ?";
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                txtBankName.setText(rs.getString("bank_name"));
                txtAccountHolder.setText(rs.getString("account_holder_name"));
                txtAccountNumber.setText(rs.getString("account_number"));
                txtIfscCode.setText(rs.getString("ifsc_code"));
                txtTotalAmount.setText(rs.getString("total_amount"));
                txtPaymentDate.setText(rs.getString("payment_date"));
            } else {
                JOptionPane.showMessageDialog(this, "No payment record found!", "Info", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }

            rs.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading payment details!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Add components to the frame
        add(lblBankName);
        add(txtBankName);
        add(lblAccountHolder);
        add(txtAccountHolder);
        add(lblAccountNumber);
        add(txtAccountNumber);
        add(lblIfscCode);
        add(txtIfscCode);
        add(lblTotalAmount);
        add(txtTotalAmount);
        add(lblPaymentDate);
        add(txtPaymentDate);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ScribePaymentAck("scribeUser123"));
    }
}
