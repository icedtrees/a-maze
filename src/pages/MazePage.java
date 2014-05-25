package pages;


import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

import maze.Maze;


public class MazePage extends Page {
	public enum Result implements Page.Result {
		
        RETURN_HOME
    };
	private static final long serialVersionUID = 1L;
	private JPanel sidePanel;
	public volatile Result result;
	
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

	
		final Maze maze = new Maze(15, 600, 1);
		maze.genMazeDFSBranch(5, 100);
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		add(maze, c);
		
		repaint();
		
		/*
         * The main event loop which gets run every frame based on a frame-rate
         * in the Maze.FPS variable.
         */
        Timer actionLoop = new Timer();
        actionLoop.scheduleAtFixedRate(new TimerTask() {
        	@Override
        	public void run() {
        		maze.nextFrame();
        		repaint();
        	}
        }, 1000/Maze.FPS, 1000/Maze.FPS);
		
	}
	
    public void setMazeResult(MazePage.Result newResult) {
        result = newResult;
    }

	@Override
	public MazePage.Result run() {
		while (result == null) {
    		// will need to modify this busy block to thread.notify and thread.wait?
    		try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			GridBagConstraints c = new GridBagConstraints();
//			
//			//panel where the maze will be drawn
//			// JPanel mazePanel = new Maze();
//			// JPanel mazePanel = new Maze();
//			
//			final Maze maze = new Maze(15, 600, 1);
//			maze.genMazeDFSBranch(5);
//			c.gridx = 0;
//			c.gridy = 0;
//			c.fill = GridBagConstraints.BOTH;
//			c.weightx = 1;
//			c.weighty = 1;
//			add(maze, c);
//			
//			repaint();
//			
//			/*
//	         * The main event loop which gets run every frame based on a frame-rate
//	         * in the Maze.FPS variable.
//	         */
//	        Timer actionLoop = new Timer();
//	        actionLoop.scheduleAtFixedRate(new TimerTask() {
//	        	@Override
//	        	public void run() {
//	        		maze.nextFrame();
//	        		repaint();
//	        	}
//	        }, 1000/Maze.FPS, 1000/Maze.FPS);
   
		}
		return result;
	}
	
	private void addReturnButton() {		
        JButton returnBut = Components.makeButton("return");
        returnBut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	System.out.println("return to main menu");
            	result = Result.RETURN_HOME;
            }
        });
		
		sidePanel.add(returnBut);
	}

}
