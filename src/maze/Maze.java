package maze;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import javax.swing.JComponent;


public class Maze extends JComponent {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private final static boolean DEBUGGING = false;
    private final static double DEFAULT_RATIO = 1.2;    
    private final Random rand;
    
    public final static int FPS = 50;
    
    private Tile[][] tiles;
    private int mazeWidth;
    private int mazeHeight;
    private int complexity;
    
    private Player player;
    
    public static enum Direction {
    	NORTH(0, -1),
    	EAST(1, 0),
    	SOUTH(0, 1),
    	WEST(-1, 0);
        
        private int dx;
        private int dy;
        private Direction reverse;
        
        static {
        	NORTH.reverse = SOUTH;
        	EAST.reverse = WEST;
        	SOUTH.reverse = NORTH;
        	WEST.reverse = EAST;
        }
        
        private Direction(int dx, int dy) {
        	this.dx = dx;
        	this.dy = dy;
        }
        
        public int dx() {
        	return dx;
        }
        public int dy() {
        	return dy;
        }
        public Direction reverse() {
        	return reverse;
        }
    }
    
    public Maze(int newHeight, int displayHeight, int newComplexity) {
        this(newHeight, displayHeight, newComplexity, System.nanoTime());
    }
    public Maze(int newHeight, int displayHeight, int newComplexity, long seed) {
    	this((int) (newHeight * DEFAULT_RATIO), newHeight,
                (int) (displayHeight * DEFAULT_RATIO), displayHeight,
                newComplexity, seed);
    }
    public Maze(int newWidth, int newHeight,
            int displayWidth, int displayHeight,
            int newComplexity, long seed) {
    	player = new Player();

        mazeWidth = 2 * newWidth + 1;
        mazeHeight = 2 * newHeight + 1;
        setPreferredSize(new Dimension(displayWidth, displayHeight));
        tiles = new Tile[mazeWidth][mazeHeight];
        for (int row = 0; row < mazeHeight; row++) {
            for (int col = 0; col < mazeWidth; col++) {
                tiles[col][row] = new Tile(Tile.WALL, col, row);
            }
        }
        
        complexity = newComplexity;
        
        rand = new Random(seed);
    }
    
    /*
     * Getters
     */
    public int getMazeWidth() {
        return mazeWidth;
    }
    public int getMazeHeight() {
        return mazeHeight;
    }
    public int getComplexity() {
        return complexity;
    }
    
    @Override
    public String toString() {
        String[] asciiOutput = new String[]{" ", "#"};
        String mazeString = "";
        
        for (int row = 0; row < mazeHeight; row++) {
            for (int col = 0; col < mazeWidth; col++) {
                mazeString += asciiOutput[tiles[col][row].getValue()];
            }
            mazeString += "\n";
        }
        
        return mazeString;
    }
    
    @Override
    public void paintComponent(Graphics g) {
    	/*
    	 * Initial calculations and background
    	 */
    	
    	// Calculate tileSize based on our available width and height
        Rectangle bounds = this.getBounds();
        int tileSize = Math.min(bounds.width/mazeWidth, bounds.height/mazeHeight);
        
        // Calculate how much margin to leave to center the maze
        int xMargin = (bounds.width - (mazeWidth * tileSize)) / 2;
        int yMargin = (bounds.height - (mazeHeight * tileSize)) / 2;
        
        // Set initial clipping to the entire bounds
        g.setClip(0, 0, bounds.width, bounds.height);
        
        // Draw green background to prove transparency of later clipping
        g.setColor(Color.GREEN);
    	g.fillRect(0, 0, bounds.width, bounds.height);
        
    	/*
    	 * Draw each tile
    	 */
    	
    	// Set clipping to just the maze display area
    	g.setClip(xMargin, yMargin, tileSize*mazeWidth, tileSize*mazeHeight);
        for (int row = 0; row < mazeHeight; row++) {
            for (int col = 0; col < mazeWidth; col++) {
            	int newX = col*tileSize + xMargin;
            	int newY = row*tileSize + yMargin;
            	
            	// Create a small localised graphics context for each
            	// tile to draw to to that they can't draw anywhere on the
            	// canvas and ruin everything.
            	Graphics newG = g.create(newX, newY, tileSize, tileSize);
            	tiles[col][row].draw(newG, tileSize);
            }
        }
        
        /*
         * Draw overlay objects such as player
         */
        
        // Create localised graphics context for player to draw to
        Graphics newG = g.create(
        		(int) (player.getCurX() * tileSize + xMargin),
        		(int) (player.getCurY() * tileSize + yMargin),
        		tileSize, tileSize);
        player.draw(newG, tileSize);
    }
    
    public void shiftTile(int x, int y) throws InterruptedException {
    	for (Direction dir : Direction.values()) {
    		if (isSpace(x + dir.dx(), y + dir.dy())) {
    			continue;
    		}
    		tiles[x][y].shiftWall(dir);
    		break;
    	}
    }
    
    public void shiftTiles(int n) {
    	boolean[][] curTileWalls = new boolean[mazeWidth][mazeHeight];
    	for (int col = 0; col < mazeWidth; col++) {
    		for (int row = 0; row < mazeHeight; row++) {
    			if (tiles[col][row].getValue() == Tile.WALL) {
    				curTileWalls[col][row] = true;
    			} else {
    				curTileWalls[col][row] = false;
    			}
    		}
    	}
    	
    	List<Coord> currentWalls = new ArrayList<Coord>();
    	List<Coord> currentSpaces = new ArrayList<Coord>();
    	int rowMagicNumber = 1;
    	for (int col = 1; col < mazeWidth - 1; col ++) {
    		rowMagicNumber = 3 - rowMagicNumber;
    		for (int row = rowMagicNumber; row < mazeHeight - rowMagicNumber; row += 2) {
    			if (curTileWalls[col][row]) {
    				currentWalls.add(new Coord(col, row));
    			} else {
    				currentSpaces.add(new Coord(col, row));
    			}
    		}
    	}
    	Collections.shuffle(currentWalls, rand);
    	Collections.shuffle(currentSpaces, rand);
    	
    	for (int i = 0; i < n; i++) {
    		Coord newWall;
    		newWall = currentSpaces.remove(0);
    		
    		int x = newWall.getX();
    		int y = newWall.getY();
    		curTileWalls[x][y] = true;
    		Coord firstCoord = null;
    		for (Direction dir : Direction.values()) {
    			if (!curTileWalls[x + dir.dx()][y + dir.dy()]) {
    				firstCoord = newWall.inDirection(dir);
    				break;
    			}
    		}
    		
    		Stack<Coord> s = new Stack<Coord>();
    		s.add(firstCoord);
    		
    		Set<Coord> seen = new HashSet<Coord>();
    		while (!s.isEmpty()) {
    			Coord cur = s.pop();
    			
    			if (seen.contains(cur)) {
    				continue;
    			}
    			seen.add(cur);
    			
    			System.out.println(cur);
    			
    			for (Direction dir : Direction.values()) {
    				int newX = cur.getX() + dir.dx();
    				int newY = cur.getY() + dir.dy();
    				if (newX < 0 || newX > mazeWidth - 1 || newY < 0 || newY > mazeHeight - 1) {
    					continue;
    				}
    				if (!curTileWalls[newX][newY]) {
    					s.push(new Coord(newX, newY));
    				}
    			}
    		}
    		
    		/*
    		 * Go through list of current walls and pick first one that
    		 * borders on one space in the seen set and one set not in
    		 */
    		Coord wallToRemove = null;
    		for (Coord cur : currentWalls) {
    			int numInSet = 0;
    			for (Direction dir : Direction.values()) {
    				int newX = cur.getX() + dir.dx();
    				int newY = cur.getY() + dir.dy();
    				Coord newCoord = cur.inDirection(dir);
    				if (newX < 0 || newX > mazeWidth - 1 || newY < 0 || newY > mazeHeight - 1) {
    					continue;
    				}
    				if (!curTileWalls[newX][newY] && seen.contains(newCoord)) {
    					numInSet++;
    				}
    			}
    			if (numInSet == 1) {
    				wallToRemove = cur;
    				break;
    			}
    		}
    		currentWalls.remove(wallToRemove);
    		curTileWalls[wallToRemove.getX()][wallToRemove.getY()] = false;
    		
    		// Shift the new wall/space tiles
    		try {
				shiftTile(newWall.getX(), newWall.getY());
				shiftTile(wallToRemove.getX(), wallToRemove.getY());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    }
    
    // TEST    
    public void movePlayer(Direction dir) throws InterruptedException {
    	player.move(dir);
    }
    
    public void nextFrame() {
    	for (int row = 0; row < mazeHeight; row++) {
    		for (int col = 0; col < mazeWidth; col++) {
    			tiles[col][row].nextFrame();
    		}
    	}
    	player.nextFrame();
    	tiles[player.getRealX()][player.getRealY()].interact(player);
    }
    
    public boolean isSpace(int x, int y) {
    	if (x == mazeWidth - 2 && y == mazeHeight - 1) {
    		// End goal
    		return true;
    	}
    	if (x < 1 || x > mazeWidth - 2 || y < 1 || y > mazeHeight - 2) {
    		// Outside of map bounds
    		return false;
    	}
    	return tiles[x][y].getValue() != Tile.WALL;
    }
    
    public void setTile(int x, int y, int value) {
    	tiles[x][y].setValue(value);
    }
    // TEST
    
    
    /*
     * Maze Generation algorithms
     */
    public void genMazeDFS() {
    	genMazeDFS(0);
    }
    public void genMazeDFS(int delay) {
        reset();
        
        int startx = mazeWidth / 2;
        int starty = mazeHeight / 2;
        if (startx % 2 == 0) {
            startx++;
        }
        if (starty % 2 == 0) {
            starty++;
        }
        Stack<MazeGenStep> s = new Stack<MazeGenStep>();
        s.add(new MazeGenStep(tiles[startx][starty], tiles[startx][starty]));
        
        while (!s.empty()) {
            MazeGenStep curStep = s.pop();
            if (!curStep.isValidMove()) {
                continue;
            }
            curStep.setValues(Tile.SPACE);
            
            if (delay > 0) {
	        	try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
            
            List<MazeGenStep> possibleMoves = new ArrayList<MazeGenStep>();
            Tile curTile = curStep.getNewTile();
            debug("Currently at " + curTile.toString());
            Tile curMove;
            // TODO use a for-each loop to iterative over the directions
            for (Direction dir : Direction.values()) {
                curMove = getRelativeTile(curTile, 2, dir);
                String dirString;
                if (dir == Direction.NORTH) {
                    dirString = "north";
                } else if (dir == Direction.EAST) {
                    dirString = "east";
                } else if (dir == Direction.SOUTH) {
                    dirString = "south";
                } else {
                    dirString = "west";
                }
                if (curMove != null) {
                    debug(String.format("    Consider move %s to %s", dirString,
                            curMove.toString()));
                    possibleMoves.add(new MazeGenStep(curMove,
                            getRelativeTile(curTile, dir)));
                }
            }
            // TODO see if you can attach multiple values to a part of the enum
            
            Collections.shuffle(possibleMoves, rand);
            for (MazeGenStep nextMove : possibleMoves) {
                s.add(nextMove);
            }
        }
        
        tiles[1][0].setValue(Tile.SPACE);
        tiles[mazeWidth-2][mazeHeight-1].setValue(Tile.SPACE);
        tiles[mazeWidth-2][mazeHeight-1].setContents(new Treasure(100));
    }
    
    public void genMazeDFSBranch(int firstBranchStep) {
    	genMazeDFSBranch(firstBranchStep, 0);
    }
    public void genMazeDFSBranch(int firstBranchStep, int delay) {
    	reset();
        
        List<Stack<MazeGenStep>> branches = new ArrayList<Stack<MazeGenStep>>();
        Stack<MazeGenStep> firstBranch = new Stack<MazeGenStep>();
        firstBranch.push(new MazeGenStep(tiles[1][1], tiles[1][0]));
        branches.add(firstBranch);
        
        int curNumSteps = 0;
        int branchAtStep = firstBranchStep;
        while (!branches.isEmpty()) {
        	curNumSteps++;
        	
        	for (int i = 0; i < branches.size(); i++) {
        		Stack<MazeGenStep> branch = branches.get(i);
	        	
		        if (!branch.isEmpty()) {
		            MazeGenStep curStep = branch.pop();
		            if (!curStep.isValidMove()) {
		                continue;
		            }
		            curStep.setValues(Tile.SPACE);
		            
		            if (delay > 0) {
			            try {
							Thread.sleep(delay);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		            }
		            
		            List<MazeGenStep> possibleMoves = new ArrayList<MazeGenStep>();
		            Tile curTile = curStep.getNewTile();
		            debug("Currently at " + curTile.toString());
		            Tile curMove;
		            // TODO use a for-each loop to iterative over the directions
		            for (Direction dir : Direction.values()) {
		                curMove = getRelativeTile(curTile, 2, dir);
		                String dirString;
		                if (dir == Direction.NORTH) {
		                    dirString = "north";
		                } else if (dir == Direction.EAST) {
		                    dirString = "east";
		                } else if (dir == Direction.SOUTH) {
		                    dirString = "south";
		                } else {
		                    dirString = "west";
		                }
		                if (curMove != null) {
		                    debug(String.format("    Consider move %s to %s", dirString,
		                            curMove.toString()));
		                    possibleMoves.add(new MazeGenStep(curMove,
		                            getRelativeTile(curTile, dir)));
		                }
		            }
		            // TODO see if you can attach multiple values to a part of the enum
		            
		            Collections.shuffle(possibleMoves, rand);
		            for (MazeGenStep nextMove : possibleMoves) {
		                branch.push(nextMove);
		            }
		            if (curNumSteps == branchAtStep) {
		            	Stack<MazeGenStep> newBranch = new Stack<MazeGenStep>();
		            	newBranch.push(possibleMoves.get(possibleMoves.size() - 1));
		            	branches.add(newBranch);
		            }
		        }
	        }
        	Iterator<Stack<MazeGenStep>> iter = branches.iterator();
	        while (iter.hasNext()) {
	        	if (iter.next().isEmpty()) {
	        		iter.remove();
	        	}
	        }
	        if (curNumSteps == branchAtStep) {
	        	curNumSteps = 0;
	        	branchAtStep += firstBranchStep;
	        }
        }
        tiles[mazeWidth-2][mazeHeight-1].setValue(Tile.SPACE);
    }
    
    public void genMazePrim() {
    	genMazePrim(0);
    }
    public void genMazePrim(int delay) {
        reset();
        
        int startx = mazeWidth / 2;
        int starty = mazeHeight / 2;
        if (startx % 2 == 0) {
            startx++;
        }
        if (starty % 2 == 0) {
            starty++;
        }
        List<MazeGenStep> possibleMoves = new ArrayList<MazeGenStep>();
        possibleMoves.add(new MazeGenStep(tiles[startx][starty], tiles[1][0]));
        
        while (!possibleMoves.isEmpty()) {
            int index = rand.nextInt(possibleMoves.size());
            MazeGenStep curStep = possibleMoves.get(index);
            possibleMoves.remove(index);
            
            if (!curStep.isValidMove()) {
                continue;
            }
            curStep.setValues(Tile.SPACE);
            
            if (delay > 0) {
	            try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            
            Tile curTile = curStep.getNewTile();
            Tile curMove;
            for (Direction dir : Direction.values()) {
                curMove = getRelativeTile(curTile, 2, dir);
                String dirString;
                if (dir == Direction.NORTH) {
                    dirString = "north";
                } else if (dir == Direction.EAST) {
                    dirString = "east";
                } else if (dir == Direction.SOUTH) {
                    dirString = "south";
                } else {
                    dirString = "west";
                }
                if (curMove != null) {
                    debug(String.format("    Consider move %s to %s", dirString,
                            curMove.toString()));
                    possibleMoves.add(new MazeGenStep(curMove,
                            getRelativeTile(curTile, dir)));
                }
            }
        }
        
        tiles[mazeWidth-2][mazeHeight-1].setValue(Tile.SPACE);
    }
    
    /*
     * Some private utility functions
     */
    private void reset() {
        for (int row = 0; row < mazeHeight; row++) {
            for (int col = 0; col < mazeWidth; col++) {
                tiles[col][row].setValue(Tile.WALL);
            }
        }
    }
    
    private void debug(String message) {
        if (Maze.DEBUGGING) {
            System.out.println("> " + message);
        }
    }
    
    private Tile getRelativeTile(Tile tile, int n, Direction direction) {
        if (tile == null) {
            return null;
        }
        
        int newX = tile.getX() + n * direction.dx();
        int newY = tile.getY() + n * direction.dy();
        
        if (newX < 0 || newX >= mazeWidth || newY < 0 || newY >= mazeHeight) {
            return null;
        } else {
            return tiles[newX][newY];
        }
    }
    
    private Tile getRelativeTile(Tile tile, Direction direction) {
        return getRelativeTile(tile, 1, direction);
    }
    
}
