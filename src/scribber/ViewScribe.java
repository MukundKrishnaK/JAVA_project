package scribber;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ViewScribe extends JFrame {
    
    JTabbedPane tab;
    String username;

    public ViewScribe(String username) {
        this.username = username;
        setTitle("Scribe Details");
        setBounds(450, 120, 800, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        tab = new JTabbedPane();

        ArrayList<String[]> scribesList = getScribeListFromDatabase();
        for (String[] details : scribesList) {
            JPanel panel = createScribePanel(details);
            tab.addTab(details[1], null, panel);
        }

        add(tab, BorderLayout.CENTER);
        setVisible(true);
    }

    private JPanel createScribePanel(String[] details) {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

        int xLabel = 30, xField = 220, y = 50, widthLabel = 150, widthField = 200, height = 30;

        panel.add(createLabel("Username:", xLabel, y, widthLabel, height));
        panel.add(createTextField(details[1], xField, y, widthField, height));
        y += 40;

        panel.add(createLabel("First Name:", xLabel, y, widthLabel, height));
        panel.add(createTextField(details[2], xField, y, widthField, height));
        y += 40;

        panel.add(createLabel("Last Name:", xLabel, y, widthLabel, height));
        panel.add(createTextField(details[3], xField, y, widthField, height));
        y += 40;

        panel.add(createLabel("DOB:", xLabel, y, widthLabel, height));
        panel.add(createTextField(details[4], xField, y, widthField, height));
        y += 40;

        panel.add(createLabel("Age:", xLabel, y, widthLabel, height));
        panel.add(createTextField(details[5], xField, y, widthField, height));
        y += 40;

        panel.add(createLabel("Gender:", xLabel, y, widthLabel, height));
        panel.add(createTextField(details[6], xField, y, widthField, height));
        y += 40;

        panel.add(createLabel("Phone:", xLabel, y, widthLabel, height));
        panel.add(createTextField(details[7], xField, y, widthField, height));
        y += 40;

        panel.add(createLabel("Country:", xLabel, y, widthLabel, height));
        panel.add(createTextField(details[8], xField, y, widthField, height));
        y += 40;

        panel.add(createLabel("State:", xLabel, y, widthLabel, height));
        panel.add(createTextField(details[9], xField, y, widthField, height));
        y += 40;

        panel.add(createLabel("City:", xLabel, y, widthLabel, height));
        panel.add(createTextField(details[10], xField, y, widthField, height));
        y += 40;

        panel.add(createLabel("Address:", xLabel, y, widthLabel, height));
        panel.add(createTextField(details[11], xField, y, widthField, height));
        y += 40;

        panel.add(createLabel("Aadhar Number:", xLabel, y, widthLabel, height));
        panel.add(createTextField(details[12], xField, y, widthField, height));

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

    private ArrayList<String[]> getScribeListFromDatabase() {
        ArrayList<String[]> scribeList = new ArrayList<>();
        try {
            Conn conn = new Conn();
            Connection connection = conn.getConnection();
            String query = "SELECT * FROM scribe";
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String[] scribeDetails = new String[13];
                scribeDetails[0] = rs.getString("id");
                scribeDetails[1] = rs.getString("username");
                scribeDetails[2] = rs.getString("first_name");
                scribeDetails[3] = rs.getString("last_name");
                scribeDetails[4] = rs.getString("dob");
                scribeDetails[5] = rs.getString("age");
                scribeDetails[6] = rs.getString("gender");
                scribeDetails[7] = rs.getString("phone_number");
                scribeDetails[8] = rs.getString("country");
                scribeDetails[9] = rs.getString("state");
                scribeDetails[10] = rs.getString("city");
                scribeDetails[11] = rs.getString("address");
                scribeDetails[12] = rs.getString("aadhar_number");

                scribeList.add(scribeDetails);
            }
            pst.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scribeList;
    }

    public static void main(String[] args) {
        new ViewScribe("User");
    }
}
