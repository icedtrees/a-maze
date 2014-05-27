package pages;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class SettingsPage extends Page {
	public enum Result implements Page.Result {
        RESOLUTION_ONE,
        RESOLUTION_TWO,
        RESOLUTION_THREE,
        DEFAULTRESOLUTION,
        RETURN_HOME
    };
	private static final long serialVersionUID = 1L;
	private volatile Result result;
	
	public SettingsPage() {
		super();
		
		setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		JLabel titleLabel = Components.makeTitle("Settings");
		c.fill = GridBagConstraints.VERTICAL; //not sure what this does
		c.gridx = 0;
		c.gridy = 0;
		c.ipady = 30;
		c.weighty = 0.25;
        add(titleLabel, c);
        
        JPanel content = Components.makePanel();
        content.setLayout(new GridLayout(5,0));
		c.fill = GridBagConstraints.VERTICAL; //not sure what this does
		c.gridx = 0;
		c.gridy = 1;
		c.ipady = 120;
		c.weighty = 0.75;
		
		//content.setSize(new Dimension(400, 400));
		add(content, c);
		JLabel text = Components.makeText("Select your desired resolution:", 20);
		text.setAlignmentX(JLabel.CENTER);
        content.add(text);
        JRadioButton resolution1 = new JRadioButton("1600 by 900");
        JRadioButton resolution2 = new JRadioButton("1280 by 720");
        JRadioButton defaultRes = new JRadioButton("800 by 600 (default)");
        defaultRes.setSelected(true);
        
        ButtonGroup pickResolution = new ButtonGroup();
        pickResolution.add(resolution1);
		resolution1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				result = Result.RESOLUTION_ONE;
			}
		});
        pickResolution.add(defaultRes);
		defaultRes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				result = Result.DEFAULTRESOLUTION;
			}
		});
        pickResolution.add(resolution2);
		resolution2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				result = Result.RESOLUTION_TWO;
			}
		});
		
        resolution1.setForeground(Color.WHITE);
        resolution2.setForeground(Color.WHITE);
        defaultRes.setForeground(Color.WHITE);
        
        resolution1.setFocusable(false);
        resolution2.setFocusable(false);
        defaultRes.setFocusable(false);
  
        content.add(resolution1);
        content.add(resolution2);
        content.add(defaultRes);
        
        resolution1.setOpaque(false);
        defaultRes.setOpaque(false);
        resolution2.setOpaque(false);
               
        addReturnButton();
	}
	
	@Override
	public SettingsPage.Result run() {
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
            	result = Result.RETURN_HOME;
            }
        });
		
		returnPanel.add(returnBut);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.VERTICAL; //not sure what this does
		c.anchor = GridBagConstraints.PAGE_END; //bottom of space
		c.gridx = 0;
		c.gridy = 2;
		c.weighty = 0.25;
		add(returnPanel, c);
	}
}
