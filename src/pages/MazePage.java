package pages;


import game.Game;

import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicIntegerArray;

import javax.swing.*;

import maze.Maze;
import maze.MazeSettings;


public class MazePage extends Page implements KeyListener{
    private static final long serialVersionUID = 1L;
    public enum Result implements Page.Result {
		WON_GAME,
        LOST_GAME
    };
    
    // Status of key presses
    private static final int KEY_PRESSED = 1;
    private static final int KEY_UNPRESSED = 0;

	private JPanel sidePanel;
	private JPanel timerPanel;
	private JLabel timeLeft1;
	private JLabel timeLeft2;
	
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

	public MazePage.Result run() {
	    result = null;
	    
	    // Start collecting keys
	    pressedKeys = new AtomicIntegerArray(256);
        this.requestFocusInWindow();

		final Maze maze = new Maze(mazeSettings);
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
        	    
        	    maze.setPlayerTimerRelative(1, -(1.0 / Game.settings.FPS));
        	    maze.setPlayerTimerRelative(2, -(1.0 / Game.settings.FPS));
        	    updateTimers(maze);
        	    
        	    if (maze.getPlayerTimer(1) <= 0 || maze.getPlayerTimer(2) <= 0) {
        	        result = Result.LOST_GAME;
        	    }
        	    
                if (pressedKeys.get(KeyEvent.VK_LEFT) == KEY_PRESSED) {
                    maze.movePlayer(1, Maze.Direction.WEST);
                }
                if (pressedKeys.get(KeyEvent.VK_RIGHT) == KEY_PRESSED) {
                    maze.movePlayer(1, Maze.Direction.EAST);
                } 
                if (pressedKeys.get(KeyEvent.VK_UP) == KEY_PRESSED) {
                    maze.movePlayer(1, Maze.Direction.NORTH);
                } 
                if (pressedKeys.get(KeyEvent.VK_DOWN) == KEY_PRESSED) {
                    maze.movePlayer(1, Maze.Direction.SOUTH);
                } 
                if (pressedKeys.get(KeyEvent.VK_A) == KEY_PRESSED) {
                    maze.movePlayer(2, Maze.Direction.WEST);
                } 
                if (pressedKeys.get(KeyEvent.VK_D) == KEY_PRESSED) {
                    maze.movePlayer(2, Maze.Direction.EAST);
                } 
                if (pressedKeys.get(KeyEvent.VK_W) == KEY_PRESSED) {
                    maze.movePlayer(2, Maze.Direction.NORTH);
                } 
                if (pressedKeys.get(KeyEvent.VK_S) == KEY_PRESSED) {
                    maze.movePlayer(2, Maze.Direction.SOUTH);
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
	
	public void setMazeSettings(MazeSettings newSettings) {
	    mazeSettings = newSettings;
	}
	
	private void updateTimers(Maze maze) {
	    timeLeft1.setText("Player1: " + String.format("%.2f", maze.getPlayerTimer(1)));
        if (maze.isMultiplayer()) {
            timeLeft2.setText("Player2: " + String.format("%.2f", maze.getPlayerTimer(2)));
            timeLeft2.setVisible(true);
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
        timeLeft1 = Components.makeText("Player1: ", 15);
        timeLeft2 = Components.makeText("Player2: ", 15);
        timeLeft2.setVisible(false);
        timerPanel.add(timeLeft1);
        timerPanel.add(timeLeft2);
        timerPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        
        sidePCon.fill = GridBagConstraints.BOTH;
        sidePCon.gridy = 1;
        sidePCon.ipady = 0;
        sidePCon.weighty = 1;
        sidePanel.add(timerPanel, sidePCon);
        
        JButton returnButton = Components.makeButton("return");
        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("return to main menu");
                result = Result.LOST_GAME;
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
