package maze;

import java.util.ArrayList;
import java.util.List;

import maze.Maze.Direction;

public class Coordinate {
	private int x;
	private int y;
	private Direction directionToGetHere;
	
	public Coordinate(int x, int y, Direction dir) {
		this.x = x;
		this.y = y;
		this.directionToGetHere = dir;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public Direction getDir() {
		return directionToGetHere;
	}
	
	public List<Coordinate> adj() {
		List<Coordinate> l = new ArrayList<Coordinate>();
		l.add(new Coordinate(x+1, y, Maze.Direction.EAST));
		l.add(new Coordinate(x, y+1, Maze.Direction.SOUTH));
		l.add(new Coordinate(x-1, y, Maze.Direction.WEST));
		l.add(new Coordinate(x, y-1, Maze.Direction.NORTH));
		
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
