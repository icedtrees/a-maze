package maze;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import java.util.List;

import javax.swing.JComponent;

import maze.modification.Modification;


public class Maze extends JComponent {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private final static boolean DEBUGGING = false;
    public final static double DEFAULT_RATIO = 1.2;    
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
    private boolean multiplayer;
    
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
    
    public Maze(MazeSettings settings) {
        mazeHeight = 2 * settings.getMazeSize() + 1;
        mazeWidth = (int) (2 * settings.getMazeSize() * DEFAULT_RATIO + 1);
//        setPreferredSize(new Dimension(displayWidth, displayHeight));
        tiles = new Tile[mazeWidth][mazeHeight];
        for (int row = 0; row < mazeHeight; row++) {
            for (int col = 0; col < mazeWidth; col++) {
                tiles[col][row] = new Tile(Tile.WALL, col, row);
            }
        }
        
        this.straightness = straightness > 0 ? straightness*straightness : straightness;
        this.branching = (int) (1.5 * (11 - branching));
        this.rand = new Random(settings.getSeed());
        this.genMazeDFSBranch();
        
		player1 = new Player(1, 0, Color.RED, settings.getTrail(), settings.getStartingTime());
		player1Last = new Coord(1, 0);
		
		player2 = null;
		this.multiplayer = settings.getMultiplayer();
    	if (this.multiplayer) {
    		player2 = new Player(mazeWidth - 2, mazeHeight - 1, Color.BLUE, settings.getTrail(), settings.getStartingTime());
    		player2Last = new Coord(mazeWidth - 2, mazeHeight - 1);
    		
    		player1.setFriend(player2);
    		player2.setFriend(player1);
    	}
    	if (player1 != null) {
    		tiles[mazeWidth-2][mazeHeight-1].setContents(new GoalFlag(player1));
    		tiles[player1.getRealX()][player1.getRealY()].interact(player1);
    	}
    	if (player2 != null) {
    		tiles[1][0].setContents(new GoalFlag(player2));
    		tiles[player2.getRealX()][player2.getRealY()].interact(player2);
    	}
        this.applyMods(settings.getModifications());
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
    public List<Coord> getSpaces() {
    	List<Coord> spaces = new ArrayList<Coord>();
		for (int row = 1; row < mazeHeight - 1; row++) {
			for (int col = 1; col < mazeWidth - 1; col++) {
				if (isSpace(col, row) && !hasTileObject(col, row)) {
					spaces.add(new Coord(col, row));
				}
			}
		}
		
		return spaces;
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
    public boolean isMultiplayer() {
    	return multiplayer;
    }
    public double getPlayer1Timer() {
    	if (player1 != null) {
    		return player1.getTimer();
    	}
    	return 0;
    }
    public double getPlayer2Timer() {
    	if (player2 != null) {
    		return player2.getTimer();
    	}
    	return 0;
    }
    public void setPlayer1Timer(double timer) {
    	if (player1 != null) {
    		player1.setTimer(timer);
    	}
    }
    public void setPlayer2Timer(double timer) {
    	if (player2 != null) {
    		player2.setTimer(timer);
    	}
    }
    public void setPlayer1TimerRelative(double timer) {
    	if (player1 != null) {
    		player1.setTimerRelative(timer);
    	}
    }
    public void setPlayer2TimerRelative(double timer) {
    	if (player2 != null) {
    		player2.setTimerRelative(timer);
    	}
    }
    public boolean player1Finished() {
    	return player1 != null && player1.isFinished();
    }
    public boolean player2Finished() {
    	return player2 != null && player2.isFinished();
    }
    
    private void applyMods(List<Modification> mods) {
    	if (mods == null) {
    		return;
    	}
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
    
    private void drawPath(Graphics g, Area a, Color color) {
    	g.setColor(color);
    	PathIterator path = a.getPathIterator(null);
    	double[] coords = new double[6];
		double prevX = 0;
    	double prevY = 0;
    	double prevMOVETOX = 0;
    	double prevMOVETOY = 0;
    	while (!path.isDone()) {
    		int type = path.currentSegment(coords);
    		if (type == PathIterator.SEG_LINETO) {
    			int startX = (int) prevX;
    			int startY = (int) prevY;
    			int endX = (int) coords[0];
    			int endY = (int) coords[1];
    			
    			if (coords[0] > prevX) {
    				startX++;
    			} else if (coords[0] < prevX) {
    				startX--;
    			}
    			if (coords[1] > prevY) {
    				startY++;
    			} else if (coords[1] < prevY) {
    				startY--;
    			}
    			
    			g.drawLine(startX, startY, endX, endY);
//    			System.out.println("Draw line from " + startX + ", " + startY + " to " + endX + ", " + endY);
    			prevX = coords[0];
        		prevY = coords[1];
    		} else if (type == PathIterator.SEG_CLOSE) {
    			int startX = (int) prevX;
    			int startY = (int) prevY;
    			int endX = (int) prevMOVETOX;
    			int endY = (int) prevMOVETOY;
    			
    			if (prevMOVETOX > prevX) {
    				startX++;
    			} else if (prevMOVETOX < prevX) {
    				startX--;
    			}
    			if (prevMOVETOY > prevY) {
    				startY++;
    			} else if (prevMOVETOY < prevY) {
    				startY--;
    			}
    			
    			g.drawLine(startX, startY, endX, endY);
//    			System.out.println("Draw line from " + startX + ", " + startY + " to " + endX + ", " + endY);
    			prevX = prevMOVETOX;
        		prevY = prevMOVETOY;
    		} else if (type == PathIterator.SEG_MOVETO) {
    			prevMOVETOX = coords[0];
    			prevMOVETOY = coords[1];
    			prevX = coords[0];
        		prevY = coords[1];
    		}
    		path.next();
    	}
//    	System.out.println();
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
        g.setColor(Color.DARK_GRAY);
    	g.fillRect(0, 0, bounds.width, bounds.height);
        
    	/*
    	 * Draw each tile
    	 */
    	
    	// Set clipping if there is fog of war
    	Rectangle p1Fog = null;
    	Rectangle p2Fog = null;
    	if (fogOfWar) {
    		Area fogClip = new Area();
    		g.setColor(Color.GRAY);
    		g.fillRect(xMargin, yMargin, mazeWidth * tileSize, mazeHeight * tileSize);
	    	if (player1 != null) {
	    		int vision = player1.getVision();
	    		int x = (int) (((player1.getCurX() - vision) * tileSize) + xMargin);
	    		int y = (int) (((player1.getCurY() - vision) * tileSize) + yMargin);
	    		
	    		p1Fog = new Rectangle(x, y, tileSize*vision*2, tileSize*vision*2);
	    		fogClip.add(new Area(p1Fog));
	    	}
	    	if (player2 != null) {
	    		int vision = player2.getVision();
	    		int x = (int) (((player2.getCurX() - vision) * tileSize) + xMargin);
	    		int y = (int) (((player2.getCurY() - vision) * tileSize) + yMargin);
	    		
	    		p2Fog = new Rectangle(x, y, tileSize*vision*2, tileSize*vision*2);
	    		fogClip.add(new Area(p2Fog));
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
        // Fog shadows
        if (fogOfWar) {
        	g.setClip(xMargin, yMargin, mazeWidth*tileSize, mazeHeight*tileSize);
	        Color curColor = Color.GRAY;
	        if (player2 != null && !p1Fog.intersects(p2Fog)) {
    			g.drawRect(p1Fog.x, p1Fog.y, p1Fog.width, p1Fog.height);
    			g.drawRect(p2Fog.x-1, p2Fog.y-1, p2Fog.width+2, p2Fog.height+2);
    		}
	        Area fogClip = new Area(p1Fog);
        	if (player2 != null) {
        		fogClip.add(new Area(p2Fog));
        	}
	        for (int i = 0; i < 51; i++) {
	        	fogClip = new Area(p1Fog);
	        	if (player2 != null) {
	        		fogClip.add(new Area(p2Fog));
	        	}
	        	
	        	drawPath(g, fogClip, curColor);
	        	curColor = new Color(curColor.getRed(), curColor.getGreen(), curColor.getBlue(), curColor.getAlpha() - 5);
	        	
	        	if (player1 != null) {
	        		p1Fog = new Rectangle(p1Fog.x+1, p1Fog.y+1, p1Fog.width-2, p1Fog.height-2);
	        	}
	        	if (player2 != null) {
	        		p2Fog = new Rectangle(p2Fog.x+1, p2Fog.y+1, p2Fog.width-2, p2Fog.height-2);
	        	}
	        }
        }
        
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
    	int x = player.getGoalX() + dir.dx();
    	int y = player.getGoalY() + dir.dy();
    	int friendX = -1;
    	int friendY = -1;
    	if (player.hasFriend()) {
	    	friendX = player.getFriend().getGoalX();
	    	friendY = player.getFriend().getGoalY();
    	}
    	if (isSpace(x, y) && !(x == friendX && y == friendY)) {
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
    	int x = player.getGoalX() + dir.dx();
    	int y = player.getGoalY() + dir.dy();
    	int friendX = player.getFriend().getGoalX();
    	int friendY = player.getFriend().getGoalY();
    	if (isSpace(x, y) && !(x == friendX && y == friendY)) {
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
    	
    	if (player1 != null) {
	    	if (player1.getRealX() != player1Last.getX() || player1.getRealY() != player1Last.getY()) {
	    		// Player has moved since we last saw
	    		tiles[player1.getRealX()][player1.getRealY()].interact(player1);
	    		player1Last = new Coord(player1.getRealX(), player1.getRealY());
	    		
	    		if (shiftingWalls) {
					stepsTaken++;
					if (stepsTaken == stepsToTake) {
						shiftTiles(wallsToShift);
						stepsTaken = 0;
					}
	    		}
	    	}
    	}
    	if (player2 != null) {
	    	if (player2.getRealX() != player2Last.getX() || player2.getRealY() != player2Last.getY()) {
	    		// Player has moved since we last saw
	    		tiles[player2.getRealX()][player2.getRealY()].interact(player2);
	    		player2Last = new Coord(player2.getRealX(), player2.getRealY());
	    		
	    		if (shiftingWalls) {
					stepsTaken++;
					if (stepsTaken == stepsToTake) {
						shiftTiles(wallsToShift);
						stepsTaken = 0;
					}
	    		}
	    	}
    	}
    }
    
    public boolean isSpace(int x, int y) {
    	if (x == 1 && y == 0) {
    		// Start
    		return true;
    	}
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
    					int weighting = 11;
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
