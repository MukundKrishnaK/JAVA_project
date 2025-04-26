package scribber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.toedter.calendar.JDateChooser;
import java.sql.*;

public class UpdateAcademicsDetails extends JFrame implements ActionListener {
    JTextField tfInstitutionName, tfSpecification, tfExamCenter, tfPaymentPerSubject;
    JComboBox<String> comboEducationType, comboExamCenterCity;
    JTextArea taSubjects;
    JDateChooser dcExamStart, dcExamEnd;
    JButton update, back;
    String username;
    
    UpdateAcademicsDetails(String username) {
        this.username = username;
        setBounds(250, 100, 1000, 650);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(20, 20, 900, 550);
        mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), " Update Academic Details", 0, 0, new Font("Tahoma", Font.BOLD, 16)));
        mainPanel.setBackground(Color.WHITE);
        add(mainPanel);

        JLabel lblInstitution = new JLabel("Institution Name:");
        lblInstitution.setBounds(30, 50, 150, 25);
        mainPanel.add(lblInstitution);

        tfInstitutionName = new JTextField();
        tfInstitutionName.setBounds(220, 50, 200, 25);
        mainPanel.add(tfInstitutionName);
        
        JLabel lblEducation = new JLabel("Type of Education:");
        lblEducation.setBounds(30, 90, 150, 25);
        mainPanel.add(lblEducation);

        comboEducationType = new JComboBox<>(new String[]{"School", "Pre-University", "UG", "PG"});
        comboEducationType.setBounds(220, 90, 200, 25);
        mainPanel.add(comboEducationType);

        JLabel lblSpecification = new JLabel("Specification:");
        lblSpecification.setBounds(30, 130, 150, 25);
        mainPanel.add(lblSpecification);

        tfSpecification = new JTextField();
        tfSpecification.setBounds(220, 130, 200, 25);
        mainPanel.add(tfSpecification);
        
        JLabel lblSubjects = new JLabel("Subjects:");
        lblSubjects.setBounds(30, 170, 150, 25);
        mainPanel.add(lblSubjects);

        taSubjects = new JTextArea();
        taSubjects.setBounds(220, 170, 200, 80);
        taSubjects.setLineWrap(true);
        mainPanel.add(taSubjects);

        JLabel lblExamStart = new JLabel("Exam Start Date:");
        lblExamStart.setBounds(30, 270, 150, 25);
        mainPanel.add(lblExamStart);

        dcExamStart = new JDateChooser();
        dcExamStart.setBounds(220, 270, 200, 25);
        mainPanel.add(dcExamStart);

        JLabel lblExamEnd = new JLabel("Exam End Date:");
        lblExamEnd.setBounds(30, 310, 150, 25);
        mainPanel.add(lblExamEnd);

        dcExamEnd = new JDateChooser();
        dcExamEnd.setBounds(220, 310, 200, 25);
        mainPanel.add(dcExamEnd);

        JLabel lblExamCenter = new JLabel("Exam Center:");
        lblExamCenter.setBounds(30, 350, 150, 25);
        mainPanel.add(lblExamCenter);
        
        tfExamCenter = new JTextField();
        tfExamCenter.setBounds(220, 350, 200, 25);
        mainPanel.add(tfExamCenter);
        
        JLabel lblExamCity = new JLabel("Exam Center City:");
        lblExamCity.setBounds(30, 390, 150, 25);
        mainPanel.add(lblExamCity);
        
        comboExamCenterCity = new JComboBox<>(new String[]{"Karnataka", "Maharashtra", "Tamil Nadu", "Delhi"});
        comboExamCenterCity.setBounds(220, 390, 200, 25);
        mainPanel.add(comboExamCenterCity);

        JLabel lblPaymentPerSubject = new JLabel("Payment Per Subject:");
        lblPaymentPerSubject.setBounds(30, 430, 150, 25);
        mainPanel.add(lblPaymentPerSubject);

        tfPaymentPerSubject = new JTextField();
        tfPaymentPerSubject.setBounds(220, 430, 200, 25);
        tfPaymentPerSubject.setEditable(false);
        mainPanel.add(tfPaymentPerSubject);
        
        update = new JButton("Update");
        update.setBounds(100, 500, 100, 30);
        update.addActionListener(this);
        mainPanel.add(update);

        back = new JButton("Back");
        back.setBounds(250, 500, 100, 30);
        back.addActionListener(this);
        mainPanel.add(back);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("images/aca.png"));
        Image i2 = i1.getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(400, 70, 500, 400);
        mainPanel.add(image);
        

        loadAcademicDetails();
        setVisible(true);
    }

    private void loadAcademicDetails() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/scribber", "root", "7586");
            String query = "SELECT * FROM academics WHERE username = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                tfInstitutionName.setText(rs.getString("institution_name"));
                comboEducationType.setSelectedItem(rs.getString("education_type"));
                tfSpecification.setText(rs.getString("specification"));
                taSubjects.setText(rs.getString("subjects"));
                dcExamStart.setDate(rs.getDate("exam_start_date"));
                dcExamEnd.setDate(rs.getDate("exam_end_date"));
                tfExamCenter.setText(rs.getString("exam_center"));
                comboExamCenterCity.setSelectedItem(rs.getString("center_address"));

                // Determine payment per subject
                int paymentPerSubject = 0;
                switch (rs.getString("education_type")) {
                    case "School": paymentPerSubject = 500; break;
                    case "Pre-University": paymentPerSubject = 800; break;
                    case "UG": paymentPerSubject = 1000; break;
                    case "PG": paymentPerSubject = 1200; break;
                }
                tfPaymentPerSubject.setText("Rs. " + paymentPerSubject);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading details.");
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == update) {
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/scribber", "root", "7586");
                String query = "UPDATE academics SET institution_name = ?, education_type = ?, specification = ?, subjects = ?, exam_start_date = ?, exam_end_date = ?, exam_center = ?, center_address = ? WHERE username = ?";
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setString(1, tfInstitutionName.getText());
                pst.setString(2, (String) comboEducationType.getSelectedItem());
                pst.setString(3, tfSpecification.getText());
                pst.setString(4, taSubjects.getText());
                pst.setDate(5, new java.sql.Date(dcExamStart.getDate().getTime()));
                pst.setDate(6, new java.sql.Date(dcExamEnd.getDate().getTime()));
                pst.setString(7, tfExamCenter.getText());
                pst.setString(8, (String) comboExamCenterCity.getSelectedItem());
                pst.setString(9, username);
                
                pst.executeUpdate();
                conn.close();
                JOptionPane.showMessageDialog(null, "Academic Details Updated Successfully!");
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error updating details.");
            }
        } else if (ae.getSource() == back) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new UpdateAcademicsDetails("User");
    }
}
