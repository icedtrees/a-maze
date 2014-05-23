package pages;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import pages.HomePage.Result;

public class SettingsPage extends Page {
	public enum Result implements Page.Result {
        RESOLUTION_ONE,
        RESOLUTION_TWO,
        RESOLUTION_THREE,
        DEFAULTRESOLUTION,
        RETURN_HOME
    };
	private static final long serialVersionUID = 1L;
	private static Result result;
	
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
	
    public void setSettingsResult(SettingsPage.Result newResult) {
        result = newResult;
    }
	
	@Override
	public SettingsPage.Result run() {
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
