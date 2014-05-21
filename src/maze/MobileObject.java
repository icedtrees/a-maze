package maze;

import java.awt.Color;
import java.awt.Graphics;

import maze.Maze.Direction;

public abstract class MobileObject {
	private double x;
	private double y;
	private double speed; // Tiles traversed per second
	private double goalX;
	private double goalY;
	private Color color;
	
	private Direction moving;
	
	public MobileObject() {
		this(new Color(0, 0, 0));
	}
	public MobileObject(Color color) {
		this(color, 1);
	}
	public MobileObject(Color color, int speed) {
		this(color, speed, 1, 0);
	}
	public MobileObject(Color color, int speed, int x, int y) {
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.goalX = x;
		this.goalY = y;
		this.color = color;
		
		this.moving = null;
	}
	
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	
	public void nextFrame() {
		if (moving == null) {
			return;
		}
		boolean finishedMoving = true;
		if (moving == Direction.EAST && lessThan(x, goalX)) {
			finishedMoving = false;
		}
		if (moving == Direction.WEST && greaterThan(x, goalX)) {
			finishedMoving = false;
		}
		if (moving == Direction.SOUTH && lessThan(y, goalY)) {
			finishedMoving = false;
		}
		if (moving == Direction.NORTH && greaterThan(y, goalY)) {
			finishedMoving = false;
		}
		
		if (finishedMoving) {
			x = goalX;
			y = goalY;
			moving = null;
		} else {
			x += moving.dx() * (speed/Maze.FPS);
			y += moving.dy() * (speed/Maze.FPS);
		}
		return;
	}
	
	public void draw(Graphics g, int tileSize) {
		g.setColor(color);
		g.fillOval(0, 0, tileSize, tileSize);
	}
	
	private boolean equalTo(double a, double b) {
		return Math.abs(a - b) < 0.00000000001;
	}
	private boolean lessThan(double a, double b) {
		return (a < b) && (!equalTo(a, b));
	}
	private boolean greaterThan(double a, double b) {
		return (a > b) && (!equalTo(a, b));
	}
	
	public boolean move(Direction dir) {
		if (dir == null) {
			return true;
		}
		if (moving != null) {
			return false;
		}
		moving = dir;
		goalX = x + dir.dx();
		goalY = y + dir.dy();
		return true;
	}
}
