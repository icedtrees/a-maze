package maze;

import java.awt.Color;
import java.awt.Graphics;

import maze.Maze.Direction;

public class Tile {
    static final public int WALL = 1;
    static final public int SPACE = 0;
    
    // TEST
    static final public int EXPLORED = 2;
    // TEST
    
    private int value;
    private int x;
    private int y;
    private TileObject contents;
    
    public Tile(int newValue, int newX, int newY) {
        value = newValue;
        x = newX;
        y = newY;
    }
    
    public int getValue() {
        return value;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    
    public void setContents(TileObject obj) {
    	this.contents = obj;
    }
    
    public void setValue(int newValue) {
        value = newValue;
    }
    
    public void draw(Graphics g, int tileSize) {
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
    	if (contents != null) {
    		contents.draw(g, tileSize);
    	}
    }
    
    public void nextFrame() {
    	
    }
    
    public void shiftWall(Direction dir) {
    	
    }
    
    public void interact(Player player) {
    	if (contents != null) {
    		contents.interact(player);
    		contents = null;
    	}
    }
    
    public String toString() {
        return "(" + x + ", " + y + ") with value " + value;
    }
}
