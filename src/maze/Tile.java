package maze;

import game.Game;

import java.awt.Color;
import java.awt.Graphics;

import maze.Maze.Direction;

public class Tile {
    public static final int WALL = 1;
    public static final int SPACE = 0;
    
    // TEST
    public static final int EXPLORED = 2;
    // TEST
    
    private static final double SHIFT_SPEED = 1; // Shifts per second
    
    private int value;
    private int x;
    private int y;
    private TileObject contents;
    
    private Direction shifting;
    private double shifted;
    
    public Tile(int newValue, int newX, int newY) {
        value = newValue;
        x = newX;
        y = newY;
        contents = null;
        
        shifting = null;
        shifted = 0;
    }
    
    public int getValue() {
    	if (isShifting()) {
    		return WALL;
    	}
        return value;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public boolean isShifting() {
    	return shifting != null;
    }
    
    public boolean hasContents() {
    	return contents != null;
    }
    public void setContents(TileObject obj) {
    	this.contents = obj;
    }
    
    public void setValue(int newValue) {
        value = newValue;
    }
    
    public void draw(Graphics g, int tileSize) {	    
	    if (isShifting()) {
	    	// Draw background
	    	if (value == WALL) {
	    		g.setColor(Color.WHITE);
	    	}
	    	if (value == SPACE) {
	    		g.setColor(Color.WHITE);
	    	}
	    	if (value == EXPLORED) {
	    		g.setColor(Color.CYAN);
	    	}
	    	g.fillRect(0, 0, tileSize, tileSize);
	    	
	    	// Draw shifting wall
	    	double shiftedAmount;
	    	if (value == WALL) {
	    		shiftedAmount = shifted;
	    	} else {
	    		shiftedAmount = 1 - shifted;
	    	}
	    	g.setColor(Color.BLACK);
	    	g.fillRect((int) (shiftedAmount * tileSize * shifting.dx()),
	    			(int) (shiftedAmount * tileSize * shifting.dy()),
	    			tileSize, tileSize);
	    } else {
	    	if (value == WALL) {
	    	    g.setColor(Color.BLACK);
		    }
		    if (value == SPACE) {
		        g.setColor(Color.WHITE);
		    }
		    
		    // TEST
		    if (value == EXPLORED) {
		    	g.setColor(Color.CYAN);
		    }
		    // TEST
		    
		    g.fillRect(0, 0, tileSize, tileSize);
	    }
	    if (contents != null) {
    		contents.draw(g, tileSize);
    	}
    }
    
    public void nextFrame() {
    	if (!isShifting()) {
    		return;
    	}
    	shifted += SHIFT_SPEED / Game.settings.FPS;
    	if (shifted >= 1) {
    		shifting = null;
    		shifted = 0;
    		if (value == WALL) {
    			value = SPACE;
    		} else {
    			value = WALL;
    		}
    		synchronized(this) {
    			notifyAll();
    		}
    	}
    }
    
    public synchronized void shiftWall(Direction dir) {
    	while (isShifting()) {
    		synchronized(this) {
    			try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    	}
    	shifting = dir;
    }
    
    public void interact(Player player) {
    	if (contents != null) {
    		if (contents.interact(player)) {
    			contents = null;
    		}
    	}
    	if (Game.settings.leaveTrail) {
    		this.value = EXPLORED;
    	}
    }
    
    public String toString() {
        return "(" + x + ", " + y + ") with value " + value;
    }
}
