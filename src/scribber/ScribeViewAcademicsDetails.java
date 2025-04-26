package scribber;

import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.sql.*;

public class ScribeViewAcademicsDetails extends JFrame {
    JTextField tfInstitutionName, tfSpecification, tfCGPA, tfFee;
    JComboBox<String> comboEducationType;
    JDateChooser dcAvailabilityFrom, dcAvailabilityTo;
    String username;

    public ScribeViewAcademicsDetails(String username) {
        this.username = username;
        setBounds(500, 100, 700, 500);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(20, 20, 650, 420);
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Scribe Academic Details", 0, 0, new Font("Tahoma", Font.BOLD, 16)));
        panel.setBackground(Color.WHITE);
        add(panel);

        JLabel lblInstitution = new JLabel("Institution Name:");
        lblInstitution.setBounds(30, 50, 150, 25);
        panel.add(lblInstitution);

        tfInstitutionName = new JTextField();
        tfInstitutionName.setBounds(200, 50, 200, 25);
        tfInstitutionName.setEditable(false);
        panel.add(tfInstitutionName);

        JLabel lblEducationType = new JLabel("Type of Education:");
        lblEducationType.setBounds(30, 100, 150, 25);
        panel.add(lblEducationType);

        comboEducationType = new JComboBox<>(new String[]{"Pre-University", "UG", "PG"});
        comboEducationType.setBounds(200, 100, 200, 25);
        comboEducationType.setEnabled(false);
        panel.add(comboEducationType);

        JLabel lblSpecification = new JLabel("Specification:");
        lblSpecification.setBounds(30, 150, 150, 25);
        panel.add(lblSpecification);

        tfSpecification = new JTextField();
        tfSpecification.setBounds(200, 150, 200, 25);
        tfSpecification.setEditable(false);
        panel.add(tfSpecification);

        JLabel lblCGPA = new JLabel("CGPA:");
        lblCGPA.setBounds(30, 200, 150, 25);
        panel.add(lblCGPA);

        tfCGPA = new JTextField();
        tfCGPA.setBounds(200, 200, 200, 25);
        tfCGPA.setEditable(false);
        panel.add(tfCGPA);

        JLabel lblFee = new JLabel("Fee:");
        lblFee.setBounds(30, 250, 150, 25);
        panel.add(lblFee);

        tfFee = new JTextField();
        tfFee.setBounds(200, 250, 200, 25);
        tfFee.setEditable(false);
        panel.add(tfFee);

        JLabel lblAvailabilityFrom = new JLabel("Availability From:");
        lblAvailabilityFrom.setBounds(30, 300, 150, 25);
        panel.add(lblAvailabilityFrom);

        dcAvailabilityFrom = new JDateChooser();
        dcAvailabilityFrom.setBounds(200, 300, 200, 25);
        dcAvailabilityFrom.setEnabled(false);
        panel.add(dcAvailabilityFrom);

        JLabel lblAvailabilityTo = new JLabel("Availability To:");
        lblAvailabilityTo.setBounds(30, 350, 150, 25);
        panel.add(lblAvailabilityTo);

        dcAvailabilityTo = new JDateChooser();
        dcAvailabilityTo.setBounds(200, 350, 200, 25);
        dcAvailabilityTo.setEnabled(false);
        panel.add(dcAvailabilityTo);

        loadScribeDetails();
        setVisible(true);
    }

    private void loadScribeDetails() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/scribber", "root", "7586");
            String query = "SELECT * FROM scribe_academics WHERE username = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                tfInstitutionName.setText(rs.getString("institution_name"));
                comboEducationType.setSelectedItem(rs.getString("education_type"));
                tfSpecification.setText(rs.getString("specification"));
                tfCGPA.setText(rs.getString("cgpa"));
                tfFee.setText(rs.getString("fee"));
                dcAvailabilityFrom.setDate(rs.getDate("availability_from"));
                dcAvailabilityTo.setDate(rs.getDate("availability_to"));
            } else {
                JOptionPane.showMessageDialog(this, "No academic details found for this user.");
            }

            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading scribe details.");
        }
    }

    public static void main(String[] args) {
        new ScribeViewAcademicsDetails("ScribeUser");
    }
}
