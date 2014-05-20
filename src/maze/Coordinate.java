package maze;

import java.util.ArrayList;
import java.util.List;

public class Coordinate {
	private int x;
	private int y;
	
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
	public List<Coordinate> adj() {
		List<Coordinate> l = new ArrayList<Coordinate>();
		l.add(new Coordinate(x+1, y));
		l.add(new Coordinate(x, y+1));
		l.add(new Coordinate(x-1, y));
		l.add(new Coordinate(x, y-1));
		
		return l;
	}
	
	public String toString() {
		return x + ", " + y;
	}
	
	public boolean equals(Object o) {
		if (o == null || !(o instanceof Coordinate)) {
			return false;
		}
		
		Coordinate c = (Coordinate) o;
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
