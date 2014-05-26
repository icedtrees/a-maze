package pages;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Components {
    public static JButton makeButton(String string) {
        // Possible helper function
    	JButton button = new JButton(string);
    	
    	// gets rid of rectangle around text
    	button.setFocusPainted(false);
    	
    	// sets text colour
    	button.setForeground(Color.WHITE);
    	// makes jbutton transparent
    	button.setOpaque(false);
    	button.setContentAreaFilled(false);
    	
    	// change font size
    	Font font = button.getFont();
    	button.setFont(new Font(font.getName(), Font.PLAIN, 20));
    	
    	button.setPreferredSize(new Dimension(200, 60));
        return button;
    }
    
    public static JLabel makeTitle(String string) {
        JLabel titleLabel = new JLabel(string, JLabel.CENTER);
        Font font = titleLabel.getFont();
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font(font.getName(), Font.PLAIN, 40));
        return titleLabel;
    }
    
    public static JPanel makePanel() {
    	JPanel panel = new JPanel();
    	panel.setOpaque(false);
    	return panel;
    }
    //return button?? not used anywhere
	public void addReturnButton(Page page) {
        JPanel returnPanel = new JPanel();
        returnPanel.setLayout(new FlowLayout());
		
        JButton returnBut = Components.makeButton("return");
        returnBut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	System.out.println("return to main menu");
            	// page.setResult(Result.RETURN_HOME);
            }
        });
		
		returnPanel.add(returnBut);
		page.add(returnPanel);
	}
}