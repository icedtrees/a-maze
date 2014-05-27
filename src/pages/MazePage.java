package pages;


import game.Game;

import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicIntegerArray;

import javax.swing.*;

import maze.Maze;
import maze.MazeStats;
import maze.modification.*;


public class MazePage extends Page implements KeyListener{
    private static final long serialVersionUID = 1L;
    public enum Result implements Page.Result {
		
        RETURN_HOME
    };
    
    // Status of key presses
    private static final int KEY_PRESSED = 1;
    private static final int KEY_UNPRESSED = 0;

	private JPanel sidePanel;
	public volatile Result result;
	public AtomicIntegerArray pressedKeys;
	
	private MazeStats stats;
	
	// in mainPanel will be a mazePanel where the maze game will be shown
	// and sidebarPanel on the right 
	
	public MazePage() {
		super();
		setLayout(new GridBagLayout());

		result = null;

		GridBagConstraints c = new GridBagConstraints();
        sidePanel = new JPanel();
        sidePanel.setLayout(new GridLayout(5, 1));
		c.gridx = 1;
		c.weightx = 0.25;
		add(sidePanel, c);
		
		JLabel mazeTitle = new JLabel("MAZE", JLabel.CENTER);
		
		sidePanel.add(mazeTitle);
		
		addReturnButton();
		
		addKeyListener(this);
		pressedKeys = new AtomicIntegerArray(256);
		
		stats = null;
	}

	public MazePage.Result run() {
	    result = null;
	    
	    // Start collecting keys
        this.requestFocusInWindow();
        
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		
		int difficulty = 28;
    	int mazeHeight = 5 + ((difficulty*3)/10);
    	int straightness = 900 - ((difficulty % 10) * 100);
    	int branching = 93 - ((difficulty % 10) * 10);
    	
    	java.util.List<Modification> mods = new java.util.ArrayList<Modification>();
		mods.add(new FogMod(4, 4));
		mods.add(new ClockMod(5));
 		mods.add(new ShiftingWallsMod(10, 8));
		
		stats = new MazeStats(2, 100);
		
		final Maze maze = new Maze(mazeHeight, 600, straightness, branching, stats, mods);
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
	
	private void addReturnButton() {		
        JButton returnBut = Components.makeButton("return");
        returnBut.setForeground(Color.BLACK);
        returnBut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	System.out.println("return to main menu");
            	result = Result.RETURN_HOME;
            }
        });
		
		sidePanel.add(returnBut);
	}

    @Override
    public void keyPressed(KeyEvent e) {
        pressedKeys.set(e.getKeyCode(), KEY_PRESSED);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys.set(e.getKeyCode(), KEY_UNPRESSED);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

}
