package scribber;

import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AcademicsDetails extends JFrame implements ActionListener {
    JTextField tfInstitutionName, tfSpecification, tfExamCenter;
    JComboBox<String> comboEducationType, comboCity;
    JTextArea taSubjects;
    JDateChooser dcExamStart, dcExamEnd;
    JButton btnSubmit;
    String username; // To store the current user's username

    // Constructor
    public AcademicsDetails(String username) {
        this.username = username; // Store the username

        setBounds(350, 100, 1000, 650);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(20, 20, 900, 550);
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Academic Details", 0, 0, new Font("Tahoma", Font.BOLD, 16)));
        panel.setBackground(Color.WHITE);
        add(panel);

        // Institution Name
        JLabel lblInstitution = new JLabel("Institution Name:");
        lblInstitution.setBounds(30, 50, 150, 25);
        panel.add(lblInstitution);

        tfInstitutionName = new JTextField();
        tfInstitutionName.setBounds(200, 50, 200, 25);
        panel.add(tfInstitutionName);

        // Type of Education
        JLabel lblEducationType = new JLabel("Type of Education:");
        lblEducationType.setBounds(30, 100, 150, 25);
        panel.add(lblEducationType);

        String[] educationTypes = {"School", "Pre-University", "UG", "PG"};
        comboEducationType = new JComboBox<>(educationTypes);
        comboEducationType.setBounds(200, 100, 200, 25);
        panel.add(comboEducationType);

        // Specification
        JLabel lblSpecification = new JLabel("Specification:");
        lblSpecification.setBounds(30, 150, 150, 25);
        panel.add(lblSpecification);

        tfSpecification = new JTextField();
        tfSpecification.setBounds(200, 150, 200, 25);
        panel.add(tfSpecification);

        // Subjects
        JLabel lblSubjects = new JLabel("Subjects:");
        lblSubjects.setBounds(30, 200, 150, 25);
        panel.add(lblSubjects);

        taSubjects = new JTextArea();
        taSubjects.setLineWrap(true);
        taSubjects.setWrapStyleWord(true);
        JScrollPane spSubjects = new JScrollPane(taSubjects);
        spSubjects.setBounds(200, 200, 200, 60);
        panel.add(spSubjects);

        // Exam Start Date
        JLabel lblStartDate = new JLabel("Exam Start Date:");
        lblStartDate.setBounds(30, 280, 150, 25);
        panel.add(lblStartDate);

        dcExamStart = new JDateChooser();
        dcExamStart.setBounds(200, 280, 200, 25);
        panel.add(dcExamStart);

        // Exam End Date
        JLabel lblEndDate = new JLabel("Exam End Date:");
        lblEndDate.setBounds(30, 320, 150, 25);
        panel.add(lblEndDate);

        dcExamEnd = new JDateChooser();
        dcExamEnd.setBounds(200, 320, 200, 25);
        panel.add(dcExamEnd);

        // Exam Center
        JLabel lblExamCenter = new JLabel("Exam Center:");
        lblExamCenter.setBounds(30, 360, 150, 25);
        panel.add(lblExamCenter);

        tfExamCenter = new JTextField();
        tfExamCenter.setBounds(200, 360, 200, 25);
        panel.add(tfExamCenter);

        // Center Address (Dropdown for State)
        JLabel lblCity = new JLabel("Center Address (City):");
        lblCity.setBounds(30, 400, 150, 25);
        panel.add(lblCity);

        String[] cities = {"Karnataka", "Maharashtra", "Tamil Nadu", "Delhi"};
        comboCity = new JComboBox<>(cities);
        comboCity.setBounds(200, 400, 200, 25);
        panel.add(comboCity);

        // Submit Button
        btnSubmit = new JButton("Submit");
        btnSubmit.setBounds(250, 450, 100, 30);
        btnSubmit.addActionListener(this);
        panel.add(btnSubmit);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("images/aca.png"));
        Image i2 = i1.getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(400, 70, 500, 400);
        panel.add(image);

        setVisible(true);
    }

    // Action Listener
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSubmit) {
            saveToDatabase();
        }
    }

    // Method to save academic details to database
    public void saveToDatabase() {
        String institutionName = tfInstitutionName.getText();
        String educationType = (String) comboEducationType.getSelectedItem();
        String specification = tfSpecification.getText();
        String subjects = taSubjects.getText();
        java.util.Date startDate = dcExamStart.getDate();
        java.util.Date endDate = dcExamEnd.getDate();
        String examCenter = tfExamCenter.getText();
        String city = (String) comboCity.getSelectedItem();

        if (institutionName.isEmpty() || specification.isEmpty() || subjects.isEmpty() || startDate == null || endDate == null || examCenter.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/scribber", "root", "7586");
            String query = "INSERT INTO academics (username, institution_name, education_type, specification, subjects, exam_start_date, exam_end_date, exam_center, center_address) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, username);
            pst.setString(2, institutionName);
            pst.setString(3, educationType);
            pst.setString(4, specification);
            pst.setString(5, subjects);
            pst.setDate(6, new java.sql.Date(startDate.getTime()));
            pst.setDate(7, new java.sql.Date(endDate.getTime()));
            pst.setString(8, examCenter);
            pst.setString(9, city);

            int rowsInserted = pst.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Academic details saved successfully!");
                dispose();
            }

            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving details to database.");
        }
    }

    public static void main(String[] args) {
        new AcademicsDetails("User"); 
    }
}
