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
import java.text.SimpleDateFormat;

public class ScribeUpdateProfile extends JFrame implements ActionListener {
    JTextField tfFirstName, tfLastName, tfAge, tfPhone, tfAddress, tfAadhar;
    JComboBox<String> comboCountry, comboState, comboCity;
    JRadioButton rmale, rfemale;
    JButton update, back;
    JDateChooser dateChooser;
    String username;

    ScribeUpdateProfile(String username) {
        this.username = username; // Save username for future use
        setBounds(500, 100, 850, 650);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(0, 0, 800, 600);
        mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Update Profile", 0, 0, new Font("Tahoma", Font.BOLD, 16)));
        mainPanel.setBackground(Color.WHITE);
        add(mainPanel);

        // Label for "Username"
        JLabel lblUsernameLabel = new JLabel("Username: ");
        lblUsernameLabel.setBounds(30, 50, 150, 25);
        mainPanel.add(lblUsernameLabel);

        // TextField to display the username (non-editable)
        JTextField tfUsername = new JTextField(username);
        tfUsername.setBounds(220, 50, 150, 25);
        tfUsername.setEditable(false); // Make the text field non-editable
        mainPanel.add(tfUsername);

        // Fetch the existing user profile from the database and display
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/scribber", "root", "7586");
            String query = "SELECT * FROM scribe WHERE username = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                // Label for First Name
                JLabel lblFirstName = new JLabel("First Name: ");
                lblFirstName.setBounds(30, 90, 150, 25);
                mainPanel.add(lblFirstName);

                tfFirstName = new JTextField(rs.getString("first_name"));
                tfFirstName.setBounds(220, 90, 150, 25);
                mainPanel.add(tfFirstName);

                // Label for Last Name
                JLabel lblLastName = new JLabel("Last Name: ");
                lblLastName.setBounds(30, 130, 150, 25);
                mainPanel.add(lblLastName);

                tfLastName = new JTextField(rs.getString("last_name"));
                tfLastName.setBounds(220, 130, 150, 25);
                mainPanel.add(tfLastName);

                // Label for Date of Birth
                JLabel lblDob = new JLabel("Date of Birth: ");
                lblDob.setBounds(30, 170, 150, 25);
                mainPanel.add(lblDob);

                dateChooser = new JDateChooser();
                dateChooser.setDate(rs.getDate("dob"));
                dateChooser.setBounds(220, 170, 150, 25);
                mainPanel.add(dateChooser);

                // Label for Age
                JLabel lblAge = new JLabel("Age: ");
                lblAge.setBounds(30, 210, 150, 25);
                mainPanel.add(lblAge);

                tfAge = new JTextField(String.valueOf(rs.getInt("age")));
                tfAge.setBounds(220, 210, 150, 25);
                mainPanel.add(tfAge);

                // Label for Gender
                JLabel lblGender = new JLabel("Gender: ");
                lblGender.setBounds(30, 250, 150, 25);
                mainPanel.add(lblGender);

                String gender = rs.getString("gender");
                rmale = new JRadioButton("Male");
                rmale.setBounds(220, 250, 70, 25);
                rmale.setBackground(Color.WHITE);
                mainPanel.add(rmale);
                rfemale = new JRadioButton("Female");
                rfemale.setBounds(300, 250, 70, 25);
                rfemale.setBackground(Color.WHITE);
                mainPanel.add(rfemale);
                ButtonGroup bg = new ButtonGroup();
                bg.add(rmale);
                bg.add(rfemale);
                if ("Male".equals(gender)) {
                    rmale.setSelected(true);
                } else {
                    rfemale.setSelected(true);
                }

                // Label for Phone Number
                JLabel lblPhone = new JLabel("Phone Number: ");
                lblPhone.setBounds(30, 290, 150, 25);
                mainPanel.add(lblPhone);

                tfPhone = new JTextField(rs.getString("phone_number"));
                tfPhone.setBounds(220, 290, 150, 25);
                mainPanel.add(tfPhone);

                // Label for Country
                JLabel lblCountry = new JLabel("Country: ");
                lblCountry.setBounds(30, 330, 150, 25);
                mainPanel.add(lblCountry);

                comboCountry = new JComboBox<>(new String[]{"India"});
                comboCountry.setBounds(220, 330, 150, 25);
                mainPanel.add(comboCountry);

                // Label for State
                JLabel lblState = new JLabel("State: ");
                lblState.setBounds(30, 370, 150, 25);
                mainPanel.add(lblState);

                comboState = new JComboBox<>(new String[]{"Karnataka", "Maharashtra", "Tamil Nadu", "Delhi"});
                comboState.setBounds(220, 370, 150, 25);
                mainPanel.add(comboState);

                // Label for City
                JLabel lblCity = new JLabel("City: ");
                lblCity.setBounds(30, 410, 150, 25);
                mainPanel.add(lblCity);

                comboCity = new JComboBox<>(new String[]{"Bangalore", "Mumbai", "Chennai", "Delhi"});
                comboCity.setBounds(220, 410, 150, 25);
                mainPanel.add(comboCity);

                // Label for Address
                JLabel lblAddress = new JLabel("Address: ");
                lblAddress.setBounds(30, 450, 150, 25);
                mainPanel.add(lblAddress);

                tfAddress = new JTextField(rs.getString("address"));
                tfAddress.setBounds(220, 450, 150, 25);
                mainPanel.add(tfAddress);

                // Label for Aadhar Number
                JLabel lblAadhar = new JLabel("Aadhar Number: ");
                lblAadhar.setBounds(30, 490, 150, 25);
                mainPanel.add(lblAadhar);

                tfAadhar = new JTextField(rs.getString("aadhar_number"));
                tfAadhar.setBounds(220, 490, 150, 25);
                mainPanel.add(tfAadhar);

            } else {
                JOptionPane.showMessageDialog(null, "No profile found for this username.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching profile details.");
        }

        // Submit and back buttons
        update = new JButton("Update");
        update.setBounds(70, 530, 100, 30);
        update.addActionListener(this);
        mainPanel.add(update);

        back = new JButton("Back");
        back.setBounds(220, 530, 100, 30);
        back.addActionListener(this);
        mainPanel.add(back);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == update) {
            String firstName = tfFirstName.getText();
            String lastName = tfLastName.getText();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dob = sdf.format(dateChooser.getDate());
            int age = Integer.parseInt(tfAge.getText());
            String gender = rmale.isSelected() ? "Male" : "Female";
            String phone = tfPhone.getText();
            String country = (String) comboCountry.getSelectedItem();
            String state = (String) comboState.getSelectedItem();
            String city = (String) comboCity.getSelectedItem();
            String address = tfAddress.getText();
            String aadhar = tfAadhar.getText();

            // Update database with new details
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/scribber", "root", "7586");
                String query = "UPDATE scribe SET first_name = ?, last_name = ?, dob = ?, age = ?, gender = ?, phone_number = ?, country = ?, state = ?, city = ?, address = ?, aadhar_number = ? WHERE username = ?";
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setString(1, firstName);
                pst.setString(2, lastName);
                pst.setString(3, dob);
                pst.setInt(4, age);
                pst.setString(5, gender);
                pst.setString(6, phone);
                pst.setString(7, country);
                pst.setString(8, state);
                pst.setString(9, city);
                pst.setString(10, address);
                pst.setString(11, aadhar);
                pst.setString(12, username);
                pst.executeUpdate();
                
                JOptionPane.showMessageDialog(null, "Profile Updated Successfully!");
                this.dispose();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error updating profile.");
            }

        } else if (ae.getSource() == back) {
            setVisible(false);
            new Dashboard(username); // Redirect to dashboard
        }
    }

    public static void main(String[] args) {
        new ScribeUpdateProfile("User");
    }
}

