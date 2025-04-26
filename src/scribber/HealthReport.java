package scribber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import com.toedter.calendar.JDateChooser;
import java.io.File;
import java.io.FileInputStream;

public class HealthReport extends JFrame {
    JTextField tfDisabilityType, tfAffectedParts, tfCause, tfCertificateNumber, tfIssuingAuthority, tfHospitalName, tfDoctorName, tfHospitalNumber;
    JRadioButton rbMild, rbModerate, rbSevere;
    ButtonGroup severityGroup;
    JButton submit, back, uploadFile;
    JLabel lblFilePath;
    JDateChooser dateChooser;
    String username;

    HealthReport(String username) {
        this.username = username;
        setBounds(350, 50, 950, 700);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(20, 20, 900, 580);
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Health Report", 0, 0, new Font("Tahoma", Font.BOLD, 16)));
        panel.setBackground(Color.WHITE);
        add(panel);
        
        JLabel heading1 = new JLabel("Disability Description");
        heading1.setBounds(50, 30, 300, 30);
        heading1.setFont(new Font("Tahoma", Font.BOLD, 16));
        panel.add(heading1);
        
        addLabelAndField("Type of Disability:", 50, 70, tfDisabilityType = new JTextField(), panel);
        
        JLabel lblSeverity = new JLabel("Severity of Disability:");
        lblSeverity.setBounds(50, 110, 150, 25);
        panel.add(lblSeverity);
        
        rbMild = new JRadioButton("Mild");
        rbModerate = new JRadioButton("Moderate");
        rbSevere = new JRadioButton("Severe");
        severityGroup = new ButtonGroup();
        severityGroup.add(rbMild);
        severityGroup.add(rbModerate);
        severityGroup.add(rbSevere);
        
        rbMild.setBounds(200, 110, 80, 25);
        rbModerate.setBounds(280, 110, 100, 25);
        rbSevere.setBounds(380, 110, 80, 25);
        
        panel.add(rbMild);
        panel.add(rbModerate);
        panel.add(rbSevere);
        
        addLabelAndField("Affected Body Parts:", 50, 150, tfAffectedParts = new JTextField(), panel);
        addLabelAndField("Cause of Disability:", 50, 190, tfCause = new JTextField(), panel);
        
        JLabel heading2 = new JLabel("Certification Details");
        heading2.setBounds(50, 230, 300, 30);
        heading2.setFont(new Font("Tahoma", Font.BOLD, 16));
        panel.add(heading2);
        
        addLabelAndField("Medical Certificate N0:", 50, 270, tfCertificateNumber = new JTextField(), panel);
        addLabelAndField("Issuing Authority:", 50, 310, tfIssuingAuthority = new JTextField(), panel);
        
        JLabel lblDate = new JLabel("Date of Issue:");
        lblDate.setBounds(50, 350, 150, 25);
        panel.add(lblDate);
        
        dateChooser = new JDateChooser();
        dateChooser.setBounds(200, 350, 300, 25);
        panel.add(dateChooser);
        
        JLabel heading3 = new JLabel("Hospital Details");
        heading3.setBounds(50, 390, 300, 30);
        heading3.setFont(new Font("Tahoma", Font.BOLD, 16));
        panel.add(heading3);
        
        addLabelAndField("Hospital Name:", 50, 430, tfHospitalName = new JTextField(), panel);
        addLabelAndField("Doctor's Name:", 50, 470, tfDoctorName = new JTextField(), panel);
        addLabelAndField("Hospital Number:", 50, 510, tfHospitalNumber = new JTextField(), panel);
        
        JLabel lblFile = new JLabel("Medical Certificate:");
        lblFile.setBounds(50, 550, 150, 25);
        panel.add(lblFile);
        
        lblFilePath = new JLabel("No file selected");
        lblFilePath.setBounds(200, 550, 300, 25);
        panel.add(lblFilePath);
        
        uploadFile = new JButton("Upload");
        uploadFile.setBounds(500, 550, 100, 25);
        uploadFile.addActionListener(e -> uploadFile());
        panel.add(uploadFile);
        
        submit = new JButton("Submit");
        submit.setBounds(250, 600, 100, 30);
        submit.addActionListener(e -> saveToDatabase());
        add(submit);
        
        back = new JButton("Back");
        back.setBounds(400, 600, 100, 30);
        back.addActionListener(e -> setVisible(false));
        add(back);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("images/medical.png"));
        Image i2 = i1.getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(450, 70, 500, 400);
        panel.add(image);
        
        setVisible(true);
    }
    
    private void uploadFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("PDF Files", "pdf"));
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            lblFilePath.setText(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }
    
   private void saveToDatabase() {
    try {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/scribber", "root", "7586");
        String query = "INSERT INTO medical_reports (username, type_of_disability, severity_of_disability, affected_body_parts, cause_of_disability, medical_certificate_number, issuing_authority, date_of_issue, hospital_name, doctor_name, hospital_number, medical_certificate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement pst = conn.prepareStatement(query);
        pst.setString(1, username);
        pst.setString(2, tfDisabilityType.getText());
        pst.setString(3, getSelectedSeverity());
        pst.setString(4, tfAffectedParts.getText());
        pst.setString(5, tfCause.getText());
        pst.setString(6, tfCertificateNumber.getText());
        pst.setString(7, tfIssuingAuthority.getText());
        pst.setDate(8, new java.sql.Date(dateChooser.getDate().getTime()));
        pst.setString(9, tfHospitalName.getText());
        pst.setString(10, tfDoctorName.getText());
        pst.setString(11, tfHospitalNumber.getText());

        // Read file as byte array for BLOB storage
        File file = new File(lblFilePath.getText());
        FileInputStream fis = new FileInputStream(file);
        pst.setBinaryStream(12, fis, (int) file.length());

        pst.executeUpdate();
        JOptionPane.showMessageDialog(null, "Medical report saved successfully!");

        fis.close();
        conn.close();
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error saving medical report.");
    }
}


    private String getSelectedSeverity() {
        if (rbMild.isSelected()) return "Mild";
        if (rbModerate.isSelected()) return "Moderate";
        if (rbSevere.isSelected()) return "Severe";
        return "";
    }

    private void addLabelAndField(String label, int x, int y, JTextField field, JPanel panel) {
        JLabel lbl = new JLabel(label);
        lbl.setBounds(x, y, 150, 25);
        panel.add(lbl);
        field.setBounds(200, y, 300, 25);
        panel.add(field);
    }

    public static void main(String[] args) {
        new HealthReport("User");
    }
}
