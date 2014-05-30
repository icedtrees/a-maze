package pages;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * Components is a library of functions used in making the GUI. 
 */
public class Components {
    /**
     * Makes a translucent button with white text and a particular size.
     * @param string to display on the button
     * @return a button with the set properties
     */
    public static JButton makeButton(String string) {
		JButton button = new JButton(string) {
			
			private static final long serialVersionUID = 1L;

			// Overrides painting of background to make it translucent
			@Override
			protected void paintComponent(Graphics g) {
				if (!isOpaque() && getBackground().getAlpha() < 255) {
					g.setColor(getBackground());
					g.fillRect(0, 0, getWidth(), getHeight());
				}
				super.paintComponent(g);
			}

		};
    	// sets the colour and opacity of button
    	button.setBackground(new Color(0, 0, 0, 150));
    	
    	// makes jbutton not opaque, so it doesn't stuff up 
    	// the default painting mechanism
    	button.setOpaque(false);
    	
    	// gets rid of rectangle around text
    	button.setFocusPainted(false);
    	
    	// sets text colour
    	button.setForeground(Color.WHITE);
    	
    	// This makes the button stay the same colour and opacity
    	// when the button is clicked
    	button.setContentAreaFilled(false);
    	
    	// change font size
    	Font font = button.getFont();
    	button.setFont(new Font(font.getName(), Font.PLAIN, 20));
    	
    	button.setPreferredSize(new Dimension(200, 60));
        return button;
    }
    
    /**
     * Makes a label for titles - it has bigger font, which is white.
     * @param string to display on the label.
     * @return a label with the string shown in the centre
     */
    public static JLabel makeTitle(String string) {
        JLabel titleLabel = new JLabel(string, JLabel.CENTER);
        Font font = titleLabel.getFont();
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font(font.getName(), Font.PLAIN, 40));
        return titleLabel;
    }
    
    /**
     * @param string The string displayed on the label
     * @param fontSize The font size of the string
     * @return a label with the specified font size. The text is in white.
     */
    public static JLabel makeText(String string, int fontSize) {
        JLabel titleLabel = new JLabel(string, JLabel.CENTER);
        Font font = titleLabel.getFont();
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font(font.getName(), Font.PLAIN, fontSize));
        return titleLabel;
    }
    
    /**
     * @return A transparent JPanel
     */
    public static JPanel makePanel() {
    	JPanel panel = new JPanel();
    	panel.setOpaque(false);
    	return panel;
    }
    
    /**
     * @param text to display for the checkBox
     * @return a checkBox with white text and a transparent background
     */
    public static JCheckBox makeCheckBox(String text) {
    	JCheckBox checkBox = new JCheckBox(text);
    	
    	checkBox.setOpaque(false);
    	Font font = checkBox.getFont();
    	checkBox.setForeground(Color.WHITE);
    	
    	// gets rid of rectangle around text when it is clicked
    	checkBox.setFocusPainted(false);
    	checkBox.setFont(new Font(font.getName(), Font.PLAIN, 15));
    	
    	return checkBox;
    }
    
    /**
     * @param min Minimum value for slider
     * @param max Maximum value for slider
     * @param defaultValue default value to show on slider
     * @param majTick Spacing of the major ticks
     * @param minTick Spacing of the minor ticks
     * @param length Length of the slider
     * @return a horizontal slider with the given parameters, with the numbers
     * in white font.
     */
    public static JSlider makeJSlider(int min, int max, 
    	int defaultValue, int majTick, int minTick, int length) {
    	JSlider slider = new JSlider(JSlider.HORIZONTAL, min, max, defaultValue);
    	slider.setMajorTickSpacing(majTick);
    	slider.setMinorTickSpacing(minTick);
    	slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setPreferredSize(new Dimension (length, slider.getPreferredSize().height));
        slider.setOpaque(false);
        slider.setForeground(Color.WHITE);
        return slider;
    }
    
}