package scribber;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ScribeStudent extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private String username; // Store the username

    public ScribeStudent(String username) {
        this.username = username; // Assign the username
        setTitle("Assigned Scribes to Students");
        setBounds(400, 100, 800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table model setup
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Scribe First Name", "Scribe Last Name", "Student First Name", "Student Last Name"});
        table = new JTable(model);
        loadAssignedScribes();

        // Scroll Pane
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadAssignedScribes() {
        try {
            Conn conn = new Conn();
            Connection connection = conn.getConnection();
            String query = "SELECT sc.first_name AS scribe_fname, sc.last_name AS scribe_lname, " +
                           "st.first_name AS student_fname, st.last_name AS student_lname " +
                           "FROM scribe_student ss " +
                           "JOIN scribe sc ON ss.scribe_username = sc.username " +
                           "JOIN students st ON ss.student_username = st.username";
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("scribe_fname"), rs.getString("scribe_lname"),
                        rs.getString("student_fname"), rs.getString("student_lname")
                });
            }
            rs.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ScribeStudent("admin").setVisible(true));
    }
}
