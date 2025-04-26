package scribber;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ScribeStatus extends JFrame {
    private String scribeUsername; // Logged-in scribe's username
    private JLabel statusLabel;
    private JPanel detailsPanel;

    public ScribeStatus(String scribeUsername) {
        this.scribeUsername = scribeUsername; // Assign username

        setTitle("Scribe Assignment Status");
        setSize(550, 350); // Slightly bigger frame
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel for displaying status
        JPanel panel = new JPanel(new BorderLayout());

        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(statusLabel, BorderLayout.NORTH);

        // Panel for student details
        detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridLayout(9, 2, 10, 5));
        detailsPanel.setVisible(false); // Initially hidden

        panel.add(detailsPanel, BorderLayout.CENTER);

        // Check if a student is assigned
        checkScribeStatus();

        add(panel);
    }

    private void checkScribeStatus() {
        try {
            Conn conn = new Conn();
            Connection connection = conn.getConnection();

            // Query to check if the scribe has an assigned student
            String query = "SELECT student_username FROM scribe_student WHERE scribe_username = ?";
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, scribeUsername);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String studentUsername = rs.getString("student_username");

                // Fetch student academic details
                fetchStudentDetails(studentUsername);
            } else {
                statusLabel.setText("No student has been assigned to your profile.");
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

    private void fetchStudentDetails(String studentUsername) {
        try {
            Conn conn = new Conn();
            Connection connection = conn.getConnection();

            String query = "SELECT * FROM academics WHERE username = ?";
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, studentUsername);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                statusLabel.setText("A student has been successfully assigned to your profile.");
                statusLabel.setForeground(Color.GREEN);

                // Retrieve academic details
                String institution = rs.getString("institution_name");
                String educationType = rs.getString("education_type");
                String specification = rs.getString("specification");
                String subjects = rs.getString("subjects");
                Date examStartDate = rs.getDate("exam_start_date");
                Date examEndDate = rs.getDate("exam_end_date");
                String examCenter = rs.getString("exam_center");
                String centerAddress = rs.getString("center_address");

                // Display student details using labels and text fields
                detailsPanel.removeAll(); // Clear previous data
                addDetail("Student Username:", studentUsername);
                addDetail("Institution:", institution);
                addDetail("Education Type:", educationType);
                addDetail("Specification:", specification);
                addDetail("Subjects:", subjects);
                addDetail("Exam Start Date:", examStartDate.toString());
                addDetail("Exam End Date:", examEndDate.toString());
                addDetail("Exam Center:", examCenter);
                addDetail("Center Address:", centerAddress);

                detailsPanel.setVisible(true); // Show details panel
                revalidate();
                repaint();
            } else {
                statusLabel.setText("No academic details found for assigned student.");
                statusLabel.setForeground(Color.RED);
            }

            rs.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
            statusLabel.setText("Error fetching student details.");
            statusLabel.setForeground(Color.RED);
        }
    }

    private void addDetail(String label, String value) {
        JLabel lbl = new JLabel(label);
        JTextField txtField = new JTextField(value);
        txtField.setEditable(false);
        txtField.setBackground(Color.LIGHT_GRAY);
        detailsPanel.add(lbl);
        detailsPanel.add(txtField);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ScribeStatus("testScribe").setVisible(true));
    }
}
