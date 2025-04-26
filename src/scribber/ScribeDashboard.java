/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scribber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ScribeDashboard extends JFrame implements ActionListener {
    
    String username;
    JButton createProfile, updateProfile, viewProfile, academicsDetails, updateAcademicsDetails, viewAcademicsDetails, payments,bankDetails, status, logout;
    
    ScribeDashboard(String username) {
        this.username = username;
        setBounds(0, 0, 1600, 1000);
        setVisible(true);
        setLayout(null);

        // Top Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(null);
        headerPanel.setBackground(new Color(39, 48, 73));
        headerPanel.setBounds(0, 0, 1600, 80);
        add(headerPanel);

        JLabel heading = new JLabel("Welcome, " + username);
        heading.setBounds(30, 20, 500, 40);
        heading.setForeground(Color.WHITE);
        heading.setFont(new Font("Arial", Font.BOLD, 30));
        headerPanel.add(heading);

        // Sidebar Panel for Navigation
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setLayout(null);
        sidebarPanel.setBackground(new Color(45, 52, 54));
        sidebarPanel.setBounds(0, 80, 300, 920);
        add(sidebarPanel);

        // Buttons
        createProfile = createButton("Create Profile", 30, sidebarPanel);
        updateProfile = createButton("Update Profile", 80, sidebarPanel);
        viewProfile = createButton("View Profile", 130, sidebarPanel);
        academicsDetails = createButton("Academics Details", 180, sidebarPanel);
        updateAcademicsDetails = createButton("Update Academics Details", 230, sidebarPanel);
        viewAcademicsDetails = createButton("View Academics Details", 280, sidebarPanel);
        bankDetails= createButton("Bank Details",330,sidebarPanel);
        payments = createButton("Payments", 380, sidebarPanel);
        status = createButton("Status", 430, sidebarPanel);
        logout = createButton("Logout", 480, sidebarPanel, new Color(255, 87, 34));

        // Main Content Area
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(null);
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBounds(300, 80, 1300, 920);
        add(contentPanel);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("images/home1.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1700, 1000, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel icon = new JLabel(i3);
        icon.setBounds(0, 0, 1100, 660);
        contentPanel.add(icon);
        
        /*ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("images/home1.jpg"));
        Image i2 = i1.getImage();

        // Calculate the scaling factor to maintain the aspect ratio
        double scaleWidth = 1300.0 / i1.getIconWidth(); // Panel width divided by image width
        double scaleHeight = 920.0 / i1.getIconHeight(); // Panel height divided by image height

        // Use the smaller scaling factor to prevent distortion
        double scaleFactor = Math.min(scaleWidth, scaleHeight);

        // Scale the image to fit within the panel while maintaining the aspect ratio
        int newWidth = (int) (i1.getIconWidth() * scaleFactor);
        int newHeight = (int) (i1.getIconHeight() * scaleFactor);

        Image i3 = i2.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ImageIcon i4 = new ImageIcon(i3);

        // Add the image to the panel
        JLabel icon = new JLabel(i4);
        icon.setBounds((1300 - newWidth) / 2, (920 - newHeight) / 2, newWidth, newHeight);  // Center the image
        contentPanel.add(icon);*/

        setVisible(true);
    }
    
    private JButton createButton(String text, int y, JPanel panel) {
        return createButton(text, y, panel, new Color(67, 160, 71));
    }
    
    private JButton createButton(String text, int y, JPanel panel, Color bgColor) {
        JButton button = new JButton(text);
        button.setBounds(10, y, 280, 40);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.addActionListener(this);
        panel.add(button);
        return button;
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == createProfile) {
            new ScribeCreateProfile(username);
        } else if (ae.getSource() == updateProfile) {
            new ScribeUpdateProfile(username);
        } else if (ae.getSource() == viewProfile) {
            new ScribeViewProfile(username);
        } else if (ae.getSource() == academicsDetails) {
            new ScribeAcademicsDetails(username);
        } else if (ae.getSource() == updateAcademicsDetails) {
            new ScribeUpdateAcademicsDetails(username);
        } else if (ae.getSource() == viewAcademicsDetails) {
            new ScribeViewAcademicsDetails(username);
        } else if (ae.getSource() == payments) {
            new ScribePaymentAck(username);
        }  else if (ae.getSource() == bankDetails) {
            new BankDetails(username);
        }else if (ae.getSource() == status) {
           SwingUtilities.invokeLater(() -> new ScribeStatus(username).setVisible(true));
        } else if (ae.getSource() == logout) {
            this.dispose();
            new Initial_Login();
        }
    }

    public static void main(String[] args) {
        new ScribeDashboard("ScribeUser");
    }
}

