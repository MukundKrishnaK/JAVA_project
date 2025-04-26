package scribber;

import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ScribeAcademicsDetails extends JFrame implements ActionListener {
    JTextField tfInstitutionName, tfSpecification, tfCGPA, tfFee;
    JComboBox<String> comboEducationType;
    JDateChooser dcAvailabilityFrom, dcAvailabilityTo;
    JButton btnSubmit;
    String username;

    public ScribeAcademicsDetails(String username) {
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
        panel.add(tfInstitutionName);

        JLabel lblEducationType = new JLabel("Type of Education:");
        lblEducationType.setBounds(30, 100, 150, 25);
        panel.add(lblEducationType);

        String[] educationTypes = {"Pre-University", "UG", "PG"};
        comboEducationType = new JComboBox<>(educationTypes);
        comboEducationType.setBounds(200, 100, 200, 25);
        panel.add(comboEducationType);

        JLabel lblSpecification = new JLabel("Specification:");
        lblSpecification.setBounds(30, 150, 150, 25);
        panel.add(lblSpecification);

        tfSpecification = new JTextField();
        tfSpecification.setBounds(200, 150, 200, 25);
        panel.add(tfSpecification);

        JLabel lblCGPA = new JLabel("CGPA:");
        lblCGPA.setBounds(30, 200, 150, 25);
        panel.add(lblCGPA);

        tfCGPA = new JTextField();
        tfCGPA.setBounds(200, 200, 200, 25);
        panel.add(tfCGPA);

        JLabel lblFee = new JLabel("Fee:");
        lblFee.setBounds(30, 250, 150, 25);
        panel.add(lblFee);

        tfFee = new JTextField();
        tfFee.setBounds(200, 250, 200, 25);
        panel.add(tfFee);

        JLabel lblAvailabilityFrom = new JLabel("Availability From:");
        lblAvailabilityFrom.setBounds(30, 300, 150, 25);
        panel.add(lblAvailabilityFrom);

        dcAvailabilityFrom = new JDateChooser();
        dcAvailabilityFrom.setBounds(200, 300, 200, 25);
        panel.add(dcAvailabilityFrom);

        JLabel lblAvailabilityTo = new JLabel("Availability To:");
        lblAvailabilityTo.setBounds(30, 350, 150, 25);
        panel.add(lblAvailabilityTo);

        dcAvailabilityTo = new JDateChooser();
        dcAvailabilityTo.setBounds(200, 350, 200, 25);
        panel.add(dcAvailabilityTo);

        btnSubmit = new JButton("Submit");
        btnSubmit.setBounds(250, 400, 100, 20);
        btnSubmit.addActionListener(this);
        panel.add(btnSubmit);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSubmit) {
            saveToDatabase();
        }
    }

    public void saveToDatabase() {
        String institutionName = tfInstitutionName.getText();
        String educationType = (String) comboEducationType.getSelectedItem();
        String specification = tfSpecification.getText();
        String cgpa = tfCGPA.getText();
        String fee = tfFee.getText();
        java.util.Date availabilityFrom = dcAvailabilityFrom.getDate();
        java.util.Date availabilityTo = dcAvailabilityTo.getDate();

        if (institutionName.isEmpty() || specification.isEmpty() || cgpa.isEmpty() || fee.isEmpty() || availabilityFrom == null || availabilityTo == null) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/scribber", "root", "7586");
            String query = "INSERT INTO scribe_academics (username, institution_name, education_type, specification, cgpa, fee, availability_from, availability_to) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, username);
            pst.setString(2, institutionName);
            pst.setString(3, educationType);
            pst.setString(4, specification);
            pst.setString(5, cgpa);
            pst.setString(6, fee);
            pst.setDate(7, new java.sql.Date(availabilityFrom.getTime()));
            pst.setDate(8, new java.sql.Date(availabilityTo.getTime()));

            int rowsInserted = pst.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Scribe academic details saved successfully!");
                dispose();
            }

            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving details to database.");
        }
    }

    public static void main(String[] args) {
        new ScribeAcademicsDetails("ScribeUser");
    }
}
