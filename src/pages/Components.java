package pages;

import java.awt.Dimension;

import javax.swing.JButton;

public class Components {
    public static JButton makeButton(String string) {
        // Possible helper function
    	JButton button = new JButton(string);
    	button.setPreferredSize(new Dimension(120, 40));
        return button;
    }
}