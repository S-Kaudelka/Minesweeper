package gui;

import engine.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainMenu extends JFrame {
    private final JTextField textFieldHeight;
    private final JTextField textFieldWidth;
    private final JLabel labelErrorMessage;

    private static final int minSizeValue = 9;
    private static final int maxSizeValue = 40;

    public static void main(String[] args) {
        new MainMenu();
    }

    public MainMenu() {

        this.setTitle("MainMenu");
        this.setSize(200, 200);

        //pane with null layout
        JPanel contentPane = new JPanel(null);
        contentPane.setPreferredSize(new Dimension(200, 180));
        contentPane.setBackground(new Color(192, 192, 192));

        JButton buttonStart = new JButton();
        buttonStart.setBounds(55, 120, 90, 35);
        buttonStart.setBackground(new Color(214, 217, 223));
        buttonStart.setForeground(new Color(0, 0, 0));
        buttonStart.setEnabled(true);
        buttonStart.setFont(new Font("SansSerif", Font.PLAIN, 12));
        buttonStart.setText("Start");
        buttonStart.setVisible(true);
        //Set methods for mouse events
        //Call defined methods
        buttonStart.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent evt) {
                startGame(evt);
            }
        });

        JLabel labelHeader1 = new JLabel();
        labelHeader1.setBounds(20, 10, 160, 35);
        labelHeader1.setBackground(new Color(214, 217, 223));
        labelHeader1.setForeground(new Color(0, 0, 0));
        labelHeader1.setEnabled(true);
        labelHeader1.setFont(new Font("SansSerif", Font.PLAIN, 14));
        labelHeader1.setText("Enter Height and Width");
        labelHeader1.setVisible(true);

        JLabel labelHeader2 = new JLabel();
        labelHeader2.setBounds(45, 35, 110, 35);
        labelHeader2.setBackground(new Color(214, 217, 223));
        labelHeader2.setForeground(new Color(0, 0, 0));
        labelHeader2.setEnabled(true);
        labelHeader2.setFont(new Font("SansSerif", Font.PLAIN, 14));
        labelHeader2.setText("then press Start");
        labelHeader2.setVisible(true);

        labelErrorMessage = new JLabel();
        labelErrorMessage.setBounds(10, 150, 180, 35);
        labelErrorMessage.setBackground(new Color(214, 217, 223));
        labelErrorMessage.setForeground(new Color(0, 0, 0));
        labelErrorMessage.setEnabled(true);
        labelErrorMessage.setFont(new Font("SansSerif", Font.PLAIN, 12));
        labelErrorMessage.setText("");
        labelErrorMessage.setHorizontalAlignment(SwingConstants.CENTER);
        labelErrorMessage.setVisible(true);

        textFieldHeight = new JTextField();
        textFieldHeight.setBounds(15, 75, 80, 35);
        textFieldHeight.setBackground(new Color(255, 255, 255));
        textFieldHeight.setForeground(new Color(0, 0, 0));
        textFieldHeight.setEnabled(true);
        textFieldHeight.setFont(new Font("SansSerif", Font.PLAIN, 12));
        textFieldHeight.setText("");
        textFieldHeight.setToolTipText("Height");
        textFieldHeight.setVisible(true);

        textFieldWidth = new JTextField();
        textFieldWidth.setBounds(105, 75, 80, 35);
        textFieldWidth.setBackground(new Color(255, 255, 255));
        textFieldWidth.setForeground(new Color(0, 0, 0));
        textFieldWidth.setEnabled(true);
        textFieldWidth.setFont(new Font("SansSerif", Font.PLAIN, 12));
        textFieldWidth.setText("");
        textFieldWidth.setToolTipText("Width");
        textFieldWidth.setVisible(true);

        //adding components to contentPane panel
        contentPane.add(buttonStart);
        contentPane.add(labelHeader1);
        contentPane.add(labelHeader2);
        contentPane.add(labelErrorMessage);
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

            if (height < minSizeValue || height > maxSizeValue) {
                labelErrorMessage.setText("Height not between " + minSizeValue + " & " + maxSizeValue);
                return;
            }
            if (width < minSizeValue || width > maxSizeValue) {
                labelErrorMessage.setText("Width not between " + minSizeValue + " & " + maxSizeValue);
                return;
            }

            this.dispose();

            new Controller(height, width);
        } catch (Exception e) {
            labelErrorMessage.setText("Invalid values");
            System.out.println("Exception starting the game: " + e);
        }
    }
}