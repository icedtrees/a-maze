package maze;

import java.awt.Color;
import java.awt.Graphics;

public class Tile {
    static final public int WALL = 1;
    static final public int SPACE = 0;
    
    // TEST
    static final public int EXPLORED = 2;
    // TEST
    
    private int value;
    private int x;
    private int y;
    
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
    
    public void setValue(int newValue) {
        value = newValue;
    }
    
    public void draw(Graphics g, int tileSize) {
        if (value == WALL) {
    	    g.setColor(new Color(0, 0, 0));
	    }
	    if (value == SPACE) {
	        g.setColor(new Color(255, 255, 255));
	    }
	    
	    // TEST
	    if (value == EXPLORED) {
	    	g.setColor(new Color(200, 200, 255));
	    }
	    // TEST
	    
    	g.fillRect(0, 0, tileSize, tileSize);
    }
    
    public void nextFrame() {
    	
    }
    
    public String toString() {
        return "(" + x + ", " + y + ") with value " + value;
    }
}
