package maze;

public enum Direction {
	NORTH(0, -1, 0),
	EAST(1, 0, 1),
	SOUTH(0, 1, 2),
	WEST(-1, 0, 3);
    
    private final int dx;
    private final int dy;
    private Direction reverse;
    private int intval;
    
    static {
    	NORTH.reverse = SOUTH;
    	EAST.reverse = WEST;
    	SOUTH.reverse = NORTH;
    	WEST.reverse = EAST;
    }
    
    private Direction(int dx, int dy, int intval) {
    	this.dx = dx;
    	this.dy = dy;
    	this.intval = intval;
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
    public int intVal() {
    	return intval;
    }
}
