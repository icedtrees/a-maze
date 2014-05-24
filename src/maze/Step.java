package maze;

import maze.Maze.Direction;

public class Step {
	private Coord cell;
	private Direction dir;
	private int dist;
	
	public Step(Coord cell, Direction dir, int dist) {
		this.cell = cell;
		this.dir = dir;
		this.dist = dist;
	}
	
	public Coord getCell() {
		return cell;
	}
	public int getX() {
		return cell.getX();
	}
	public int getY() {
		return cell.getY();
	}
	public Direction getDir() {
		return dir;
	}
	public int getDist() {
		return dist;
	}
}
