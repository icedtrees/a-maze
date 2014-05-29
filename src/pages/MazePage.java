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
	

	public MazePage() {
		super();
		setLayout(new GridBagLayout());

		result = null;
		
		GridBagConstraints c = new GridBagConstraints();
        sidePanel = Components.makePanel();
        sidePanel.setLayout(new GridLayout(5, 1));
        sidePanel.setMinimumSize(new Dimension(150, 100));
		c.gridx = 1;
		//c.ipadx = 30;
		add(sidePanel, c);
		
		addKeyListener(this);
		pressedKeys = new AtomicIntegerArray(256);
		
        drawSidebar();
	}

	public MazePage.Result run() {
	    result = null;
	    
	    // Start collecting keys
        this.requestFocusInWindow();
				
		int mazeHeight = 17;
		int branching = 10;
		int straightness = 0;
		int startingTime = 60;
    	
    	java.util.List<Modification> mods = new java.util.ArrayList<Modification>();
    	int clockFreq = 2;
    	int bootsFreq = 2;
    	int torchFreq = 3;
    	int scale = ((torchFreq + clockFreq + bootsFreq) / 100) + 1;
    	int numSpaces = (int) (mazeHeight * mazeHeight * Maze.DEFAULT_RATIO);
    	System.out.println(numSpaces);
    	System.out.println(bootsFreq * numSpaces / scale / 100);
    	
		mods.add(new FogMod(4, torchFreq * numSpaces / scale / 100));
		mods.add(new ClockMod(clockFreq * numSpaces / scale / 100));
		mods.add(new SpeedMod(bootsFreq * numSpaces / scale / 100));
// 		mods.add(new ShiftingWallsMod(10, 8));
		
		MazeSettings mazeInfo = new MazeSettings(false, true, mazeHeight, branching, straightness, startingTime, System.nanoTime(), mods);
		
		final Maze maze = new Maze(mazeInfo);
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
        	    
        	    maze.setPlayer1TimerRelative(-(1.0 / Game.settings.FPS));
        	    maze.setPlayer2TimerRelative(-(1.0 / Game.settings.FPS));
        	    updateTimers(maze);
        	    
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
	
	private void updateTimers(Maze maze) {
	    timeLeft1.setText("Player1: " + String.format("%.2f", maze.getPlayer1Timer()));
        if (maze.isMultiplayer()) {
            timeLeft2.setText("Player2: " + String.format("%.2f", maze.getPlayer2Timer()));
        }
	}
	
    private void drawSidebar() {
        JLabel mazeTitle = Components.makeText("MAZE", 20);
        mazeTitle.setAlignmentX(JLabel.CENTER);
        sidePanel.add(mazeTitle);

        timeLeft1 = Components.makeText("Player1: ", 15);
        sidePanel.add(timeLeft1);
        
        timeLeft2 = Components.makeText("Player2: ", 15);
        sidePanel.add(timeLeft2);
        
        JButton returnButton = Components.makeButton("return");
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
