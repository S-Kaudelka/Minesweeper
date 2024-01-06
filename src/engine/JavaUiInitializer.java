package engine;

import javax.swing.*;
import java.awt.*;

public interface JavaUiInitializer {

    int minNumberFields = 10;
    int maxNumberFields = 40;

    default JButton initializeButton(int x, int y, int width, int height, int fontSize, String text) {
        JButton newButton = new JButton();
        newButton.setBounds(x, y, width, height);
        newButton.setBackground(new Color(214, 217, 223));
        newButton.setForeground(new Color(0, 0, 0));
        newButton.setEnabled(true);
        newButton.setFont(new Font("SansSerif", Font.PLAIN, fontSize));
        newButton.setText(text);
        newButton.setVisible(true);
        return newButton;
    }

    default JTextField initializeTextField(int x, int y, int width, int height, int fontSize, String text) {
        JTextField newTextField = new JTextField();
        newTextField.setBounds(x, y, width, height);
        newTextField.setBackground(new Color(255, 255, 255));
        newTextField.setForeground(new Color(0, 0, 0));
        newTextField.setEnabled(true);
        newTextField.setFont(new Font("SansSerif", Font.PLAIN, fontSize));
        newTextField.setText(text);
        newTextField.setVisible(true);
        return newTextField;
    }

    default JLabel initializeLabel(int x, int y, int width, int height, int fontSize, String text) {
        JLabel newLabel = new JLabel();
        newLabel.setBounds(x, y, width, height);
        newLabel.setBackground(new Color(214, 217, 223));
        newLabel.setForeground(new Color(0, 0, 0));
        newLabel.setEnabled(true);
        newLabel.setFont(new Font("SansSerif", Font.PLAIN, fontSize));
        newLabel.setText(text);
        newLabel.setVisible(true);
        return newLabel;
    }
}
