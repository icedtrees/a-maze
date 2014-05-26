package pages;


import game.Game;

import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

import maze.Maze;


public class MazePage extends Page implements KeyListener{
	public enum Result implements Page.Result {
		
        RETURN_HOME
    };
	private static final long serialVersionUID = 1L;
	private JPanel sidePanel;
	public volatile Result result;
	public volatile Maze.Direction currentPress;
	
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
		currentPress = null;
	}

	public MazePage.Result run() {
	    result = null;
        this.requestFocusInWindow();
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		
		final Maze maze = new Maze(15, 600, 1);
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
        	    SwingUtilities.getWindowAncestor(mazePage).repaint();
        		maze.nextFrame();
        	}
        }, 1000/Game.settings.FPS, 1000/Game.settings.FPS);
		
        // TODO this takes a long time - third argument is delay
 		// You can set it to 0 or remove it entirely if you want
 		maze.genMazeDFSBranch(5, 0, 10);
        
		while (result == null) {
    		// will need to modify this busy block to thread.notify and thread.wait?
    		try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            if (currentPress != null) {
                maze.movePlayer(currentPress);
                SwingUtilities.getWindowAncestor(this).repaint();
                currentPress = null;
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
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            currentPress = Maze.Direction.WEST;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            currentPress = Maze.Direction.EAST;
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            currentPress = Maze.Direction.NORTH;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            currentPress = Maze.Direction.SOUTH;
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

}
