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
		
		setLayout(new GridLayout(2,1));
		
		JLabel text = new JLabel("display high scores", JLabel.CENTER);;
        add(text);
        
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
