package pages;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
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
        DEFAULTRESOLUTION
    };
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	
	public SettingsPage(JFrame frame) {
		super();
		
		mainPanel = new JPanel(new GridLayout(0,1));
		
		JLabel text = new JLabel("display settings", JLabel.CENTER);
	
        mainPanel.add(text);
        
        JRadioButton resolution1 = new JRadioButton("resolution 1");
        JRadioButton resolution2 = new JRadioButton("resolution 2");
        JRadioButton resolution3 = new JRadioButton("resolution 3");
        JRadioButton defaultRes = new JRadioButton("default - _");
        defaultRes.setSelected(true);
        
        ButtonGroup pickResolution = new ButtonGroup();
        pickResolution.add(resolution1);
        pickResolution.add(resolution2);
        pickResolution.add(resolution3);
        pickResolution.add(defaultRes);
        
        
		resolution1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// some action to show instructions screen for each feature
				System.out.println("change resolution to res1");
			}
		});
        
        // might need to register a listener for the radio buttons
       
        mainPanel.add(resolution1);
        mainPanel.add(resolution2);
        mainPanel.add(resolution3);
        mainPanel.add(defaultRes);
        
        add(mainPanel, BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
	}
	
	public JPanel getHighScoresPanel() {
		return mainPanel;
	}

	@Override
	public pages.Page.Result run() {
		// TODO Auto-generated method stub
		return null;
	}
	
}