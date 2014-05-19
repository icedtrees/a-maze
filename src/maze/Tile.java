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
    private Tile north;
    private Tile east;
    private Tile south;
    private Tile west;
    
    public Tile(int newValue, int newX, int newY) {
        value = newValue;
        x = newX;
        y = newY;
        
        north = null;
        east = null;
        south = null;
        west = null;
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
    public Tile getNorth() {
        return this.north;
    }
    public Tile getEast() {
        return this.east;
    }
    public Tile getSouth() {
        return this.south;
    }
    public Tile getWest() {
        return this.west;
    }
    public Tile getNorth(int n) {
        Tile t = this.north;
        if (t == null) {
            return t;
        }
        
        int i = 1;
        while (t.north != null && i < n) {
            t = t.north;
            i++;
        }
        return t;
    }
    public Tile getEast(int n) {
        Tile t = this.east;
        if (t == null) {
            return t;
        }
        
        int i = 1;
        while (t.east != null && i < n) {
            t = t.east;
            i++;
        }
        return t;
    }
    public Tile getSouth(int n) {
        Tile t = this.south;
        if (t == null) {
            return t;
        }
        
        int i = 1;
        while (t.south != null && i < n) {
            t = t.south;
            i++;
        }
        return t;
    }
    public Tile getWest(int n) {
        Tile t = this.west;
        if (t == null) {
            return t;
        }
        
        int i = 1;
        while (t.west != null && i < n) {
            t = t.west;
            i++;
        }
        return t;
    }
    
    public void setValue(int newValue) {
        value = newValue;
    }
    public void setNorth(Tile newNorth) {
        north = newNorth;
    }
    public void setEast(Tile newEast) {
        east = newEast;
    }
    public void setSouth(Tile newSouth) {
        south = newSouth;
    }
    public void setWest(Tile newWest) {
        west = newWest;
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
    
    public String toString() {
        return "(" + x + ", " + y + ") with value " + value;
    }
}
