package pages;


import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import maze.Maze;


public class MazePage extends Page {
	public enum Result implements Page.Result {
        RETURN_HOME
    };
	private static final long serialVersionUID = 1L;
	public static Result result;
	// in mainPanel will be a mazePanel where the maze game will be shown
	// and sidebarPanel on the right 
	
	public MazePage() {
		super();
		setLayout(new GridBagLayout());
//		GridBagConstraints c = new GridBagConstraints();
//		
//		//panel where the maze will be drawn
//		// JPanel mazePanel = new Maze();
//		// JPanel mazePanel = new Maze();
//		
//		final Maze maze = new Maze(15, 600, 1);
//		maze.genMazeDFSBranch(5);
//		c.gridx = 0;
//		c.gridy = 0;
//		c.fill = GridBagConstraints.BOTH;
//		c.weightx = 1;
//		c.weighty = 1;
//		add(maze, c);
//		
//		repaint();
//		
//		/*
//         * The main event loop which gets run every frame based on a frame-rate
//         * in the Maze.FPS variable.
//         */
//        Timer actionLoop = new Timer();
//        actionLoop.scheduleAtFixedRate(new TimerTask() {
//        	@Override
//        	public void run() {
//        		maze.nextFrame();
//        		repaint();
//        	}
//        }, 1000/Maze.FPS, 1000/Maze.FPS);
		
	}

	@Override
	public Result run() {
		GridBagConstraints c = new GridBagConstraints();
		
		//panel where the maze will be drawn
		// JPanel mazePanel = new Maze();
		// JPanel mazePanel = new Maze();
		
		final Maze maze = new Maze(15, 600, 1);
		maze.genMazeDFSBranch(5);
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
        
        return null;
	}
	

}
