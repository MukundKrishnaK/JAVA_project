package scribber;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class AdminMedicalReport extends JFrame {
    JTabbedPane tab;
    String username;

    public AdminMedicalReport(String username) {
        
        this.username = username;
        setTitle("Medical Reports");
        setBounds(450, 120, 800, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        tab = new JTabbedPane();
        ArrayList<String[]> reportsList = getMedicalReportsFromDatabase();
        
        for (String[] details : reportsList) {
            JPanel panel = createReportPanel(details);
            tab.addTab(details[1], null, panel);
        }

        add(tab, BorderLayout.CENTER);
        setVisible(true);
    }

    private JPanel createReportPanel(String[] details) {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        Font labelFont = new Font("Tahoma", Font.PLAIN, 16);

        int xLabel = 30, xField = 250, y = 50, widthLabel = 200, widthField = 350, height = 30;

        panel.add(createLabel("Username:", xLabel, y, widthLabel, height));
        panel.add(createTextField(details[1], xField, y, widthField, height));
        y += 40;

        panel.add(createLabel("Type of Disability:", xLabel, y, widthLabel, height));
        panel.add(createTextField(details[2], xField, y, widthField, height));
        y += 40;

        panel.add(createLabel("Severity:", xLabel, y, widthLabel, height));
        panel.add(createTextField(details[3], xField, y, widthField, height));
        y += 40;

        panel.add(createLabel("Affected Body Parts:", xLabel, y, widthLabel, height));
        panel.add(createTextField(details[4], xField, y, widthField, height));
        y += 40;

        panel.add(createLabel("Cause of Disability:", xLabel, y, widthLabel, height));
        panel.add(createTextField(details[5], xField, y, widthField, height));
        y += 40;

        panel.add(createLabel("Medical Certificate No:", xLabel, y, widthLabel, height));
        panel.add(createTextField(details[6], xField, y, widthField, height));
        y += 40;

        panel.add(createLabel("Issuing Authority:", xLabel, y, widthLabel, height));
        panel.add(createTextField(details[7], xField, y, widthField, height));
        y += 40;

        panel.add(createLabel("Date of Issue:", xLabel, y, widthLabel, height));
        panel.add(createTextField(details[8], xField, y, widthField, height));
        y += 40;

        panel.add(createLabel("Hospital Name:", xLabel, y, widthLabel, height));
        panel.add(createTextField(details[9], xField, y, widthField, height));
        y += 40;

        panel.add(createLabel("Doctor Name:", xLabel, y, widthLabel, height));
        panel.add(createTextField(details[10], xField, y, widthField, height));
        y += 40;

        panel.add(createLabel("Hospital Number:", xLabel, y, widthLabel, height));
        panel.add(createTextField(details[11], xField, y, widthField, height));
        y += 40;

        return panel;
    }

    private JLabel createLabel(String text, int x, int y, int width, int height) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Tahoma", Font.PLAIN, 16));
        label.setBounds(x, y, width, height);
        return label;
    }

    private JTextField createTextField(String text, int x, int y, int width, int height) {
        JTextField textField = new JTextField(text);
        textField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        textField.setBounds(x, y, width, height);
        textField.setForeground(Color.BLACK);
        textField.setEditable(false);
        return textField;
    }

    private ArrayList<String[]> getMedicalReportsFromDatabase() {
        ArrayList<String[]> reportsList = new ArrayList<>();
        try {
            Conn conn = new Conn();
            Connection connection = conn.getConnection();
            String query = "SELECT * FROM medical_reports";
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String[] reportDetails = new String[12];
                reportDetails[0] = String.valueOf(rs.getInt("id"));
                reportDetails[1] = rs.getString("username");
                reportDetails[2] = rs.getString("type_of_disability");
                reportDetails[3] = rs.getString("severity_of_disability");
                reportDetails[4] = rs.getString("affected_body_parts");
                reportDetails[5] = rs.getString("cause_of_disability");
                reportDetails[6] = rs.getString("medical_certificate_number");
                reportDetails[7] = rs.getString("issuing_authority");
                reportDetails[8] = rs.getString("date_of_issue");
                reportDetails[9] = rs.getString("hospital_name");
                reportDetails[10] = rs.getString("doctor_name");
                reportDetails[11] = rs.getString("hospital_number");
                reportsList.add(reportDetails);
            }
            pst.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reportsList;
    }

    public static void main(String[] args) {
        new AdminMedicalReport("User");
    }
}
