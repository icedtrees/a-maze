package pages;


import game.Game;

import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicIntegerArray;

import javax.swing.*;

import maze.Maze;
import maze.Direction;
import maze.MazeSettings;


/**
 * This page contains the actual maze itself, along with game state information
 * in a sidebar (JPanel) and a 'Return' button to go back to the main screen.
 */
public class MazePage extends Page implements KeyListener{
    private static final long serialVersionUID = 1L;
    public enum Result implements Page.Result {
		WON_GAME,
        LOST_GAME,
        RETURN_HOME
    };
    
    // Status of key presses
    private static final int KEY_PRESSED = 1;
    private static final int KEY_UNPRESSED = 0;

	private JPanel sidePanel;
	private JPanel timerPanel;
	private JLabel timeLeft1;
	private JLabel timeLeft2;
	private volatile boolean timeStarted;
	private JPanel hintsPanel;
	private JLabel hintsLeftLabel;
	
	private Maze maze;
	
	public volatile Result result;
	public volatile AtomicIntegerArray pressedKeys;
	
	private MazeSettings mazeSettings;

	public MazePage() {
		super();
		setLayout(new GridBagLayout());

		result = null;
		
		GridBagConstraints c = new GridBagConstraints();
        sidePanel = Components.makePanel();
        sidePanel.setOpaque(true);
        sidePanel.setBackground(Color.DARK_GRAY);
        sidePanel.setLayout(new GridBagLayout());
        sidePanel.setPreferredSize(new Dimension(225, 100));
        c.fill = GridBagConstraints.BOTH;
		c.gridx = 1;
		c.weightx = 0.04;
		add(sidePanel, c);
		
		addKeyListener(this);
		pressedKeys = new AtomicIntegerArray(256);
		
        drawSidebar();
        mazeSettings = new MazeSettings();
	}

	/**
	 * Runs the page.
	 */
	public MazePage.Result run() {
	    result = null;
	    
	    timeStarted = false;
	    
	    // Start collecting keys
	    pressedKeys = new AtomicIntegerArray(256);
        this.requestFocusInWindow();

		maze = new Maze(mazeSettings);
		updateTimers(maze);
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		add(maze, c);
		validate();
		
		/*
         * The main event loop which gets run every frame based on a frame-rate
         * in the Maze.FPS variable.
         */
		final MazePage mazePage = this;
        Timer actionLoop = new Timer();
        actionLoop.scheduleAtFixedRate(new TimerTask() {
        	@Override
        	public void run() {
        	    if (timeStarted) {
            	    maze.setPlayerTimerRelative(1, -(1.0 / Game.settings.FPS));
            	    maze.setPlayerTimerRelative(2, -(1.0 / Game.settings.FPS));
            	    updateTimers(maze);
        	    }
        	    
        	    if (maze.getPlayerTimer(1) <= 0 || maze.getPlayerTimer(2) <= 0) {
        	        result = Result.LOST_GAME;
        	    }
        	    
                if (pressedKeys.get(KeyEvent.VK_A) == KEY_PRESSED) {
                    maze.movePlayer(1, Direction.WEST);
                } 
                if (pressedKeys.get(KeyEvent.VK_D) == KEY_PRESSED) {
                    maze.movePlayer(1, Direction.EAST);
                } 
                if (pressedKeys.get(KeyEvent.VK_W) == KEY_PRESSED) {
                    maze.movePlayer(1, Direction.NORTH);
                } 
                if (pressedKeys.get(KeyEvent.VK_S) == KEY_PRESSED) {
                    maze.movePlayer(1, Direction.SOUTH);
                }

                if (pressedKeys.get(KeyEvent.VK_LEFT) == KEY_PRESSED) {
                    maze.movePlayer(2, Direction.WEST);
                }
                if (pressedKeys.get(KeyEvent.VK_RIGHT) == KEY_PRESSED) {
                    maze.movePlayer(2, Direction.EAST);
                } 
                if (pressedKeys.get(KeyEvent.VK_UP) == KEY_PRESSED) {
                    maze.movePlayer(2, Direction.NORTH);
                } 
                if (pressedKeys.get(KeyEvent.VK_DOWN) == KEY_PRESSED) {
                    maze.movePlayer(2, Direction.SOUTH);
                } 
                
                maze.nextFrame();
        	    SwingUtilities.getWindowAncestor(mazePage).repaint();
        	    if (maze.playersFinished()) {
        	        result = Result.WON_GAME;
        	    }
        	}
        }, 1000/Game.settings.FPS, 1000/Game.settings.FPS);
        
		while (result == null) {
    		// will need to modify this busy block to thread.notify and thread.wait?
    		try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		remove(maze);
		actionLoop.cancel();
		
		return result;
	}
	
	/**
	 * Applies settings to the page to allow the player to choose optional or
	 * variable features which modify the maze. 
	 * @param newSettings a <code>MazeSettings</code> object encapsulating
	 * information about chosen game settings
	 */
	public void setMazeSettings(MazeSettings newSettings) {
	    mazeSettings = newSettings;
	}
	
	private void updateTimers(Maze maze) {
		if (maze.playerFinished(1)) {
			timeLeft1.setText("Player1: Finished!");
		} else {
			timeLeft1.setText("Player1: " + String.format("%.2f", maze.getPlayerTimer(1)));
		}
        if (maze.isMultiplayer()) {
        	if (maze.playerFinished(2)) {
        		timeLeft2.setText("Player2: Finished!");
        	} else {
        		timeLeft2.setText("Player2: " + String.format("%.2f", maze.getPlayerTimer(2)));
        	}
            timeLeft2.setVisible(true);
        }
        if (mazeSettings.getHints()) {
            hintsLeftLabel.setText("Hints left: " + maze.getPlayerHints(1));
        } else {
            hintsLeftLabel.setText("No hints");
        }
	}
	
    private void drawSidebar() {
        JLabel mazeTitle = Components.makeText("MAZE", 40);
        mazeTitle.setAlignmentX(JLabel.CENTER);
        GridBagConstraints sidePCon = new GridBagConstraints();
        sidePCon.fill = GridBagConstraints.BOTH;
        sidePCon.gridx = 0;
        sidePCon.gridy = 0;
		sidePCon.ipady = 30;
//		c.weightx = 10;
		sidePCon.weighty = 0.20;
        sidePanel.add(mazeTitle, sidePCon);
        
        timerPanel = Components.makePanel();
        timerPanel.setLayout(new BoxLayout(timerPanel, BoxLayout.PAGE_AXIS));
        timeLeft1 = Components.makeText("Player1: ", 25);
        timeLeft2 = Components.makeText("Player2: ", 25);
        timeLeft2.setVisible(false);
        timerPanel.add(timeLeft1);
        timerPanel.add(timeLeft2);
        timerPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));

        hintsPanel = Components.makePanel();
        hintsLeftLabel = Components.makeText("Hints Left: ", 25);
        hintsPanel.add(hintsLeftLabel);


        JPanel hintsPanel2 = Components.makePanel();
        JLabel instructionsLabel = Components.makeText("(Press H to get hints)", 25);
        hintsPanel2.add(instructionsLabel);
        
        sidePCon.fill = GridBagConstraints.BOTH;
        sidePCon.gridy = 1;
        sidePCon.ipady = 0;
        sidePCon.weighty = 1;
        sidePanel.add(timerPanel, sidePCon);
        
        sidePCon.gridy = 2;
        sidePCon.ipady = 0;
        sidePanel.add(hintsPanel, sidePCon);
        sidePCon.gridy = 3;
        sidePanel.add(hintsPanel2, sidePCon);
        
        sidePCon.fill = GridBagConstraints.BOTH;
        sidePCon.gridy = 4;
        sidePCon.ipady = 0;
        sidePCon.weighty = 1;
        
        JButton returnButton = Components.makeButton("return");
        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("return to main menu");
                result = Result.RETURN_HOME;
            }
        });
        sidePCon.fill = GridBagConstraints.HORIZONTAL;
        sidePCon.gridy = 3;
        sidePCon.weighty = 0.1;
        sidePanel.add(returnButton, sidePCon);
    }
	

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() < 256) {
            pressedKeys.set(e.getKeyCode(), KEY_PRESSED);
            if (e.getKeyCode() == KeyEvent.VK_H) {
                if (maze.getPlayerHints(1) > 0) {
                    maze.showHint(1, maze.getPlayerHints(1) * 4);
                    maze.showHint(2, maze.getPlayerHints(2) * 4);
                }
            }
            if (!timeStarted) {
                timeStarted = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() < 256) {
            pressedKeys.set(e.getKeyCode(), KEY_UNPRESSED);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

}
