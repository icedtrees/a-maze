package maze;

import maze.Direction;

/**
 * Coordinate class with integer x and y
 * Immutable
 * @author Leo
 *
 */
public class Coord {
	private int x;
	private int y;
	
	public Coord(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * 
	 * @return x coordinate of coordinate
	 */
	public int getX() {
		return x;
	}
	/**
	 * 
	 * @return y coordinate of coordinate
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Returns a new coordinate one step in the given direction
	 * @param dir Direction to create new coordinate in
	 * @return New coordinate in given direction
	 */
	public Coord inDirection(Direction dir) {
		return inDirection(dir, 1);
	}
	/**
	 * Returns a new coordinate n steps in the given direction
	 * @param dir Direction to create new coordinate in
	 * @param n Number of units in the given direction
	 * @return New coordinate in given direction
	 */
	public Coord inDirection(Direction dir, int n) {
		return new Coord(x + (n * dir.dx()), y + (n * dir.dy()));
	}
	
	public String toString() {
		return x + ", " + y;
	}
	public boolean equals(Object o) {
		if (o == null || !(o instanceof Coord)) {
			return false;
		}
		
		Coord c = (Coord) o;
		if (this.x == c.x && this.y == c.y) {
			return true;
		} else {
			return false;
		}
	}
	public int hashCode() {
		return x + 37*y;
	}
}
