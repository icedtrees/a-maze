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
		GridBagConstraints c = new GridBagConstraints();
		
		JLabel titleLabel = Components.makeTitle("Instructions");
		c.fill = GridBagConstraints.VERTICAL; //not sure what this does
		c.gridx = 0;
		c.gridy = 0;
		c.ipady = 40;
		c.weighty = 0.25;
        add(titleLabel, c);
        
        JPanel content = Components.makePanel();
        content.setLayout(new GridLayout(2, 0));
        JLabel instructions1 = Components.makeText("Single Player: Use the arrow keys to move.", 20);
        //JLabel instructions2 = Components.makeText("Multi Player: Player 1 WASD, Player 2 Arrow Keys", 20);
        instructions1.setOpaque(false);
        JLabel imageLabel = new JLabel();
        imageLabel.setOpaque(false);
        imageLabel.setLayout(new BorderLayout());
		ImageIcon image = new ImageIcon("src/WASDarrowKeys.png");
		imageLabel.setIcon(image);

        content.add(imageLabel);
        content.add(instructions1);
        //content.add(instructions2);
        content.setOpaque(false);
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
        returnPanel.setOpaque(false);
		
        JButton returnBut = Components.makeButton("return");
        returnBut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
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
