package pages;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class HighScoresPage extends Page {
	public enum Result implements Page.Result {
        RETURN_HOME
    };
	private static final long serialVersionUID = 1L;
	private volatile Result result;
	
	public HighScoresPage() {
		super();
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JLabel titleLabel = Components.makeTitle("High Scores");
		c.fill = GridBagConstraints.VERTICAL; //not sure what this does
		c.gridx = 0;
		c.gridy = 0;
		c.ipady = 40;
		c.weighty = 0.25;
        add(titleLabel, c);
        
        JPanel content = Components.makePanel();
		c.fill = GridBagConstraints.VERTICAL; //not sure what this does
		c.gridx = 0;
		c.gridy = 1;
		c.ipady = 320;
		c.weighty = 0.75;
		add(content, c);
        
        addReturnButton();
        result = null;
	}


	@Override
	public HighScoresPage.Result run() {
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
