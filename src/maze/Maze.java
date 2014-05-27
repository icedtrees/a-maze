package maze;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
    
    private Tile[][] tiles;
    private int mazeWidth;
    private int mazeHeight;
    
    private int straightness;
    private int branching;
    
    private boolean fogOfWar;
    
    private boolean shiftingWalls;
    private int wallsToShift;
    private int stepsTaken;
    private int stepsToTake;
    
    private Player player1;
    private Player player2;
    private Coord player1Last;
    private Coord player2Last;
    
    public enum Direction {
    	NORTH(0, -1),
    	EAST(1, 0),
    	SOUTH(0, 1),
    	WEST(-1, 0);
        
        private final int dx;
        private final int dy;
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
    
    public Maze(int newHeight, int displayHeight,
    		int straightness, int branching, int numPlayers) {
        this(newHeight, displayHeight, straightness, branching,
        		numPlayers, System.nanoTime());
    }
    public Maze(int newHeight, int displayHeight,
    		int straightness, int branching, int numPlayers, long seed) {
    	this((int) (newHeight * DEFAULT_RATIO), newHeight,
                (int) (displayHeight * DEFAULT_RATIO), displayHeight,
                straightness, branching, numPlayers, seed);
    }
    public Maze(int newWidth, int newHeight,
            int displayWidth, int displayHeight,
            int straightness, int branching, int numPlayers, long seed) {
        mazeWidth = 2 * newWidth + 1;
        mazeHeight = 2 * newHeight + 1;
        setPreferredSize(new Dimension(displayWidth, displayHeight));
        tiles = new Tile[mazeWidth][mazeHeight];
        for (int row = 0; row < mazeHeight; row++) {
            for (int col = 0; col < mazeWidth; col++) {
                tiles[col][row] = new Tile(Tile.WALL, col, row);
            }
        }
        
        player1 = null;
    	player2 = null;
    	if (numPlayers >= 1) {
    		player1 = new Player(1, 0, Color.RED);
    		player1Last = new Coord(1, 0);
    	}
    	if (numPlayers >= 2) {
    		player2 = new Player(mazeWidth - 2, mazeHeight - 1, Color.BLUE);
    		player2Last = new Coord(mazeWidth - 2, mazeHeight - 1);
    	}
        
        this.straightness = straightness;
        this.branching = branching;
        
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
    public boolean hasTileObject(int x, int y) {
    	if (x < 1 || x > mazeWidth - 2 || y < 1 || y > mazeHeight - 2) {
    		return true;
    	}
    	return tiles[x][y].hasContents();
    }
    public void setTileObject(int x, int y, TileObject obj) {
    	if (x < 1 || x > mazeWidth - 2 || y < 1 || y > mazeHeight - 2) {
    		return;
    	}
    	tiles[x][y].setContents(obj);
    }
    public void setFogOfWar(boolean on, int vision) {
    	fogOfWar = on;
    	if (player1 != null) {
    		player1.setVision(vision);
    	}
    	if (player2 != null) {
    		player2.setVision(vision);
    	}
    }
    public void setShiftingWalls(boolean on, int numWalls, int stepsToTake) {
    	shiftingWalls = on;
    	wallsToShift = numWalls;
    	this.stepsToTake = stepsToTake;
    	stepsTaken = 0;
    }
    public void applyMods(List<Modification> mods) {
    	for (Modification mod : mods) {
    		mod.apply(this, rand);
    	}
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
    	
    	// Set clipping if there is fog of war
    	if (fogOfWar) {
	    	Area fogClip = new Area();
	    	if (player1 != null) {
	    		int vision = player1.getVision();
	    		int x = (int) (((player1.getCurX() - vision) * tileSize) + xMargin);
	    		int y = (int) (((player1.getCurY() - vision) * tileSize) + yMargin);
	    		
	    		fogClip.add(new Area(new Rectangle(x, y, tileSize*vision*2, tileSize*vision*2)));
	    	}
	    	if (player2 != null) {
	    		int vision = player2.getVision();
	    		int x = (int) (((player2.getCurX() - vision) * tileSize) + xMargin);
	    		int y = (int) (((player2.getCurY() - vision) * tileSize) + yMargin);
	    		
	    		fogClip.add(new Area(new Rectangle(x, y, tileSize*vision*2, tileSize*vision*2)));
	    	}
	    	g.setClip(fogClip);
    	}
    	
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
        if (player1 != null) {
	        Graphics newG = g.create(
	        		(int) (player1.getCurX() * tileSize + xMargin),
	        		(int) (player1.getCurY() * tileSize + yMargin),
	        		tileSize, tileSize);
	        player1.draw(newG, tileSize);
        }
        if (player2 != null) {
	        Graphics newG = g.create(
	        		(int) (player2.getCurX() * tileSize + xMargin),
	        		(int) (player2.getCurY() * tileSize + yMargin),
	        		tileSize, tileSize);
	        player2.draw(newG, tileSize);
        }
    }
    
    public void shiftTile(int x, int y) {
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
			shiftTile(newWall.getX(), newWall.getY());
			shiftTile(wallToRemove.getX(), wallToRemove.getY());
    	}
    	
    }
    
    public boolean movePlayer(int playerNum, Direction dir) {
    	Player player = null;
    	if (playerNum == 1) {
    		player = player1;
    	} else if (playerNum == 2) {
    		player = player2;
    	}
    	
    	if (player == null || dir == null) {
    		return false;
    	}
    	if (isSpace(player.getGoalX() + dir.dx(), player.getGoalY() + dir.dy())) {
    		return player.move(dir);
    	} else {
    		return false;
    	}
    }
    public void movePlayerWait(int playerNum, Direction dir) {
    	Player player = null;
    	if (playerNum == 1) {
    		player = player1;
    	} else if (playerNum == 2) {
    		player = player2;
    	}
    	
    	if (dir == null) {
    		return;
    	}
    	if (isSpace(player.getGoalX() + dir.dx(), player.getGoalY() + dir.dy())) {
    		player.moveWait(dir);
    	}
    }
    
    public void nextFrame() {
    	for (int row = 0; row < mazeHeight; row++) {
    		for (int col = 0; col < mazeWidth; col++) {
    			tiles[col][row].nextFrame();
    		}
    	}
    	if (player1 != null) {
    		player1.nextFrame();
    	}
    	if (player2 != null) {
    		player2.nextFrame();
    	}
    	
    	// TODO tile interaction
    	if (player1.getRealX() != player1Last.getX() || player1.getRealY() != player1Last.getY()) {
    		// Player has moved since we last saw
    		tiles[player1.getRealX()][player1.getRealY()].interact(player1);
    		player1Last = new Coord(player1.getRealX(), player1.getRealY());
    		
			stepsTaken++;
			if (stepsTaken == stepsToTake) {
				shiftTiles(wallsToShift);
				stepsTaken = 0;
			}
    	}
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
    
    /*
     * Maze Generation algorithm
     */    
    public void genMazeDFSBranch() {
    	genMazeDFSBranch(0);
    }
    public void genMazeDFSBranch(int delay) {
    	reset();
    	
    	List<MazeBranch> branches = new ArrayList<MazeBranch>();
    	MazeBranch branchFromStart = new MazeBranch(new Coord(1, 1), Direction.SOUTH, 0);
    	MazeBranch branchFromEnd = new MazeBranch(new Coord(mazeWidth-2, mazeHeight-2), Direction.NORTH, 1);
    	branches.add(branchFromStart);
    	branches.add(branchFromEnd);
    	
    	HashMap<Coord,Integer> cellID = new HashMap<Coord,Integer>();
    	HashMap<Coord,Integer> cellDist = new HashMap<Coord,Integer>();
    	
    	int stepsTaken = 0;
    	int branchAtStep = branching;
    	while (!branches.isEmpty()) {
    		stepsTaken++;
    		
    		int numBranches = branches.size();
    		for (int i = 0; i < numBranches; i++) {
    			MazeBranch branch = branches.get(i);
    			if (!branch.isEmpty()) {
    				Step curStep = null;
    				Coord curCell = null;
    				boolean validMoveFound = false;
    				while (!branch.isEmpty()) {
	    				curStep= branch.pop();
	    				curCell = curStep.getCell();
	    				
	    				if (getTileValue(curCell) == Tile.WALL) {
	    					// We found a valid move
	    					validMoveFound = true;
	    					break;
	    				}
    				}
    				if (!validMoveFound) {
    					// Stack was full of invalid moves
    					continue;
    				}
    				
    				cellID.put(curCell, branch.getID());
    				cellDist.put(curCell, curStep.getDist());
    				setTileValue(curCell, Tile.SPACE);
    				setTileValue(curCell.inDirection(curStep.getDir().reverse()), Tile.SPACE);
    				
    				if (delay > 0) {
			            try {
							Thread.sleep(delay);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
		            }
    				
    				List<Step> possibleMoves = new ArrayList<Step>();
    				for (Direction dir : Direction.values()) {
    					Coord newCell = curCell.inDirection(dir, 2);
    					if (!validCoord(newCell)) {
    						continue;
    					}
    					int weighting = 50;
    					if (dir == curStep.getDir()) {
    						weighting += straightness;
    					}
    					Step newStep = new Step(newCell, dir, curStep.getDist() + 1, weighting);
    					possibleMoves.add(newStep);
    				}
    				possibleMoves = weightedShuffle(possibleMoves, rand);
    				
    				for (Step step : possibleMoves) {
    					branch.push(step);
    				}
    				if (stepsTaken == branchAtStep) {
    					branch.setBranchDue(true);
    				}
    				if (branch.branchDue()) {
    					if (possibleMoves.size() > 1) {
    						MazeBranch newBranch = new MazeBranch(possibleMoves.get(0), branch.getID());
    						branches.add(newBranch);
    						branch.setBranchDue(false);
    					} else {
    						System.out.println("Failed to branch");
    					}
    				}
    			}
    		}
    		
    		// Prune empty branches
    		Iterator<MazeBranch> iter = branches.iterator();
	        while (iter.hasNext()) {
	        	if (iter.next().isEmpty()) {
	        		iter.remove();
	        	}
	        }
	        if (stepsTaken == branchAtStep) {
	        	stepsTaken = 0;
	        	branchAtStep += branching;
	        }
    	}
    	
    	/*
    	 * Now that the two sides have been completed, pick the wall
    	 * which creates the longest path to break and join the two halves
    	 */
    	Coord wallToRemove = null;
    	int largestDist = 0;
    	int rowMagicNumber = 1;
    	for (int col = 1; col < mazeWidth - 1; col ++) {
    		rowMagicNumber = 3 - rowMagicNumber;
    		for (int row = rowMagicNumber; row < mazeHeight - rowMagicNumber; row += 2) {
    			if (tiles[col][row].getValue() == Tile.WALL) {
    				Coord curWall = new Coord(col, row);
    				if (rowMagicNumber == 2) {
    					// We are looking at horizontal walls
    					int northID = cellID.get(curWall.inDirection(Direction.NORTH));
    					int southID = cellID.get(curWall.inDirection(Direction.SOUTH));
    					if (northID != southID) {
    						int northDist = cellDist.get(curWall.inDirection(Direction.NORTH));
    						int southDist = cellDist.get(curWall.inDirection(Direction.SOUTH));
    						if (northDist + southDist > largestDist) {
    							largestDist = northDist + southDist;
    							wallToRemove = curWall;
    						}
    					}
    				} else if (rowMagicNumber == 1) {
    					// We are looking at vertical walls
    					int eastID = cellID.get(curWall.inDirection(Direction.EAST));
    					int westID = cellID.get(curWall.inDirection(Direction.WEST));
    					if (eastID != westID) {
    						int eastDist = cellDist.get(curWall.inDirection(Direction.EAST));
    						int westDist = cellDist.get(curWall.inDirection(Direction.WEST));
    						if (eastDist + westDist > largestDist) {
    							largestDist = eastDist + westDist;
    							wallToRemove = curWall;
    						}
    					}
    				}
    			}
    		}
    	}
    	
    	if (delay > 0) {
            try {
				Thread.sleep(1000);
				shiftTile(wallToRemove.getX(), wallToRemove.getY());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        } else {
        	setTileValue(wallToRemove, Tile.SPACE);
        }
    	
    	debug("Path of length " + largestDist + " in a total of " + (mazeWidth/2)*(mazeHeight/2) + " cells");
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
    private int getTileValue(Coord coord) {
    	return tiles[coord.getX()][coord.getY()].getValue();
    }
    private void setTileValue(Coord coord, int value) {
    	tiles[coord.getX()][coord.getY()].setValue(value);
    }
    public void setTile(int x, int y, int value) {
    	tiles[x][y].setValue(value);
    }
    private boolean validCoord(Coord coord) {
    	int x = coord.getX();
    	int y = coord.getY();
    	
    	return x > 0 && x < mazeWidth-1 && y > 0 && y < mazeHeight-1;
    }
    private List<Step> weightedShuffle(List<Step> moves, Random rand) {
    	List<Step> shuffledMoves = new ArrayList<Step>();
    	int weightSum = 0;
    	for (Step move : moves) {
    		weightSum += move.getWeighting();
    	}
    	
    	while (!moves.isEmpty()) {
    		int randomNum = rand.nextInt(weightSum);
	    	for (Step move : moves) {
	    		randomNum -= move.getWeighting();
	    		if (randomNum < 0) {
	    			moves.remove(move);
	    			shuffledMoves.add(0, move);
	    			weightSum -= move.getWeighting();
	    			break;
	    		}
	    	}
    	}
    	
    	return shuffledMoves;
    }
    
    private void debug(String message) {
        if (Maze.DEBUGGING) {
            System.out.println("> " + message);
        }
    }
}
