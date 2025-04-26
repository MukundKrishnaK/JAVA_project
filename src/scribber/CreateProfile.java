package scribber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.toedter.calendar.JDateChooser;
import java.sql.*;
import java.text.SimpleDateFormat;

public class CreateProfile extends JFrame implements ActionListener {
    JTextField tfFirstName, tfLastName, tfAge, tfPhone, tfAddress, tfAadhar;
    JComboBox<String> comboCountry, comboState, comboCity;
    JRadioButton rmale, rfemale;
    JButton submit, back;
    JDateChooser dateChooser;
    String username;
    JLabel lblUsername;

    CreateProfile(String username) {
        this.username = username; // Save username for future use
        setBounds(500, 100, 850, 650);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(0, 0, 800, 600);
        mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Create Profile", 0, 0, new Font("Tahoma", Font.BOLD, 16)));
        mainPanel.setBackground(Color.WHITE);
        add(mainPanel);

                // Label for "Username"
        JLabel lblUsernameLabel = new JLabel("Username: ");
        lblUsernameLabel.setBounds(30, 50, 150, 25);
        mainPanel.add(lblUsernameLabel);

        // TextField to display the username
        JTextField tfUsername = new JTextField(username);
        tfUsername.setBounds(220, 50, 150, 25);
        tfUsername.setEditable(false); // Make the text field non-editable
        mainPanel.add(tfUsername);

        JLabel lblFirstName = new JLabel("First Name:");
        lblFirstName.setBounds(30, 90, 150, 25);
        mainPanel.add(lblFirstName);

        tfFirstName = new JTextField();
        tfFirstName.setBounds(220, 90, 150, 25);
        mainPanel.add(tfFirstName);
        tfFirstName.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (Character.isDigit(e.getKeyChar())) {
                    e.consume();
                }
            }
        });

        JLabel lblLastName = new JLabel("Last Name:");
        lblLastName.setBounds(30, 130, 150, 25);
        mainPanel.add(lblLastName);

        tfLastName = new JTextField();
        tfLastName.setBounds(220, 130, 150, 25);
        mainPanel.add(tfLastName);
        tfLastName.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (Character.isDigit(e.getKeyChar())) {
                    e.consume();
                }
            }
        });

        JLabel lblDob = new JLabel("Date of Birth:");
        lblDob.setBounds(30, 170, 150, 25);
        mainPanel.add(lblDob);

        dateChooser = new JDateChooser();
        dateChooser.setBounds(220, 170, 150, 25);
        mainPanel.add(dateChooser);

        JLabel lblAge = new JLabel("Age:");
        lblAge.setBounds(30, 210, 150, 25);
        mainPanel.add(lblAge);

        tfAge = new JTextField();
        tfAge.setBounds(220, 210, 150, 25);
        mainPanel.add(tfAge);
        tfAge.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar())) {
                    e.consume();
                }
            }
        });

        JLabel lblGender = new JLabel("Gender:");
        lblGender.setBounds(30, 250, 150, 25);
        mainPanel.add(lblGender);

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

        JLabel lblPhone = new JLabel("Phone No:");
        lblPhone.setBounds(30, 290, 150, 25);
        mainPanel.add(lblPhone);

        tfPhone = new JTextField();
        tfPhone.setBounds(220, 290, 150, 25);
        mainPanel.add(tfPhone);
        tfPhone.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar())) {
                    e.consume();
                }
            }
        });

        JLabel lblCountry = new JLabel("Country:");
        lblCountry.setBounds(30, 330, 150, 25);
        mainPanel.add(lblCountry);

        comboCountry = new JComboBox<>(new String[]{"India"});
        comboCountry.setBounds(220, 330, 150, 25);
        mainPanel.add(comboCountry);

        JLabel lblState = new JLabel("State:");
        lblState.setBounds(30, 370, 150, 25);
        mainPanel.add(lblState);

        comboState = new JComboBox<>(new String[]{"Karnataka", "Maharashtra", "Tamil Nadu", "Delhi"});
        comboState.setBounds(220, 370, 150, 25);
        mainPanel.add(comboState);

        JLabel lblCity = new JLabel("City:");
        lblCity.setBounds(30, 410, 150, 25);
        mainPanel.add(lblCity);

        comboCity = new JComboBox<>(new String[]{"Bangalore", "Mumbai", "Chennai", "Delhi"});
        comboCity.setBounds(220, 410, 150, 25);
        mainPanel.add(comboCity);

        JLabel lblAddress = new JLabel("Address:");
        lblAddress.setBounds(30, 450, 150, 25);
        mainPanel.add(lblAddress);

        tfAddress = new JTextField();
        tfAddress.setBounds(220, 450, 150, 25);
        mainPanel.add(tfAddress);

        JLabel lblAadhar = new JLabel("Aadhar Number:");
        lblAadhar.setBounds(30, 490, 150, 25);
        mainPanel.add(lblAadhar);

        tfAadhar = new JTextField();
        tfAadhar.setBounds(220, 490, 150, 25);
        mainPanel.add(tfAadhar);
        tfAadhar.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar())) {
                    e.consume();
                }
            }
        });

        submit = new JButton("Submit");
        submit.setBounds(70, 530, 100, 30);
        submit.addActionListener(this);
        mainPanel.add(submit);

        back = new JButton("Back");
        back.setBounds(220, 530, 100, 30);
        back.addActionListener(this);
        mainPanel.add(back);

        setVisible(true);
        
        /*ImageIcon i1 = new ImageIcon(getClass().getResource("/images/create.png"));
if (i1.getIconWidth() == -1) {
    JOptionPane.showMessageDialog(null, "Image not found!");
} else {
    // Scale the image
    Image i2 = i1.getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT);
    ImageIcon i3 = new ImageIcon(i2);
    
    // Create JLabel for the image
    JLabel image = new JLabel(i3);
    
    // Adjust the bounds to ensure it's aligned correctly
    image.setBounds(50, 30, 300, 300);  // Set the size and position
    
    // Add the image to the main panel
    mainPanel.add(image);
}*/

    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submit) {
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

            // Add database insertion logic here
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/scribber", "root", "7586");
                String query = "INSERT INTO students (first_name, last_name, dob, age, gender, phone_number, country, state, city, address, aadhar_number, username) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
                pst.setString(12, username); // Including the username in the database
                pst.executeUpdate();
                
                JOptionPane.showMessageDialog(null, "Profile Created Successfully!");
                this.dispose();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error in profile creation.");
            }

        } else if (ae.getSource() == back) {
            setVisible(false); // Pass the username to the Dashboard
        }
    }

    public static void main(String[] args) {
        new CreateProfile("User");
    }
}
