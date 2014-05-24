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
        SHOW_HIGH_SCORES,
        QUIT_GAME,
    };

    private volatile Result result;
	
    public HomePage() {
    	super();
        setLayout(new GridLayout(7, 1));
        
        JLabel blank = new JLabel("", JLabel.CENTER);
        add(blank);
        
        JLabel titleLabel = new JLabel("MAZE", JLabel.CENTER);
        add(titleLabel);
        
        addStartButton();
        addInstructionsButton();
        addSettingsButton();
        addHighScoresButton();
        addExitButton();
        
        result = null;
    }
    
    public void setHomeResult(HomePage.Result newResult) {
        result = newResult;
    }

    public HomePage.Result run() {
        // Wait until the user presses the button, and then return the result
        System.out.println("asdf");
    	while (result == null) {
    		// will need to modify this busy block to thread.notify and thread.wait?
    		try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		System.out.println("asdf");
    	}

        return result;
    }
    
    private synchronized void addStartButton() {
        JPanel startPanel = new JPanel();
        startPanel.setLayout(new FlowLayout());
        
        JButton startMaze = Components.makeButton("Start maze");
		startMaze.addActionListener(new ActionListener() {
			public synchronized void actionPerformed(ActionEvent e) {
				// some action to generate maze and display a MazePage
				System.out.println("hello");
				result = Result.PLAY_GAME;
				System.out.println("Result.play_game is" + result);
			}
		});
		
		startPanel.add(startMaze);
		add(startPanel);
    }
    
	private synchronized void addInstructionsButton() {
		JPanel instructionsPanel = new JPanel();
		instructionsPanel.setLayout(new FlowLayout());

		JButton instructions = Components.makeButton("Instructions");
		instructions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// some action to show instructions screen for each feature
				System.out.println("show instructions");
				result = Result.SHOW_INSTRUCTIONS;
				System.out.println("Result.play_game is" + result);
			}
		});
		instructionsPanel.add(instructions);
		add(instructionsPanel);
	}
    
	private void addSettingsButton() {
		JPanel settingsPanel = new JPanel();
		settingsPanel.setLayout(new FlowLayout());

		JButton settings = Components.makeButton("Settings");
		settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("show settings");
				result = Result.SHOW_SETTINGS;
			}
		});
		settingsPanel.add(settings);
		add(settingsPanel);
	}
	
	private void addHighScoresButton() {
		JPanel scoresPanel = new JPanel();
		scoresPanel.setLayout(new FlowLayout());

		JButton scores = Components.makeButton("High Scores");
		scores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("show high scores");
				result = Result.SHOW_HIGH_SCORES;
			}
		});
		scoresPanel.add(scores);
		add(scoresPanel);
	}
	
	private void addExitButton() {
		JPanel exitPanel = new JPanel();
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
