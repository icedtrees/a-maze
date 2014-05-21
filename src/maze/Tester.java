package maze;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import maze.Maze.Direction;

public class Tester {
    public static void main(String[] args) {
        final Maze myMaze = new Maze(15, 610, 1);
        
        final JFrame f = new JFrame();
        f.setLayout(new GridBagLayout());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        f.add(myMaze, c);
        
        final Maze maze2 = new Maze(15, 610, 1);
        c.gridx = 1;
        f.add(maze2, c);
        
        f.pack();
        f.setVisible(true);
        
        /*
         * The main event loop which gets run every frame based on a frame-rate
         * in the 'fps' variable.
         */
        Timer actionLoop = new Timer();
        actionLoop.scheduleAtFixedRate(new TimerTask() {
        	@Override
        	public void run() {
        		myMaze.nextFrame();
        		f.repaint();
        	}
        }, 1000/Maze.FPS, 1000/Maze.FPS);
        
        Thread thread1 = new Thread() {
            public void run() {
            	myMaze.genMazeDFS();
            }
        };
        
        Thread thread2 = new Thread() {
            public void run() {
            	maze2.genMazeDFSBranch(5);
            }
        };
        
        thread1.start();
        thread2.start();
        try {
			thread1.join();
			thread2.join();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        
        /*
         * A simple dfs approach to find a path to the end of the maze.
         */
        Set<Coordinate> seen = new HashSet<Coordinate>();
        Stack<Coordinate> s = new Stack<Coordinate>();
        Stack<BranchCounter> branchPoints = new Stack<BranchCounter>();
        
        Coordinate startCoordinate = new Coordinate(1, 0, null);
        Coordinate endCoordinate = new Coordinate(myMaze.getWidth() - 2, myMaze.getHeight() - 1, null);
        System.out.println("End goal is " + endCoordinate.toString());
        
        s.add(startCoordinate);
        branchPoints.add(new BranchCounter(startCoordinate, 1));
        
        while (!s.isEmpty()) {
        	Coordinate cur = s.pop();
        	
        	// Check the seen set
        	if (seen.contains(cur)) {
        		continue;
        	}
        	seen.add(cur);
        	
        	// Try and make the move
        	while (true) {
        		if (myMaze.movePlayer(cur.getDir())) {
        			break;
        		}
        		try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        	myMaze.setTile(cur.getX(), cur.getY(), Tile.EXPLORED);
        	
        	// Check if we're at the end
        	if (cur.equals(endCoordinate)) {
        		break;
        	}
        	
        	int numAdj = 0;
        	for (Coordinate coord : cur.adj()) {
        		if (myMaze.isSpace(coord.getX(), coord.getY())) {
        			s.push(coord);
        			numAdj++;
        		}
        	}
        	
        	branchPoints.peek().pushPath(cur);
        	if (numAdj > 1) {
        		branchPoints.push(new BranchCounter(cur, numAdj - 1));
        	}
        	if (numAdj == 0) {
        		System.out.println("Deadend");
        		Coordinate curPath = null;
        		BranchCounter myBranchCounter = branchPoints.peek();
        		while (!myBranchCounter.pathEmpty()) {
        			curPath = myBranchCounter.popPath();
        			Direction reverseDir = curPath.getDir().reverse();
        			while (true) {
                		if (myMaze.movePlayer(reverseDir)) {
                			break;
                		}
                		try {
        					Thread.sleep(50);
        				} catch (InterruptedException e) {
        					// TODO Auto-generated catch block
        					e.printStackTrace();
        				}
                	}
        		}
        		myBranchCounter.decBranchCount();
        		if (myBranchCounter.getBranchCount() == 0) {
        			branchPoints.pop();
        		}
        	}
        }
    }

}
