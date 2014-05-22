package pages;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import pages.MazePage.Result;

public class InstructionsPage extends Page {
	public enum Result implements Page.Result {
        RETURN_HOME
    };
	private static final long serialVersionUID = 1L;

	public InstructionsPage() {
		super();
		
		setLayout(new GridLayout(2, 1));
		
		JLabel text = new JLabel("will show instructions here", JLabel.CENTER);;
        add(text);
	}

	@Override
	public pages.Page.Result run() {
		// TODO Auto-generated method stub
		return null;
	}

	private void addReturnButton() {
        JPanel returnPanel = new JPanel();
        returnPanel.setLayout(new FlowLayout());
		
        JButton returnBut = new JButton("return");
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
