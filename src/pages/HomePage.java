package pages;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HomePage extends Page {
    private static final long serialVersionUID = 1L;
    public enum Result implements Page.Result {
        PLAY_GAME,
        SHOW_INSTRUCTIONS,
        SHOW_SETTINGS,
        SHOW_CUSTOM,
        QUIT_GAME,
    };

    private volatile Result result;
	
    public HomePage() {
    	super();
        setLayout(new GridLayout(6, 1));
        
        //JLabel blank = new JLabel("", JLabel.CENTER);
        //add(blank);
        
        JLabel titleLabel = Components.makeTitle("A-MAZE");
        add(titleLabel);
        
        addStartButton();
        addInstructionsButton();
        addSettingsButton();
        addHighScoresButton();
        addExitButton();
        
        result = null;
    }
    
    public HomePage.Result run() {
        result = null;
        // Wait until the user presses the button, and then return the result
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
    
    private void addStartButton() {
        JPanel startPanel = Components.makePanel();
        startPanel.setLayout(new FlowLayout());
        
        JButton startMaze = Components.makeButton("Start maze");
		startMaze.addActionListener(new ActionListener() {
			public synchronized void actionPerformed(ActionEvent e) {
				// some action to generate maze and display a MazePage
				result = Result.PLAY_GAME;
			}
		});
		
		startPanel.add(startMaze);
		add(startPanel);
    }
    
	private void addInstructionsButton() {
		JPanel instructionsPanel = Components.makePanel();
		instructionsPanel.setLayout(new FlowLayout());

		JButton instructions = Components.makeButton("Instructions");
		instructions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// some action to show instructions screen for each feature
				result = Result.SHOW_INSTRUCTIONS;
			}
		});
		instructionsPanel.add(instructions);
		add(instructionsPanel);
	}
    
	private void addSettingsButton() {
		JPanel settingsPanel = Components.makePanel();
		settingsPanel.setLayout(new FlowLayout());

		JButton settings = Components.makeButton("Settings");
		settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				result = Result.SHOW_SETTINGS;
			}
		});
		settingsPanel.add(settings);
		add(settingsPanel);
	}
	
	private void addHighScoresButton() {
		JPanel scoresPanel = Components.makePanel();
		scoresPanel.setLayout(new FlowLayout());

		JButton scores = Components.makeButton("Custom Maze");
		scores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				result = Result.SHOW_CUSTOM;
			}
		});
		scoresPanel.add(scores);
		add(scoresPanel);
	}
	
	private void addExitButton() {
		JPanel exitPanel = Components.makePanel();
		exitPanel.setLayout(new FlowLayout());

		JButton exit = Components.makeButton("Exit");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				result = Result.QUIT_GAME;
			}
		});
		exitPanel.add(exit);
		add(exitPanel);
	}
	
}
