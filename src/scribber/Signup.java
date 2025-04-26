package scribber;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class Signup extends JFrame implements ActionListener {

    JButton create, back;
    JTextField tfname, tfusername, tfanswer;
    JPasswordField tfpassword;
    Choice security;

    Signup() {
        setBounds(350, 200, 900, 400);
        setLayout(null);

        // Gradient Background Panel
        JPanel p1 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(0, 102, 204), getWidth(), getHeight(), new Color(102, 204, 255));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        p1.setBounds(0, 0, 500, 400);
        p1.setLayout(null);
        add(p1);

        JLabel lblusername = new JLabel("Username:");
        styleLabel(lblusername, 50, 20);
        p1.add(lblusername);

        tfusername = createTextField(190, 20);
        p1.add(tfusername);

        JLabel lblname = new JLabel("Name:");
        styleLabel(lblname, 50, 60);
        p1.add(lblname);

        tfname = createTextField(190, 60);
        p1.add(tfname);

        // Restrict name field to characters only
        tfname.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isLetter(c) && !Character.isWhitespace(c) && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                    JOptionPane.showMessageDialog(null, "Enter only characters", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        JLabel lblpassword = new JLabel("Password:");
        styleLabel(lblpassword, 50, 100);
        p1.add(lblpassword);

        tfpassword = createPasswordField(190, 100);
        p1.add(tfpassword);

        JLabel lblsecurity = new JLabel("Security Question:");
        styleLabel(lblsecurity, 50, 140);
        p1.add(lblsecurity);

        security = new Choice();
        security.add("Fav Character from Avengers");
        security.add("Lucky Number");
        security.add("Fav Band");
        security.add("Fav Character from Friends");
        security.setBounds(190, 140, 180, 25);
        p1.add(security);

        JLabel lblanswer = new JLabel("Answer:");
        styleLabel(lblanswer, 50, 180);
        p1.add(lblanswer);

        tfanswer = createTextField(190, 180);
        p1.add(tfanswer);

        // Buttons
        create = createButton("Create", 80, 250);
        create.addActionListener(this);
        p1.add(create);

        back = createButton("Back", 250, 250);
        back.addActionListener(this);
        p1.add(back);

        // Image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("images/sign.png"));
        Image i2 = i1.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
        JLabel image = new JLabel(new ImageIcon(i2));
        image.setBounds(580, 50, 250, 250);
        add(image);

        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    }

    private void styleLabel(JLabel label, int x, int y) {
        label.setFont(new Font("Tahoma", Font.BOLD, 14));
        label.setForeground(Color.WHITE);
        label.setBounds(x, y, 150, 25);
    }

    private JTextField createTextField(int x, int y) {
        JTextField textField = new JTextField();
        textField.setBounds(x, y, 180, 25);
        textField.setFont(new Font("Tahoma", Font.PLAIN, 14));
        textField.setBorder(new LineBorder(Color.WHITE, 1));
        textField.setBackground(new Color(230, 245, 255));
        return textField;
    }

    private JPasswordField createPasswordField(int x, int y) {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(x, y, 180, 25);
        passwordField.setFont(new Font("Tahoma", Font.PLAIN, 14));
        passwordField.setBorder(new LineBorder(Color.WHITE, 1));
        passwordField.setBackground(new Color(230, 245, 255));
        return passwordField;
    }

    private JButton createButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setBounds(x, y, 120, 30);
        button.setFont(new Font("Tahoma", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 102, 204));
        button.setBorder(new LineBorder(Color.WHITE, 2));
        button.setFocusPainted(false);
        return button;
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == create) {
            String username = tfusername.getText();
            String name = tfname.getText();
            String password = new String(tfpassword.getPassword());
            String question = security.getSelectedItem();
            String answer = tfanswer.getText();

            String query = "INSERT INTO account VALUES('" + username + "', '" + name + "', '" + password + "', '" + question + "', '" + answer + "')";
            try {
                Conn c = new Conn();
                c.s.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "Account Created");
                setVisible(false);
                new Login();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == back) {
            setVisible(false);
            new Login();
        }
    }

    public static void main(String[] args) {
        new Signup();
    }
}
