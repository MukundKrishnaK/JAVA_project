package scribber;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class ViewProfile extends JFrame {
    JTextField tfFirstName, tfLastName, tfAge, tfPhone, tfAddress, tfAadhar;
    JComboBox<String> comboCountry, comboState, comboCity;
    JRadioButton rmale, rfemale;
    JLabel lblUsername;
    String username;
    JLabel lblUsernameLabel;

    ViewProfile(String username) {
        this.username = username; // Save username for future use
        setBounds(500, 100, 850, 650);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(0, 0, 800, 600);
        mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "View Profile", 0, 0, new Font("Tahoma", Font.BOLD, 16)));
        mainPanel.setBackground(Color.WHITE);
        add(mainPanel);

        // Label for "Username"
        lblUsernameLabel = new JLabel("Username: ");
        lblUsernameLabel.setBounds(30, 50, 150, 25);
        mainPanel.add(lblUsernameLabel);

        // TextField to display the username
        JTextField tfUsername = new JTextField(username);
        tfUsername.setBounds(220, 50, 150, 25);
        tfUsername.setEditable(false); // Make the text field non-editable
        mainPanel.add(tfUsername);

        // Label and TextField for "First Name"
        JLabel lblFirstName = new JLabel("First Name:");
        lblFirstName.setBounds(30, 90, 150, 25);
        mainPanel.add(lblFirstName);

        tfFirstName = new JTextField();
        tfFirstName.setBounds(220, 90, 150, 25);
        tfFirstName.setEditable(false); // Set as non-editable
        mainPanel.add(tfFirstName);

        // Label and TextField for "Last Name"
        JLabel lblLastName = new JLabel("Last Name:");
        lblLastName.setBounds(30, 130, 150, 25);
        mainPanel.add(lblLastName);

        tfLastName = new JTextField();
        tfLastName.setBounds(220, 130, 150, 25);
        tfLastName.setEditable(false); // Set as non-editable
        mainPanel.add(tfLastName);

        // Label and TextField for "Date of Birth"
        JLabel lblDob = new JLabel("Date of Birth:");
        lblDob.setBounds(30, 170, 150, 25);
        mainPanel.add(lblDob);

        JTextField tfDob = new JTextField();
        tfDob.setBounds(220, 170, 150, 25);
        tfDob.setEditable(false); // Set as non-editable
        mainPanel.add(tfDob);

        // Label and TextField for "Age"
        JLabel lblAge = new JLabel("Age:");
        lblAge.setBounds(30, 210, 150, 25);
        mainPanel.add(lblAge);

        tfAge = new JTextField();
        tfAge.setBounds(220, 210, 150, 25);
        tfAge.setEditable(false); // Set as non-editable
        mainPanel.add(tfAge);

        // Label and Radio Buttons for "Gender"
        JLabel lblGender = new JLabel("Gender:");
        lblGender.setBounds(30, 250, 150, 25);
        mainPanel.add(lblGender);

        rmale = new JRadioButton("Male");
        rmale.setBounds(220, 250, 70, 25);
        rmale.setBackground(Color.WHITE);
        rmale.setEnabled(false); // Set as non-editable
        mainPanel.add(rmale);

        rfemale = new JRadioButton("Female");
        rfemale.setBounds(300, 250, 70, 25);
        rfemale.setBackground(Color.WHITE);
        rfemale.setEnabled(false); // Set as non-editable
        mainPanel.add(rfemale);

        // Button Group for Gender Radio Buttons
        ButtonGroup bg = new ButtonGroup();
        bg.add(rmale);
        bg.add(rfemale);

        // Label and TextField for "Phone No"
        JLabel lblPhone = new JLabel("Phone No:");
        lblPhone.setBounds(30, 290, 150, 25);
        mainPanel.add(lblPhone);

        tfPhone = new JTextField();
        tfPhone.setBounds(220, 290, 150, 25);
        tfPhone.setEditable(false); // Set as non-editable
        mainPanel.add(tfPhone);

        // Label and ComboBox for "Country"
        JLabel lblCountry = new JLabel("Country:");
        lblCountry.setBounds(30, 330, 150, 25);
        mainPanel.add(lblCountry);

        comboCountry = new JComboBox<>(new String[]{"India"});
        comboCountry.setBounds(220, 330, 150, 25);
        comboCountry.setEnabled(false); // Set as non-editable
        mainPanel.add(comboCountry);

        // Label and ComboBox for "State"
        JLabel lblState = new JLabel("State:");
        lblState.setBounds(30, 370, 150, 25);
        mainPanel.add(lblState);

        comboState = new JComboBox<>(new String[]{"Karnataka", "Maharashtra", "Tamil Nadu", "Delhi"});
        comboState.setBounds(220, 370, 150, 25);
        comboState.setEnabled(false); // Set as non-editable
        mainPanel.add(comboState);

        // Label and ComboBox for "City"
        JLabel lblCity = new JLabel("City:");
        lblCity.setBounds(30, 410, 150, 25);
        mainPanel.add(lblCity);

        comboCity = new JComboBox<>(new String[]{"Bangalore", "Mumbai", "Chennai", "Delhi"});
        comboCity.setBounds(220, 410, 150, 25);
        comboCity.setEnabled(false); // Set as non-editable
        mainPanel.add(comboCity);

        // Label and TextField for "Address"
        JLabel lblAddress = new JLabel("Address:");
        lblAddress.setBounds(30, 450, 150, 25);
        mainPanel.add(lblAddress);

        tfAddress = new JTextField();
        tfAddress.setBounds(220, 450, 150, 25);
        tfAddress.setEditable(false); // Set as non-editable
        mainPanel.add(tfAddress);

        // Label and TextField for "Aadhar Number"
        JLabel lblAadhar = new JLabel("Aadhar Number:");
        lblAadhar.setBounds(30, 490, 150, 25);
        mainPanel.add(lblAadhar);

        tfAadhar = new JTextField();
        tfAadhar.setBounds(220, 490, 150, 25);
        tfAadhar.setEditable(false); // Set as non-editable
        mainPanel.add(tfAadhar);

        // Fetch the student details from the database and populate the fields
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/scribber", "root", "7586");
            String query = "SELECT * FROM students WHERE username = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                tfFirstName.setText(rs.getString("first_name"));
                tfLastName.setText(rs.getString("last_name"));
                tfDob.setText(rs.getString("dob"));
                tfAge.setText(String.valueOf(rs.getInt("age")));
                if (rs.getString("gender").equals("Male")) {
                    rmale.setSelected(true);
                } else {
                    rfemale.setSelected(true);
                }
                tfPhone.setText(rs.getString("phone_number"));
                comboCountry.setSelectedItem(rs.getString("country"));
                comboState.setSelectedItem(rs.getString("state"));
                comboCity.setSelectedItem(rs.getString("city"));
                tfAddress.setText(rs.getString("address"));
                tfAadhar.setText(rs.getString("aadhar_number"));
            } else {
                JOptionPane.showMessageDialog(null, "Profile not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching profile data.");
        }

        setVisible(true);
    }

    public static void main(String[] args) {
        new ViewProfile("User");  // Replace "User" with the username to view the profile
    }
}
