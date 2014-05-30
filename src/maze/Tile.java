package maze;

import game.Game;

import java.awt.Color;
import java.awt.Graphics;

import maze.Direction;

/**
 * This class implements a square section of the maze (the smallest subdivison).
 * It contains a <code>TileObject</code> which is used to render the tile onto
 * the JFrame containing the maze.
 */
public class Tile {
    public static final int WALL = 1;
    public static final int SPACE = 0;
    
    private Color trail;
    private Color hint;
    
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
        trail = null;
    }
    
    /**
     * 
     * @return Value of the tile - WALL or SPACE
     */
    public int getValue() {
    	if (isShifting()) {
    		return WALL;
    	}
        return value;
    }
    /**
     * 
     * @return x coordinate of tile
     */
    public int getX() {
        return x;
    }
    /**
     * 
     * @return y coordinate of tile
     */
    public int getY() {
        return y;
    }
    /**
     * 
     * @return Whether or not tile is currently shifting
     */
    public boolean isShifting() {
    	return shifting != null;
    }
    
    /**
     * 
     * @return Whether or not tile has a TileObject in it
     */
    public boolean hasContents() {
    	return contents != null;
    }
    /**
     * Places a TileObject within this tile
     * @param obj Object to place in tile
     */
    public void setContents(TileObject obj) {
    	this.contents = obj;
    }
    
    /**
     * Set the tile to show as a hint tile
     */
    public void setHint() {
    	this.hint = new Color(0, 255, 0);
    }
    
    /**
     * Set the tile to a new value (WALL or SPACE)
     * @param newValue Value to set the tile to - WALL or SPACE
     */
    public void setValue(int newValue) {
        value = newValue;
    }
    
    /**
     * Draw the tile and everything inside it
     * @param g Graphics context
     * @param tileSize Tile size in pixels
     */
    public void draw(Graphics g, int tileSize) {	    
	    if (isShifting()) {
	    	// Draw background
	    	g.setColor(Color.WHITE);
	    	g.fillRect(0, 0, tileSize, tileSize);
	    	if (trail != null) {
	    		g.setColor(trail);
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
		    g.fillRect(0, 0, tileSize, tileSize);
		    
		    if (trail != null) {
	    		g.setColor(trail);
	    		g.fillRect(0, 0, tileSize, tileSize);
	    	}
	    }
	    
	    if (hint != null) {
	    	g.setColor(hint);
	    	g.fillRect(0,  0, tileSize, tileSize);
	    }
	    if (contents != null) {
    		contents.draw(g, tileSize);
    	}
    }
    
    /**
     * Advance the tile to the next frame and everything inside it.
     */
    public void nextFrame() {
    	if (hint != null) {
    		hint = new Color(hint.getRed(), hint.getGreen(), hint.getBlue(), hint.getAlpha() - 5);
    		if (hint.getAlpha() == 0) {
    			hint = null;
    		}
    	}
    	if (trail != null) {
    		if (trail.getAlpha() != 255) {
    			trail = new Color(trail.getRed(),
    					trail.getGreen(),
    					trail.getBlue(),
    					trail.getAlpha() + 5);
    		}
    	}
    	if (isShifting()) {
	    	shifted += SHIFT_SPEED / Game.settings.FPS;
	    	if (shifted >= 1) {
	    		shifting = null;
	    		shifted = 0;
	    		if (value == WALL) {
	    			value = SPACE;
	    		} else {
	    			value = WALL;
	    		}
	    	}
    	}
    	
    	if (contents != null) {
    		contents.nextFrame();
    	}
    }
    
    /**
     * Shift this tile and change it from a WALL to a SPACE or vice versa
     * @param dir Direction to shift in
     * @return Whether or not the shift was successful (can't shift if already shifting)
     */
    public boolean shiftWall(Direction dir) {
    	if (isShifting()) {
    		return false;
    	}
    	shifting = dir;
    	return true;
    }
    
    /**
     * Interact with the given player. Leaves a trail if that setting is true
     * and/or allows player to interact with the TileObject within this tile
     * @param player
     */
    public void interact(Player player) {
    	if (contents != null) {
    		if (contents.interact(player)) {
    			contents = null;
    		}
    	}
    	if (trail == null && player.leavesTrail()) {
    		trail = new Color(player.getColor().getRed(),
    				player.getColor().getGreen(),
    				player.getColor().getBlue(),
    				0);
    	}
    }
    
    public String toString() {
        return "(" + x + ", " + y + ") with value " + value;
    }
}
