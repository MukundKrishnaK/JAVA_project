package scribber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Initial_Login extends JFrame implements ActionListener {
    JButton studentLogin, scribeLogin, adminLogin;

    Initial_Login() {
        setTitle("Welcome to Scribber");
        setBounds(450, 150, 700, 450);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Gradient Background Panel
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(0, 90, 180), getWidth(), getHeight(), new Color(80, 170, 250));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setBounds(0, 0, 700, 450);
        backgroundPanel.setLayout(null);
        add(backgroundPanel);

        JLabel title = new JLabel("Welcome to Scribber", JLabel.CENTER);
        title.setFont(new Font("Tahoma", Font.BOLD, 26));
        title.setForeground(Color.WHITE);
        title.setBounds(180, 40, 350, 40);
        backgroundPanel.add(title);

        // Panel for Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 15, 20));
        buttonPanel.setBounds(200, 120, 300, 200);
        buttonPanel.setBackground(new Color(0, 0, 0, 0)); // Transparent
        backgroundPanel.add(buttonPanel);

        studentLogin = createButton("Student Login");
        scribeLogin = createButton("Scribe Login");
        adminLogin = createButton("Admin Login");

        buttonPanel.add(studentLogin);
        buttonPanel.add(scribeLogin);
        buttonPanel.add(adminLogin);

        studentLogin.addActionListener(this);
        scribeLogin.addActionListener(this);
        adminLogin.addActionListener(this);

        setVisible(true);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Tahoma", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(30, 144, 255));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusable(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(250, 50));
        
        // Button hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(70, 160, 255));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(30, 144, 255));
            }
        });

        return button;
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == studentLogin) {
            new Login();
            setVisible(false);
        } else if (ae.getSource() == scribeLogin) {
            new SLogin();
            setVisible(false);
        } else if (ae.getSource() == adminLogin) {
            new Adminlogin();
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new Initial_Login();
    }
}
