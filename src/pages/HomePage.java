package pages;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HomePage extends Page {
	public enum Result implements Page.Result {
        PLAY_GAME,
        SHOW_INSTRUCTIONS,
        SHOW_SETTINGS,
        SHOW_HIGH_SCORES,
        QUIT_GAME,
    };
	private static final long serialVersionUID = 1L;
	private static JPanel mainPanel;
    private static Result result;
	
    public HomePage(JFrame frame) {
    	super();
    	mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(7, 1));
        
        JLabel blank = new JLabel("", JLabel.CENTER);
        mainPanel.add(blank);
        
        JLabel titleLabel = new JLabel("MAZE", JLabel.CENTER);
        mainPanel.add(titleLabel);
        
        mainPanel = new JPanel();
        addStartButton();
        addInstructionsButton();
        addSettingsButton();
        addHighScoresButton();
        addExitButton();
        
        result = null;
    }

    public HomePage.Result run() {
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
    	
        System.out.println("result is " + result);
        return result;
    }
    
    public JPanel getHomePanel() {
        return mainPanel;
    }
    
    private synchronized static void addStartButton() {
        JPanel startPanel = new JPanel();
        startPanel.setLayout(new FlowLayout());
        
        JButton startMaze = Components.makeButton("Start maze");
		startMaze.addActionListener(new ActionListener() {
			public synchronized void actionPerformed(ActionEvent e) {
				// some action to generate maze and display a MazePage
				System.out.println("hello");
				result = Result.PLAY_GAME;
				System.out.println("pressedButton is now trueeeee");
				System.out.println("Result.play_game is" + result);
			}
		});
		
		startPanel.add(startMaze);
		mainPanel.add(startPanel);
    }
    
	private synchronized static void addInstructionsButton() {
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
		mainPanel.add(instructionsPanel);
	}
    
	private static void addSettingsButton() {
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
		mainPanel.add(settingsPanel);
	}
	
	private static void addHighScoresButton() {
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
		mainPanel.add(scoresPanel);
	}
	
	private static void addExitButton() {
		JPanel exitPanel = new JPanel();
		exitPanel.setLayout(new FlowLayout());

		JButton exit = Components.makeButton("Exit");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				result = Result.QUIT_GAME;
			}
		});
		exitPanel.add(exit);
		mainPanel.add(exitPanel);
	}
	
}
