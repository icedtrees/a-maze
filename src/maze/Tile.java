package maze;

import game.Game;

import java.awt.Color;
import java.awt.Graphics;

import maze.Maze.Direction;

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
    
    public void setHint() {
    	this.hint = new Color(0, 255, 0);
    }
    
    public void setValue(int newValue) {
        value = newValue;
    }
    
    public void draw(Graphics g, int tileSize) {	    
	    if (isShifting()) {
	    	// Draw background
	    	g.setColor(Color.WHITE);
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
		    	if (trail != null) {
		    		g.setColor(trail);
		    	} else {
		    		g.setColor(Color.WHITE);
		    	}
		    }
		    
		    g.fillRect(0, 0, tileSize, tileSize);
	    }
	    
	    if (hint != null) {
	    	g.setColor(hint);
	    	g.fillRect(0,  0, tileSize, tileSize);
	    }
	    if (contents != null) {
    		contents.draw(g, tileSize);
    	}
    }
    
    public void nextFrame() {
    	if (hint != null) {
    		hint = new Color(hint.getRed(), hint.getGreen(), hint.getBlue(), hint.getAlpha() - 5);
    		if (hint.getAlpha() == 0) {
    			hint = null;
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
    }
    
    public boolean shiftWall(Direction dir) {
    	if (isShifting()) {
    		return false;
    	}
    	shifting = dir;
    	return true;
    }
    
    public void interact(Player player) {
    	if (contents != null) {
    		if (contents.interact(player)) {
    			contents = null;
    		}
    	}
    	if (player.leavesTrail()) {
    		this.trail = player.getColor();
    	}
    }
    
    public String toString() {
        return "(" + x + ", " + y + ") with value " + value;
    }
}
