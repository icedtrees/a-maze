package maze.autoplayer;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import maze.Tile;
import maze.Maze;
import maze.Maze.Direction;

public class DFSPlayer {

	public static void play(Maze maze) {
		/*
         * A simple dfs approach to find a path to the end of the maze.
         */
        Set<Coordinate> seen = new HashSet<Coordinate>();
        Stack<Coordinate> s = new Stack<Coordinate>();
        Stack<BranchCounter> branchPoints = new Stack<BranchCounter>();
        
        Coordinate startCoordinate = new Coordinate(1, 0, null);
        Coordinate endCoordinate = new Coordinate(maze.getMazeWidth() - 2, maze.getMazeHeight() - 1, null);
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
        	
        	// Make the move
        	try {
				maze.movePlayer(cur.getDir());
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	maze.setTile(cur.getX(), cur.getY(), Tile.EXPLORED);
        	
        	// Check if we're at the end
        	if (cur.equals(endCoordinate)) {
        		break;
        	}
        	
        	int numAdj = 0;
        	for (Coordinate coord : cur.adj()) {
        		if (!seen.contains(coord) && maze.isSpace(coord.getX(), coord.getY())) {
        			s.push(coord);
        			numAdj++;
        		}
        	}
        	
        	branchPoints.peek().pushPath(cur);
        	if (numAdj > 1) {
        		branchPoints.push(new BranchCounter(cur, numAdj - 1));
        	}
        	if (numAdj == 0) {
        		Coordinate curPath = null;
        		BranchCounter myBranchCounter = branchPoints.peek();
        		while (!myBranchCounter.pathEmpty()) {
        			curPath = myBranchCounter.popPath();
        			Direction reverseDir = curPath.getDir().reverse();
        			try {
						maze.movePlayer(reverseDir);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
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
