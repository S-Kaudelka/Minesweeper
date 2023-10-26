package gui;

import engine.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainMenu extends JFrame {
    private final JTextField textFieldHeight;
    private final JTextField textFieldWidth;

    public static void main(String[] args) {
        new MainMenu();
    }

    //Constructor
    public MainMenu() {

        this.setTitle("MainMenu");
        this.setSize(200, 200);

        //pane with null layout
        JPanel contentPane = new JPanel(null);
        contentPane.setPreferredSize(new Dimension(200, 200));
        contentPane.setBackground(new Color(192, 192, 192));

        JButton button1 = new JButton();
        button1.setBounds(55, 150, 90, 35);
        button1.setBackground(new Color(214, 217, 223));
        button1.setForeground(new Color(0, 0, 0));
        button1.setEnabled(true);
        button1.setFont(new Font("sansserif", Font.PLAIN, 12));
        button1.setText("Start");
        button1.setVisible(true);
        //Set methods for mouse events
        //Call defined methods
        button1.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent evt) {
                startGame(evt);
            }
        });

        JLabel label1 = new JLabel();
        label1.setBounds(20, 10, 160, 35);
        label1.setBackground(new Color(214, 217, 223));
        label1.setForeground(new Color(0, 0, 0));
        label1.setEnabled(true);
        label1.setFont(new Font("SansSerif", Font.PLAIN, 14));
        label1.setText("Enter Height and Width");
        label1.setVisible(true);

        JLabel label4 = new JLabel();
        label4.setBounds(45, 35, 110, 35);
        label4.setBackground(new Color(214, 217, 223));
        label4.setForeground(new Color(0, 0, 0));
        label4.setEnabled(true);
        label4.setFont(new Font("SansSerif", Font.PLAIN, 14));
        label4.setText("and press Start");
        label4.setVisible(true);

        JLabel labelW = new JLabel();
        labelW.setBounds(30, 70, 60, 35);
        labelW.setBackground(new Color(214, 217, 223));
        labelW.setForeground(new Color(0, 0, 0));
        labelW.setEnabled(true);
        labelW.setFont(new Font("SansSerif", Font.PLAIN, 14));
        labelW.setText("Width");
        labelW.setVisible(true);

        JLabel label_H = new JLabel();
        label_H.setBounds(120, 70, 60, 35);
        label_H.setBackground(new Color(214, 217, 223));
        label_H.setForeground(new Color(0, 0, 0));
        label_H.setEnabled(true);
        label_H.setFont(new Font("SansSerif", Font.PLAIN, 14));
        label_H.setText("Height");
        label_H.setVisible(true);

        textFieldHeight = new JTextField();
        textFieldHeight.setBounds(100, 105, 90, 35);
        textFieldHeight.setBackground(new Color(255, 255, 255));
        textFieldHeight.setForeground(new Color(0, 0, 0));
        textFieldHeight.setEnabled(true);
        textFieldHeight.setFont(new Font("sansserif", Font.PLAIN, 12));
        textFieldHeight.setText("");
        textFieldHeight.setVisible(true);

        textFieldWidth = new JTextField();
        textFieldWidth.setBounds(10, 105, 90, 35);
        textFieldWidth.setBackground(new Color(255, 255, 255));
        textFieldWidth.setForeground(new Color(0, 0, 0));
        textFieldWidth.setEnabled(true);
        textFieldWidth.setFont(new Font("sansserif", Font.PLAIN, 12));
        textFieldWidth.setText("");
        textFieldWidth.setVisible(true);

        //adding components to contentPane panel
        contentPane.add(button1);
        contentPane.add(label1);
        contentPane.add(label4);
        contentPane.add(labelW);
        contentPane.add(label_H);
        contentPane.add(textFieldHeight);
        contentPane.add(textFieldWidth);

        //adding panel to JFrame and seting of window position and close operation
        this.add(contentPane);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }

    //Method mouseReleased for button1
    private void startGame(MouseEvent ignore) {
        String widthString = textFieldWidth.getText();
        String heightString = textFieldHeight.getText();

        try {
            int width = Integer.parseInt(widthString);
            int height = Integer.parseInt(heightString);

            if (width == -1 || height == -1) {
                textFieldHeight.setText("Invalid");
                textFieldWidth.setText("Values");
                return;
            }

            if (width < 9 || width > 40 || height < 9 || height > 40) {
                textFieldHeight.setText("Too");
                textFieldWidth.setText("Big");
                return;
            }

            this.dispose();

            new Controller(height, width);
        } catch (Exception e) {
            System.out.println("Exception starting the game: " + e);
        }
    }
}