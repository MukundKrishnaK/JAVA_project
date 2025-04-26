package scribber;

import javax.swing.*;
import java.awt.*;
import javax.swing.ImageIcon;

public class Scribber {
    private static JFrame loginFrame;

    public static void main(String[] args) {
        // Create Welcome Frame with a full-screen welcome message
        JFrame welcomeFrame = new JFrame("Welcome");
        JLabel welcomeLabel = new JLabel("Welcome to Scribe", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 32));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeFrame.getContentPane().setBackground(new Color(30, 144, 255)); // Deep Sky Blue
        welcomeFrame.add(welcomeLabel);
        welcomeFrame.setSize(600, 400);
        welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        welcomeFrame.setLocationRelativeTo(null);
        welcomeFrame.setVisible(true);
        
        // Timer to switch to login frame
        new Timer(3000, e -> {
            welcomeFrame.dispose();
            openLoginFrame();
        }).start();
    }
    
    public static void openLoginFrame() {
        if (loginFrame == null) {  // Ensure the frame is only created once
            loginFrame = new JFrame("Login");
            loginFrame.setSize(700, 500);
            loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            loginFrame.setLayout(new BorderLayout());
            loginFrame.getContentPane().setBackground(new Color(70, 130, 180)); // Steel Blue

            // Left Panel with Buttons
            JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
            buttonPanel.setBackground(new Color(0, 0, 128)); // Navy Blue
            
            JButton userLogin = new JButton("User Login");
            JButton employeeLogin = new JButton("Employee Login");
            JButton adminLogin = new JButton("Admin Login");
            
            // Styling buttons
            userLogin.setBackground(new Color(50, 205, 50)); // Lime Green
            userLogin.setForeground(Color.WHITE);
            userLogin.setFont(new Font("Arial", Font.BOLD, 18));
            
            employeeLogin.setBackground(new Color(255, 140, 0)); // Dark Orange
            employeeLogin.setForeground(Color.WHITE);
            employeeLogin.setFont(new Font("Arial", Font.BOLD, 18));
            
            adminLogin.setBackground(new Color(220, 20, 60)); // Crimson
            adminLogin.setForeground(Color.WHITE);
            adminLogin.setFont(new Font("Arial", Font.BOLD, 18));
            
            buttonPanel.add(userLogin);
            buttonPanel.add(employeeLogin);
            buttonPanel.add(adminLogin);
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // Right Panel with Centered Welcome Message
            JPanel messagePanel = new JPanel();
            messagePanel.setBackground(new Color(176, 224, 230)); // Light Blue
            
            JLabel messageLabel = new JLabel("Welcome to Scribe", SwingConstants.CENTER);
            messageLabel.setFont(new Font("Arial", Font.BOLD, 32));
            messageLabel.setForeground(Color.BLACK);
            messagePanel.add(messageLabel);
            
            // Set the layout to divide the frame into two parts
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new GridLayout(1, 2)); // Divide into two parts
            mainPanel.add(buttonPanel);
            mainPanel.add(messagePanel);

            loginFrame.add(mainPanel, BorderLayout.CENTER); // Add the mainPanel to the center

            loginFrame.setLocationRelativeTo(null);
            loginFrame.setVisible(true);
        }
    }
}
