package scribber;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.*;
import com.toedter.calendar.JDateChooser;

public class ViewHealthReport extends JFrame {
    JTextField tfDisabilityType, tfAffectedParts, tfCause, tfCertificateNumber, tfIssuingAuthority, tfHospitalName, tfDoctorName, tfHospitalNumber;
    JRadioButton rbMild, rbModerate, rbSevere;
    ButtonGroup severityGroup;
    JLabel lblFilePath;
    JDateChooser dateChooser;
    String username;

    public ViewHealthReport(String username) {
        this.username = username;
        setTitle("View Health Report");
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

        addLabelAndField("Medical Certificate Number:", 50, 270, tfCertificateNumber = new JTextField(), panel);
        addLabelAndField("Issuing Authority:", 50, 310, tfIssuingAuthority = new JTextField(), panel);

        JLabel lblDate = new JLabel("Date of Issue:");
        lblDate.setBounds(50, 350, 150, 25);
        panel.add(lblDate);

        dateChooser = new JDateChooser();
        dateChooser.setBounds(200, 350, 300, 25);
        dateChooser.setEnabled(false);
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

        lblFilePath = new JLabel("No file found");
        lblFilePath.setBounds(200, 550, 300, 25);
        panel.add(lblFilePath);

        JButton back = new JButton("Back");
        back.setBounds(400, 600, 100, 30);
        back.addActionListener(e -> setVisible(false));
        add(back);

        retrieveFromDatabase();
        setVisible(true);
    }

    private void retrieveFromDatabase() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/scribber", "root", "7586")) {
            String query = "SELECT * FROM medical_reports WHERE username = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                tfDisabilityType.setText(rs.getString("type_of_disability"));
                String severity = rs.getString("severity_of_disability");
                if (severity.equals("Mild")) rbMild.setSelected(true);
                else if (severity.equals("Moderate")) rbModerate.setSelected(true);
                else if (severity.equals("Severe")) rbSevere.setSelected(true);

                tfAffectedParts.setText(rs.getString("affected_body_parts"));
                tfCause.setText(rs.getString("cause_of_disability"));
                tfCertificateNumber.setText(rs.getString("medical_certificate_number"));
                tfIssuingAuthority.setText(rs.getString("issuing_authority"));
                dateChooser.setDate(rs.getDate("date_of_issue"));
                tfHospitalName.setText(rs.getString("hospital_name"));
                tfDoctorName.setText(rs.getString("doctor_name"));
                tfHospitalNumber.setText(rs.getString("hospital_number"));

                // Retrieve medical certificate BLOB
                InputStream input = rs.getBinaryStream("medical_certificate");
                if (input != null) {
                    File file = new File("retrieved_certificate.pdf");
                    FileOutputStream output = new FileOutputStream(file);
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = input.read(buffer)) > 0) {
                        output.write(buffer, 0, bytesRead);
                    }
                    output.close();
                    lblFilePath.setText("Certificate saved: " + file.getAbsolutePath());
                } else {
                    lblFilePath.setText("No certificate found");
                }
            } else {
                JOptionPane.showMessageDialog(null, "No health report found for this user.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error retrieving medical report.");
        }
    }

    private void addLabelAndField(String label, int x, int y, JTextField field, JPanel panel) {
        JLabel lbl = new JLabel(label);
        lbl.setBounds(x, y, 180, 25);
        panel.add(lbl);
        field.setBounds(200, y, 300, 25);
        field.setEditable(false);
        panel.add(field);
    }

    public static void main(String[] args) {
        new ViewHealthReport("User");
    }
}
