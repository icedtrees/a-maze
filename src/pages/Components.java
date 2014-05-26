package pages;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Components {
    public static JButton makeButton(String string) {
        // Possible helper function
    	JButton button = new JButton(string);
    	button.setForeground(Color.WHITE);
    	button.setOpaque(false);
    	button.setContentAreaFilled(false);
    	button.setPreferredSize(new Dimension(120, 40));
        return button;
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