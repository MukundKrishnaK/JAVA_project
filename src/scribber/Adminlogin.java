/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scribber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Adminlogin extends JFrame implements ActionListener {

    JButton login, cancel;
    JTextField tfusername;
    JPasswordField tfpassword;
    
      public Adminlogin() {
         setBounds(350, 200, 600, 300);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JPanel p1 = new JPanel();
        p1.setBackground(new Color(133, 193, 233));
        p1.setBounds(0, 0, 600, 300);
        p1.setLayout(null);
        add(p1);

        JLabel lblusername = new JLabel("Username");
        lblusername.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblusername.setBounds(100, 40, 125, 25);
        p1.add(lblusername);

        tfusername = new JTextField();
        tfusername.setBounds(230, 40, 180, 25);
        tfusername.setBorder(BorderFactory.createEmptyBorder());
        p1.add(tfusername);

        JLabel lblpassword = new JLabel("Password");
        lblpassword.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblpassword.setBounds(100, 80, 125, 25);
        p1.add(lblpassword);
        
         tfpassword = new JPasswordField();
        tfpassword.setBounds(230, 80, 180, 25);
        tfpassword.setBorder(BorderFactory.createEmptyBorder());
        p1.add(tfpassword);

        login = new JButton("Login");
        login.setBackground(Color.WHITE);
        login.setForeground(new Color(133, 193, 233));
        login.setFont(new Font("Tahoma", Font.BOLD, 14));
        login.setBounds(150, 150, 100, 30);
        login.addActionListener(this);
        p1.add(login);

        cancel = new JButton("Cancel");
        cancel.setBackground(Color.WHITE);
        cancel.setForeground(new Color(133, 193, 233));
        cancel.setFont(new Font("Tahoma", Font.BOLD, 14));
        cancel.setBounds(300, 150, 100, 30);
        cancel.addActionListener(this);
        p1.add(cancel);

        setVisible(true);
    }
      public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == login) {
            String username = tfusername.getText();
            String password = new String(tfpassword.getPassword());

            String query = "select * from adlogin where username = '" + username + "' and password = '" + password + "'";
            try {
                Conn c = new Conn();
                ResultSet rs = c.s.executeQuery(query);

                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "Logged in successfully");
                    setVisible(false);
                    new AdminDashboard(username); // Redirect to the dashboard page
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == cancel) {
            setVisible(false);
            new Login();
        }
    }
 public static void main(String[] args) {
        new Adminlogin();
    }
    
    
}
