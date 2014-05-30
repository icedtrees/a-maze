package maze;

import maze.Maze.Direction;

public class Step {
	private Coord cell;
	private Direction dir;
	private int dist;
	private int weighting;
	private Step from;
	
	public Step(Coord cell, Direction dir, int dist) {
		this(cell, dir, dist, 1);
	}
	public Step(Coord cell, Direction dir, int dist, int weighting) {
		this.cell = cell;
		this.dir = dir;
		this.dist = dist;
		this.weighting = weighting;
		this.from = null;
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
	public int getWeighting() {
		return weighting;
	}
	public void setWeighting(int weighting) {
		if (weighting < 1) {
			weighting = 1;
		}
		if (weighting > 100) {
			weighting = 100;
		}
		this.weighting = weighting;
	}
	public Step getFrom() {
		return from;
	}
	public void setFrom(Step from) {
		this.from = from;
	}
}
