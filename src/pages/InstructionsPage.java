package pages;

import java.awt.*;
import java.awt.event.*;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.*;


public class InstructionsPage extends Page {
	public enum Result implements Page.Result {
        RETURN_HOME
    };
	private static final long serialVersionUID = 1L;
	private volatile Result result;

	public InstructionsPage() {
		super();
		setLayout(new GridBagLayout());
		setOpaque(false);
		GridBagConstraints c = new GridBagConstraints();
		
		JLabel titleLabel = Components.makeTitle("Instructions");
		c.fill = GridBagConstraints.VERTICAL; //not sure what this does
		c.gridx = 0;
		c.gridy = 0;
		c.ipady = 40;
		c.weighty = 0.25;
        add(titleLabel, c);
        
        JPanel content = Components.makePanel();
        JLabel instructions = new JLabel("    Use the arrow keys to move.");
        instructions.setOpaque(false);
        JLabel imageLabel = new JLabel();
        imageLabel.setOpaque(false);
        imageLabel.setLayout(new BorderLayout());
        URL arrows;
		try {
			arrows = new URL("http://24.media.tumblr.com/795e1d41222cc28485232ac3fb797d62/tumblr_n66q9zJErr1qeu7d7o1_400.png");
			ImageIcon image = new ImageIcon(arrows);
			imageLabel.setIcon(image);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        content.add(imageLabel);
        content.add(instructions);
		c.fill = GridBagConstraints.VERTICAL; //not sure what this does
		c.gridx = 0;
		c.gridy = 1;
		c.ipady = 20;
		c.weighty = 0.75;
		add(content, c);
        
        addReturnButton();
        result = null;
	}

	public InstructionsPage.Result run() {
	    result = null;
    	while (result == null) {
    		// will need to modify this busy block to thread.notify and thread.wait?
    		try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
		return result;
	}

	private void addReturnButton() {
        JPanel returnPanel = Components.makePanel();
        returnPanel.setLayout(new FlowLayout());
		
        JButton returnBut = Components.makeButton("return");
        returnBut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	System.out.println("return to main menu");
            	result = Result.RETURN_HOME;
            }
        });
		
		returnPanel.add(returnBut);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.VERTICAL; //not sure what this does
		c.anchor = GridBagConstraints.PAGE_END; //bottom of space
		c.gridx = 0;
		c.gridy = 2;
		c.weighty = 0.50;
		add(returnPanel, c);
	}
	
}
