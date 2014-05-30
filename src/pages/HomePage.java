package pages;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HomePage extends Page {
    private static final long serialVersionUID = 1L;
    public enum Result implements Page.Result {
        PLAY_GAME_SINGLEPLAYER,
        PLAY_GAME_MULTIPLAYER,
        SHOW_INSTRUCTIONS,
        SHOW_HIGH_SCORES,
        SHOW_SETTINGS,
        PLAY_CUSTOM,
        QUIT_GAME,
    };

    private volatile Result result;
	private JButton startMaze;
	
    public HomePage() {
    	super();
        setLayout(new GridLayout(6, 1));
        
        //JLabel blank = new JLabel("", JLabel.CENTER);
        //add(blank);
        
        JLabel titleLabel = Components.makeTitle("A-MAZE");
        add(titleLabel);
        
        addStartButton();
        addResultPanel("Instructions", Result.SHOW_INSTRUCTIONS);
        addResultPanel("Settings", Result.SHOW_SETTINGS);
        addResultPanel("Custom Game", Result.PLAY_CUSTOM);
        addResultPanel("Quit", Result.QUIT_GAME);
        
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
    
    public void setStartButtonText(String newText) {
        startMaze.setText(newText);
    }
    
    private void addStartButton() {
        JPanel startPanel = Components.makePanel();
        startPanel.setLayout(new FlowLayout());
        
        startMaze = Components.makeButton("Play");
		startMaze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// some action to generate maze and display a MazePage
				result = Result.PLAY_GAME_SINGLEPLAYER;
			}
		});
		
		startPanel.add(startMaze);
		add(startPanel);
    }
    
	private void addResultPanel(String text, final HomePage.Result buttonResult) {
	    JPanel panel = Components.makePanel();
        panel.setLayout(new FlowLayout());

        JButton exit = Components.makeButton(text);
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                result = buttonResult;
            }
        });
        panel.add(exit);
        add(panel);
	}
}
