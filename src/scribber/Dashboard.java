package scribber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Dashboard extends JFrame implements ActionListener {

    String username;
    JButton createProfile, updateProfile, viewProfile, academicsDetails, updateAcademicsDetails, viewAcademicsDetails, healthReport, viewhealthReport, payments, status, acknowledgement, logout;

    Dashboard(String username) {
        this.username = username;
        setBounds(100, 50, 1400, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Top Header Panel with Gradient
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(30, 40, 60), getWidth(), 0, new Color(60, 80, 120));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerPanel.setLayout(null);
        headerPanel.setBounds(0, 0, 1400, 80);
        add(headerPanel);

        JLabel heading = new JLabel("Welcome, " + username);
        heading.setBounds(30, 20, 500, 40);
        heading.setForeground(Color.WHITE);
        heading.setFont(new Font("Tahoma", Font.BOLD, 28));
        headerPanel.add(heading);

        // Sidebar Panel with Gradient
        JPanel sidebarPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(50, 60, 80), 0, getHeight(), new Color(80, 100, 130));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        sidebarPanel.setLayout(null);
        sidebarPanel.setBounds(0, 80, 250, 720);
        add(sidebarPanel);

        // Sidebar Buttons
        createProfile = createButton("Create Profile", sidebarPanel, 30);
        updateProfile = createButton("Update Profile", sidebarPanel, 80);
        viewProfile = createButton("View Profile", sidebarPanel, 130);
        academicsDetails = createButton("Academics Details", sidebarPanel, 180);
        updateAcademicsDetails = createButton("Update Academics Details", sidebarPanel, 230);
        viewAcademicsDetails = createButton("View Academics Details", sidebarPanel, 280);
        healthReport = createButton("Health Report", sidebarPanel, 330);
        viewhealthReport = createButton("View Health Report", sidebarPanel, 380);
        status = createButton("Status", sidebarPanel, 430);
        payments = createButton("Payments", sidebarPanel, 480);
        acknowledgement = createButton("Acknowledgement", sidebarPanel, 530);
        logout = createButton("Logout", sidebarPanel, 580, new Color(200, 40, 40));

        // Main Content Panel (White Background)
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(null);
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBounds(250, 80, 1150, 720);
        add(contentPanel);

        JLabel contentText = new JLabel("", JLabel.CENTER);
        contentText.setFont(new Font("Tahoma", Font.PLAIN, 22));
        contentText.setForeground(new Color(50, 50, 50));
        contentText.setBounds(300, 200, 700, 50);
        contentPanel.add(contentText);

        setVisible(true);
    }

    private JButton createButton(String text, JPanel panel, int yPosition) {
        return createButton(text, panel, yPosition, new Color(67, 160, 71));
    }

    private JButton createButton(String text, JPanel panel, int yPosition, Color color) {
        JButton button = new JButton(text);
        button.setBounds(20, yPosition, 210, 40);
        button.setFont(new Font("Tahoma", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.brighter());
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });

        button.addActionListener(this);
        panel.add(button);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == createProfile) {
            new CreateProfile(username);
        } else if (ae.getSource() == updateProfile) {
            new UpdateProfile(username);
        } else if (ae.getSource() == viewProfile) {
            new ViewProfile(username);
        } else if (ae.getSource() == academicsDetails) {
            new AcademicsDetails(username);
        } else if (ae.getSource() == updateAcademicsDetails) {
            new UpdateAcademicsDetails(username);
        } else if (ae.getSource() == viewAcademicsDetails) {
            new ViewAcademicsDetails(username);
        } else if (ae.getSource() == healthReport) {
            new HealthReport(username);
        } else if (ae.getSource() == viewhealthReport) {
            new ViewHealthReport(username);
        } else if (ae.getSource() == status) {
            new Status(username);
        } else if (ae.getSource() == acknowledgement) {
            new Acknowledgement(username);
        } else if (ae.getSource() == payments) {
            new Payments(username);
        } else if (ae.getSource() == logout) {
            this.dispose();
            new Initial_Login();
        }
    }

    public static void main(String[] args) {
        new Dashboard("User");
    }
}
