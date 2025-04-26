package scribber;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class AssignScribe extends JFrame {
    private JTable scribeTable, studentTable;
    private DefaultTableModel scribeModel, studentModel;
    private JButton assignButton;
    private String selectedScribeUsername = "";
    private String selectedStudentUsername = "";
    private String adminUsername;

    public AssignScribe(String adminUsername) {
        this.adminUsername = adminUsername;
        setTitle("Assign Scribe to Student");
        setBounds(400, 100, 900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set background color
        getContentPane().setBackground(Color.WHITE);

        // Panel for Tables
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.setBackground(Color.WHITE);

        // Scribe Table
        scribeModel = new DefaultTableModel();
        scribeModel.setColumnIdentifiers(new String[]{"Username", "First Name", "Last Name", "City", "Available From", "Available To"});
        scribeTable = new JTable(scribeModel);
        scribeTable.setBackground(Color.WHITE);
        loadScribeData();

        // Student Table
        studentModel = new DefaultTableModel();
        studentModel.setColumnIdentifiers(new String[]{"Username", "First Name", "Last Name", "City", "Exam Start", "Exam End"});
        studentTable = new JTable(studentModel);
        studentTable.setBackground(Color.WHITE);
        loadStudentData();

        // Adding tables to panel
        panel.add(new JScrollPane(scribeTable));
        panel.add(new JScrollPane(studentTable));

        add(panel, BorderLayout.CENTER);

        // Assign Button
        assignButton = new JButton("Assign Scribe");
        assignButton.setEnabled(false);
        assignButton.setForeground(Color.BLACK);
        assignButton.setBackground(new Color(255, 87, 34));
        assignButton.addActionListener(e -> assignScribe());
        add(assignButton, BorderLayout.SOUTH);

        // Row Selection Listener for Scribes
        scribeTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = scribeTable.getSelectedRow();
                selectedScribeUsername = scribeModel.getValueAt(row, 0).toString();
                highlightMatchingStudents();
            }
        });
    }

    private void loadScribeData() {
        try {
            Conn conn = new Conn();
            Connection connection = conn.getConnection();
            String query = "SELECT s.username, s.first_name, s.last_name, s.city, sa.availability_from, sa.availability_to " +
                           "FROM scribe s JOIN scribe_academics sa ON s.username = sa.username";
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                scribeModel.addRow(new Object[]{
                        rs.getString("username"), rs.getString("first_name"), rs.getString("last_name"),
                        rs.getString("city"), rs.getString("availability_from"), rs.getString("availability_to")
                });
            }
            rs.close();
            pst.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadStudentData() {
        try {
            Conn conn = new Conn();
            Connection connection = conn.getConnection();
            String query = "SELECT s.username, s.first_name, s.last_name, s.city, a.exam_start_date, a.exam_end_date " +
                           "FROM students s JOIN academics a ON s.username = a.username";
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                studentModel.addRow(new Object[]{
                        rs.getString("username"), rs.getString("first_name"), rs.getString("last_name"),
                        rs.getString("city"), rs.getString("exam_start_date"), rs.getString("exam_end_date")
                });
            }
            rs.close();
            pst.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void highlightMatchingStudents() {
        int selectedRow = scribeTable.getSelectedRow();
        if (selectedRow < 0) return;

        String scribeCity = scribeModel.getValueAt(selectedRow, 3).toString();
        String availFrom = scribeModel.getValueAt(selectedRow, 4).toString();
        String availTo = scribeModel.getValueAt(selectedRow, 5).toString();

        assignButton.setEnabled(false);
        studentTable.clearSelection();
        
        for (int i = 0; i < studentModel.getRowCount(); i++) {
            String studentCity = studentModel.getValueAt(i, 3).toString();
            String examStart = studentModel.getValueAt(i, 4).toString();
            String examEnd = studentModel.getValueAt(i, 5).toString();

            // Check if student matches the criteria
            if (studentCity.equals(scribeCity) && examStart.compareTo(availFrom) >= 0 && examEnd.compareTo(availTo) <= 0) {
                studentTable.setRowSelectionInterval(i, i);
                selectedStudentUsername = studentModel.getValueAt(i, 0).toString();
                assignButton.setEnabled(true);
                return;
            }
        }
    }

    private void assignScribe() {
        if (selectedScribeUsername.isEmpty() || selectedStudentUsername.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a scribe and a matching student first.");
            return;
        }

        try {
            Conn conn = new Conn();
            Connection connection = conn.getConnection();
            String query = "INSERT INTO scribe_student (student_username, scribe_username) VALUES (?, ?)";
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, selectedStudentUsername);
            pst.setString(2, selectedScribeUsername);
            int rowsInserted = pst.executeUpdate();
            pst.close();

            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Scribe assigned successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AssignScribe("AdminUser").setVisible(true));
    }
} 
