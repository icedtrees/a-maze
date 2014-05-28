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
	private JLabel timeLeft1;
	private JLabel timeLeft2;
	
	public volatile Result result;
	public AtomicIntegerArray pressedKeys;
	
	private MazeStats mazeInfo;
	
	// in mainPanel will be a mazePanel where the maze game will be shown
	// and sidebarPanel on the right 
	
	public MazePage() {
		super();
		setLayout(new GridBagLayout());

		result = null;

        mazeInfo = new MazeStats(2, 100);
		
		GridBagConstraints c = new GridBagConstraints();
        sidePanel = Components.makePanel();
        sidePanel.setLayout(new GridLayout(5, 1));
        sidePanel.setMinimumSize(new Dimension(150, 100));
		c.gridx = 1;
		//c.ipadx = 30;
		add(sidePanel, c);
		
		drawSidebar();
		
		addKeyListener(this);
		pressedKeys = new AtomicIntegerArray(256);
		

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
		
		int difficulty = 39;
    	int mazeHeight = 5 + ((difficulty*3)/10);
    	int straightness = 900 - ((difficulty % 10) * 100);
    	int branching = 93 - ((difficulty % 10) * 10);
    	
    	java.util.List<Modification> mods = new java.util.ArrayList<Modification>();
//		mods.add(new FogMod(4, 4));
		mods.add(new ClockMod(5));
		mods.add(new SpeedMod(5));
// 		mods.add(new ShiftingWallsMod(1, 8));
		
		mazeInfo = new MazeStats(2, 100);
		
		final Maze maze = new Maze(mazeHeight, 600, straightness, branching, mazeInfo, mods);
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
        	    
        	    mazeInfo.setTimer(1, mazeInfo.getTimer(1) - (1.0 / Game.settings.FPS));
        	    if (mazeInfo.getNumPlayers() > 1) {
        	        mazeInfo.setTimer(2, mazeInfo.getTimer(2) - (1.0 / Game.settings.FPS));
        	    }
        	    updateTimers();
        	    
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
	
	private void updateTimers() {
	    timeLeft1.setText("Player1: " + String.format("%.2f", mazeInfo.getTimer(1)));
        if (mazeInfo.getNumPlayers() > 1) {
            timeLeft2.setText("Player2: " + String.format("%.2f", mazeInfo.getTimer(2)));
        }
	}
	
    private void drawSidebar() {
        JLabel mazeTitle = Components.makeText("MAZE", 20);
        mazeTitle.setAlignmentX(JLabel.CENTER);
        mazeTitle.setForeground(Color.BLACK);
        sidePanel.add(mazeTitle);

        timeLeft1 = new JLabel("Player1: ", JLabel.CENTER);
        sidePanel.add(timeLeft1);
        
        if (mazeInfo.getNumPlayers() > 1) {
            timeLeft2 = new JLabel("Player2: ", JLabel.CENTER);
            sidePanel.add(timeLeft2);
        }
        
        JButton returnButton = Components.makeButton("return");
        returnButton.setForeground(Color.BLACK);
        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("return to main menu");
                result = Result.RETURN_HOME;
            }
        });
        sidePanel.add(returnButton);
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
