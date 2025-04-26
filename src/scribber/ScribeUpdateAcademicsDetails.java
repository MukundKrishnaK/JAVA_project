/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scribber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.toedter.calendar.JDateChooser;
import java.sql.*;
import java.io.File;

public class ScribeUpdateAcademicsDetails extends JFrame implements ActionListener {
    JTextField tfUsername, tfInstitutionName, tfSpecification, tfCGPA, tfFee;
    JComboBox<String> comboEducationType;
    JDateChooser dcAvailabilityFrom, dcAvailabilityTo;
    JButton update, back;
    String username;
    
    ScribeUpdateAcademicsDetails(String username) {
        this.username = username;
        setBounds(500, 100, 850, 600);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(0, 0, 800, 550);
        mainPanel.setBorder(BorderFactory.createTitledBorder("Update Scribe Academic Details"));
        mainPanel.setBackground(Color.WHITE);
        add(mainPanel);

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setBounds(30, 50, 150, 25);
        mainPanel.add(lblUsername);

        tfUsername = new JTextField(username);
        tfUsername.setBounds(220, 50, 200, 25);
        tfUsername.setEditable(false);
        mainPanel.add(tfUsername);
        
        JLabel lblInstitution = new JLabel("Institution Name:");
        lblInstitution.setBounds(30, 90, 150, 25);
        mainPanel.add(lblInstitution);

        tfInstitutionName = new JTextField();
        tfInstitutionName.setBounds(220, 90, 200, 25);
        mainPanel.add(tfInstitutionName);
        
        JLabel lblEducation = new JLabel("Type of Education:");
        lblEducation.setBounds(30, 130, 150, 25);
        mainPanel.add(lblEducation);

        comboEducationType = new JComboBox<>(new String[]{"Pre-University", "UG", "PG"});
        comboEducationType.setBounds(220, 130, 200, 25);
        mainPanel.add(comboEducationType);

        JLabel lblSpecification = new JLabel("Specification:");
        lblSpecification.setBounds(30, 170, 150, 25);
        mainPanel.add(lblSpecification);

        tfSpecification = new JTextField();
        tfSpecification.setBounds(220, 170, 200, 25);
        mainPanel.add(tfSpecification);
        
        JLabel lblCGPA = new JLabel("CGPA:");
        lblCGPA.setBounds(30, 210, 150, 25);
        mainPanel.add(lblCGPA);
        
        tfCGPA = new JTextField();
        tfCGPA.setBounds(220, 210, 200, 25);
        mainPanel.add(tfCGPA);

        JLabel lblFee = new JLabel("Fee:");
        lblFee.setBounds(30, 250, 150, 25);
        mainPanel.add(lblFee);
        
        tfFee = new JTextField();
        tfFee.setBounds(220, 250, 200, 25);
        mainPanel.add(tfFee);

        JLabel lblAvailabilityFrom = new JLabel("Availability From:");
        lblAvailabilityFrom.setBounds(30, 290, 150, 25);
        mainPanel.add(lblAvailabilityFrom);

        dcAvailabilityFrom = new JDateChooser();
        dcAvailabilityFrom.setBounds(220, 290, 200, 25);
        mainPanel.add(dcAvailabilityFrom);

        JLabel lblAvailabilityTo = new JLabel("Availability To:");
        lblAvailabilityTo.setBounds(30, 330, 150, 25);
        mainPanel.add(lblAvailabilityTo);

        dcAvailabilityTo = new JDateChooser();
        dcAvailabilityTo.setBounds(220, 330, 200, 25);
        mainPanel.add(dcAvailabilityTo);

        update = new JButton("Update");
        update.setBounds(100, 400, 100, 30);
        update.addActionListener(this);
        mainPanel.add(update);

        back = new JButton("Back");
        back.setBounds(250, 400, 100, 30);
        back.addActionListener(this);
        mainPanel.add(back);

        loadAcademicDetails();
        setVisible(true);
    }

    private void loadAcademicDetails() {
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
                String query = "UPDATE scribe_academics SET institution_name = ?, education_type = ?, specification = ?, cgpa = ?, fee = ?, availability_from = ?, availability_to = ? WHERE username = ?";
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setString(1, tfInstitutionName.getText());
                pst.setString(2, (String) comboEducationType.getSelectedItem());
                pst.setString(3, tfSpecification.getText());
                pst.setString(4, tfCGPA.getText());
                pst.setString(5, tfFee.getText());
                pst.setDate(6, new java.sql.Date(dcAvailabilityFrom.getDate().getTime()));
                pst.setDate(7, new java.sql.Date(dcAvailabilityTo.getDate().getTime()));
                pst.setString(8, username);
                
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
        new ScribeUpdateAcademicsDetails("ScribeUser");
    }
}
