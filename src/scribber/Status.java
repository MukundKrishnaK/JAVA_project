package scribber;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Status extends JFrame {
    private String username; // Store logged-in student's username
    private JLabel statusLabel; // Label to display the status message

    public Status(String username) {
        this.username = username; // Assign username

        setTitle("Scribe Status");
        setSize(400, 200); // Small frame size
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel for message display
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        statusLabel = new JLabel();
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(statusLabel);

        // Check scribe status
        checkScribeStatus();

        add(panel, BorderLayout.CENTER);
    }

    private void checkScribeStatus() {
        try {
            Conn conn = new Conn();
            Connection connection = conn.getConnection();
            String query = "SELECT * FROM scribe_student WHERE student_username = ?";
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                statusLabel.setText("<html><center>A scribe has been assigned to your profile.<br>"
                        + "Please ensure to make the payment at the earliest.</center></html>");
                statusLabel.setForeground(Color.GREEN);
            } else {
                statusLabel.setText("No Scribes found for your profile.");
                statusLabel.setForeground(Color.RED);
            }

            rs.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
            statusLabel.setText("Error fetching status.");
            statusLabel.setForeground(Color.RED);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Status("testStudent").setVisible(true));
    }
}

