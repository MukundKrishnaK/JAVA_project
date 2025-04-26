package scribber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdminDashboard extends JFrame implements ActionListener {
    
    String username;
    JButton viewStudents, deleteStudents, viewScribe, deleteScribe, assignScribe,scribeStudent,medicalReport, bankDetails,paidScribe, scribePayments,payments, logout;
    
    AdminDashboard(String adminUsername) {
        this.username = adminUsername;
        setBounds(0, 0, 1600, 1000);
        setVisible(true);
        setLayout(null);

        // Top Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(null);
        headerPanel.setBackground(new Color(39, 48, 73));
        headerPanel.setBounds(0, 0, 1600, 80);
        add(headerPanel);

        JLabel heading = new JLabel("Admin Dashboard - " + username);
        heading.setBounds(30, 20, 500, 40);
        heading.setForeground(Color.WHITE);
        heading.setFont(new Font("Arial", Font.BOLD, 30));
        headerPanel.add(heading);

        // Sidebar Panel
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setLayout(null);
        sidebarPanel.setBackground(new Color(45, 52, 54));
        sidebarPanel.setBounds(0, 80, 300, 920);
        add(sidebarPanel);

        // Buttons
        viewStudents = new JButton("View Students");
        viewStudents.setBounds(10, 30, 280, 40);
        viewStudents.setBackground(new Color(67, 160, 71));
        viewStudents.setForeground(Color.WHITE);
        viewStudents.setFont(new Font("Arial", Font.BOLD, 18));
        viewStudents.addActionListener(this);
        sidebarPanel.add(viewStudents);

        deleteStudents = new JButton("Delete Students");
        deleteStudents.setBounds(10, 80, 280, 40);
        deleteStudents.setBackground(new Color(67, 160, 71));
        deleteStudents.setForeground(Color.WHITE);
        deleteStudents.setFont(new Font("Arial", Font.BOLD, 18));
        deleteStudents.addActionListener(this);
        sidebarPanel.add(deleteStudents);

        viewScribe = new JButton("View Scribe");
        viewScribe.setBounds(10, 130, 280, 40);
        viewScribe.setBackground(new Color(67, 160, 71));
        viewScribe.setForeground(Color.WHITE);
        viewScribe.setFont(new Font("Arial", Font.BOLD, 18));
        viewScribe.addActionListener(this);
        sidebarPanel.add(viewScribe);

        deleteScribe = new JButton("Delete Scribe");
        deleteScribe.setBounds(10, 180, 280, 40);
        deleteScribe.setBackground(new Color(67, 160, 71));
        deleteScribe.setForeground(Color.WHITE);
        deleteScribe.setFont(new Font("Arial", Font.BOLD, 18));
        deleteScribe.addActionListener(this);
        sidebarPanel.add(deleteScribe);

        assignScribe = new JButton("Assign Scribe");
        assignScribe.setBounds(10, 230, 280, 40);
        assignScribe.setBackground(new Color(67, 160, 71));
        assignScribe.setForeground(Color.WHITE);
        assignScribe.setFont(new Font("Arial", Font.BOLD, 18));
        assignScribe.addActionListener(this);
        sidebarPanel.add(assignScribe);

        scribeStudent = new JButton("Scribe - Student");
        scribeStudent.setBounds(10, 280, 280, 40);
        scribeStudent.setBackground(new Color(67, 160, 71));
        scribeStudent.setForeground(Color.WHITE);
        scribeStudent.setFont(new Font("Arial", Font.BOLD, 18));
        scribeStudent.addActionListener(this);
        sidebarPanel.add(scribeStudent);

        medicalReport = new JButton("Student Medical Report");
        medicalReport.setBounds(10, 330, 280, 40);
        medicalReport.setBackground(new Color(67, 160, 71));
        medicalReport.setForeground(Color.WHITE);
        medicalReport.setFont(new Font("Arial", Font.BOLD, 18));
        medicalReport.addActionListener(this);
        sidebarPanel.add(medicalReport);
        
        payments = new JButton("Student Payments");
        payments.setBounds(10, 380, 280, 40);
        payments.setBackground(new Color(67, 160, 71));
        payments.setForeground(Color.WHITE);
        payments.setFont(new Font("Arial", Font.BOLD, 18));
        payments.addActionListener(this);
        sidebarPanel.add(payments);
        
        bankDetails = new JButton("Bank Details");
        bankDetails.setBounds(10, 430, 280, 40);
        bankDetails.setBackground(new Color(67, 160, 71));
        bankDetails.setForeground(Color.WHITE);
        bankDetails.setFont(new Font("Arial", Font.BOLD, 18));
        bankDetails.addActionListener(this);
        sidebarPanel.add(bankDetails);
        
        scribePayments = new JButton("Scribe Payments");
        scribePayments.setBounds(10, 480, 280, 40);
        scribePayments.setBackground(new Color(67, 160, 71));
        scribePayments.setForeground(Color.WHITE);
        scribePayments.setFont(new Font("Arial", Font.BOLD, 18));
        scribePayments.addActionListener(this);
        sidebarPanel.add(scribePayments);
        
        paidScribe = new JButton("Paid Scribes");
        paidScribe.setBounds(10, 530, 280, 40);
        paidScribe.setBackground(new Color(67, 160, 71));
        paidScribe.setForeground(Color.WHITE);
        paidScribe.setFont(new Font("Arial", Font.BOLD, 18));
        paidScribe.addActionListener(this);
        sidebarPanel.add(paidScribe);
        
        logout = new JButton("Logout");
        logout.setBounds(10, 580, 280, 40);
        logout.setBackground(new Color(255, 87, 34));
        logout.setForeground(Color.WHITE);
        logout.setFont(new Font("Arial", Font.BOLD, 18));
        logout.addActionListener(this);
        sidebarPanel.add(logout);


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
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == viewStudents) {
            new ViewStudents(username);
        } else if (ae.getSource() == deleteStudents) {
            new DeleteStudent(username);
        } else if (ae.getSource() == viewScribe) {
            new ViewScribe(username);
        } else if (ae.getSource() == deleteScribe) {
            new DeleteScribe(username);
        } else if (ae.getSource() == assignScribe) {    
          SwingUtilities.invokeLater(() -> new AssignScribe(username).setVisible(true));
        } else if (ae.getSource() == bankDetails) {    
          SwingUtilities.invokeLater(() -> new ScribeBankDetails(username).setVisible(true));
        }else if (ae.getSource() == paidScribe) {    
          SwingUtilities.invokeLater(() -> new PaidScribe(username).setVisible(true));
        }else if (ae.getSource() == scribePayments) {    
          SwingUtilities.invokeLater(() -> new ScribePayments(username).setVisible(true));
        }else if (ae.getSource() == medicalReport) {
            new AdminMedicalReport(username);
        } else if (ae.getSource() == payments) {
            SwingUtilities.invokeLater(() -> new AdminPayments(username).setVisible(true));
        }else if (ae.getSource() == scribeStudent) {
           SwingUtilities.invokeLater(() -> new ScribeStudent(username).setVisible(true));
        }else if (ae.getSource() == logout) {
            this.dispose();
            new Initial_Login();
        }
    }

    public static void main(String[] args) {
        new AdminDashboard("User");
    }
}
