package engine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainMenu extends JFrame implements JavaUiInitializer {
    private final JTextField textFieldHeight;
    private final JTextField textFieldWidth;
    private final JLabel labelErrorMessage;

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

        JButton buttonStart = initializeButton(55, 120, 90, 35, 12, "Start");
        buttonStart.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent evt) {
                startGame(evt);
            }
        });

        JLabel labelHeader1 = initializeLabel(20, 10, 160, 35, 14, "Enter Height and Width");

        JLabel labelHeader2 = initializeLabel(45, 35, 110, 35, 14, "then press Start");

        labelErrorMessage = initializeLabel(10, 150, 180, 35, 12, "");
        labelErrorMessage.setHorizontalAlignment(SwingConstants.CENTER);

        textFieldHeight = initializeTextField(15, 75, 80, 35, 12, "");

        textFieldWidth = initializeTextField(105, 75, 80, 35, 12, "");

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

    //Method mouseReleased for buttonStart
    private void startGame(MouseEvent ignore) {
        String widthString = textFieldWidth.getText();
        String heightString = textFieldHeight.getText();

        try {
            int width = Integer.parseInt(widthString);
            int height = Integer.parseInt(heightString);

            if (height < minNumberFields || height > maxNumberFields) {
                labelErrorMessage.setText("Height not between " + minNumberFields + " & " + maxNumberFields);
                return;
            }
            if (width < minNumberFields || width > maxNumberFields) {
                labelErrorMessage.setText("Width not between " + minNumberFields + " & " + maxNumberFields);
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