package window;
import maze.Maze;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class MazeWindow extends Window {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// in mainPanel will be a mazePanel where the maze game will be shown
	// and sidebarPanel on the right 
	private JPanel mainPanel;
	
	public MazeWindow(JFrame frame) {
		super(frame);
		mainPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//panel where the maze will be drawn
		// JPanel mazePanel = new Maze();
		// JPanel mazePanel = new Maze();
		
		
		
	}
	
	public JPanel getMazePanel() {
		return mainPanel;
	}
}
