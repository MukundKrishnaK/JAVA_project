package scribber;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ViewAcademicsDetails extends JFrame {
    JTextField tfInstitution, tfType, tfSpecification, tfStartDate, tfEndDate, tfExamCenter, tfExamCity, tfPaymentPerSubject, tfTotalPayment;
    JTextArea taSubjects;
    JLabel lblReceipt;
    String username;

    ViewAcademicsDetails(String username) {
        this.username = username;
        setBounds(350, 100, 1000, 650);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JLabel heading = new JLabel("Academic Details");
        heading.setBounds(200, 20, 200, 30);
        heading.setFont(new Font("Tahoma", Font.BOLD, 18));
        add(heading);

        addLabelAndField("Institution Name:", 50, 70, tfInstitution = new JTextField());
        addLabelAndField("Education Type:", 50, 110, tfType = new JTextField());
        addLabelAndField("Specification:", 50, 150, tfSpecification = new JTextField());
        addLabelAndTextArea("Subjects:", 50, 190, taSubjects = new JTextArea());
        addLabelAndField("Exam Start Date:", 50, 270, tfStartDate = new JTextField());
        addLabelAndField("Exam End Date:", 50, 310, tfEndDate = new JTextField());
        addLabelAndField("Exam Center:", 50, 350, tfExamCenter = new JTextField());
        addLabelAndField("Exam City:", 50, 390, tfExamCity = new JTextField());
        addLabelAndField("Payment Per Subject:", 50, 430, tfPaymentPerSubject = new JTextField());
        addLabelAndField("Total Payment:", 50, 470, tfTotalPayment = new JTextField());

        JButton back = new JButton("Back");
        back.setBounds(250, 540, 100, 30);
        back.addActionListener(e -> setVisible(false));
        add(back);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("images/exam1.jpg"));
        Image i2 = i1.getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(500, 70, 500, 400);
        add(image);
        
        loadAcademicDetails();
        setVisible(true);   
        
    }

    private void loadAcademicDetails() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/scribber", "root", "7586");
            String query = "SELECT * FROM academics WHERE username = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                tfInstitution.setText(rs.getString("institution_name"));
                tfType.setText(rs.getString("education_type"));
                tfSpecification.setText(rs.getString("specification"));
                taSubjects.setText(rs.getString("subjects"));
                tfStartDate.setText(rs.getString("exam_start_date"));
                tfEndDate.setText(rs.getString("exam_end_date"));
                tfExamCenter.setText(rs.getString("exam_center"));
                tfExamCity.setText(rs.getString("center_address"));
                
                
                int paymentPerSubject = 0;
                String educationType = rs.getString("education_type");

                switch (educationType) {
                case "School":
                       paymentPerSubject = 500;
                       break;
                case "Pre-University":
                       paymentPerSubject = 800;
                       break;
                case "UG":
                       paymentPerSubject = 1000;
                       break;
                case "PG":
                       paymentPerSubject = 1200;
                       break;
                default:
                       paymentPerSubject = 0;
}

                tfPaymentPerSubject.setText("Rs." + paymentPerSubject);
                
                String subjects = rs.getString("subjects");
                int subjectCount = subjects.split(",").length;
                int totalPayment = paymentPerSubject * subjectCount;
                tfTotalPayment.setText("Rs." + totalPayment);
            } else {
                JOptionPane.showMessageDialog(null, "No academic details found for this user.");
                setVisible(false);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading academic details.");
        }
    }

    private void addLabelAndField(String label, int x, int y, JTextField field) {
        JLabel lbl = new JLabel(label);
        lbl.setBounds(x, y, 150, 25);
        add(lbl);

        field.setBounds(200, y, 300, 25);
        field.setEditable(false);
        add(field);
    }

    private void addLabelAndTextArea(String label, int x, int y, JTextArea textArea) {
        JLabel lbl = new JLabel(label);
        lbl.setBounds(x, y, 150, 25);
        add(lbl);

        textArea.setBounds(200, y, 300, 75);
        textArea.setEditable(false);
        add(textArea);
    }

    public static void main(String[] args) {
        new ViewAcademicsDetails("User");
    }
}
