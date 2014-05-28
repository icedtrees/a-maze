package pages;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Components {
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
    	button.setBackground(new Color(0, 0, 0, 50));
    	
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
    
    public static JLabel makeTitle(String string) {
        JLabel titleLabel = new JLabel(string, JLabel.CENTER);
        Font font = titleLabel.getFont();
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font(font.getName(), Font.PLAIN, 40));
        return titleLabel;
    }
    
    public static JLabel makeText(String string, int fontSize) {
        JLabel titleLabel = new JLabel(string, JLabel.CENTER);
        Font font = titleLabel.getFont();
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font(font.getName(), Font.PLAIN, fontSize));
        return titleLabel;
    }
    
    public static JPanel makePanel() {
    	JPanel panel = new JPanel();
    	panel.setOpaque(false);
    	return panel;
    }
    
    public static JPanel makeTranslucentPanel() {
    	JPanel panel = new JPanel() {
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
    	panel.setBackground(new Color(0, 0, 0, 50));
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