package maze;

import maze.Maze.Direction;

public class Coord {
	private int x;
	private int y;
	
	public Coord(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
	public Coord inDirection(Direction dir) {
		return new Coord(x + dir.dx(), y + dir.dy());
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
