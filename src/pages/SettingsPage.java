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
		
		setLayout(new GridLayout(0,1));
		
		JLabel text = new JLabel("Select your desired resolution:", JLabel.CENTER);
	
        add(text);
        
        JRadioButton resolution1 = new JRadioButton("1000 by 800");
        JRadioButton defaultRes = new JRadioButton("800 by 600 (default)");
        JRadioButton resolution2 = new JRadioButton("600 by 400");
        JRadioButton resolution3 = new JRadioButton("400 by 300");
        defaultRes.setSelected(true);
        
        ButtonGroup pickResolution = new ButtonGroup();
        pickResolution.add(resolution1);
		resolution1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("change resolution to res1");
				result = Result.RESOLUTION_ONE;
			}
		});
        pickResolution.add(defaultRes);
		defaultRes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("change resolution to default");
				result = Result.DEFAULTRESOLUTION;
			}
		});
        pickResolution.add(resolution2);
		resolution2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("change resolution to res2");
				result = Result.RESOLUTION_TWO;
			}
		});
        pickResolution.add(resolution3);
		resolution3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("change resolution to res3");
				result = Result.RESOLUTION_THREE;
			}
		});
  
        add(resolution1);
        add(defaultRes);
        add(resolution2);
        add(resolution3);

        
        //add(this, BorderLayout.CENTER);
        //setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        
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
    	System.out.println("setting's result is " + result);
		return result;
	}
	
	private void addReturnButton() {
        JPanel returnPanel = new JPanel();
        returnPanel.setLayout(new FlowLayout());
		
        JButton returnBut = Components.makeButton("return");
        returnBut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	System.out.println("return to main menu");
            	result = Result.RETURN_HOME;
            }
        });
		
		returnPanel.add(returnBut);
		add(returnPanel);
	}
}
