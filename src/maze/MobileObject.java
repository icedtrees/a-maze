package maze;

import java.awt.Color;
import java.awt.Graphics;

import maze.Maze.Direction;

public abstract class MobileObject {
	private double x;
	private double y;
	private double speed;
	private double goalX;
	private double goalY;
	private Color color;
	
	private static int FPS = 50;
	
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
	}
	
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	
	public void nextFrame() {
		if (lessThan(x, goalX)) {
			x += speed/FPS;
		}
		if (greaterThan(x, goalX)) {
			x -= speed/FPS;
		}
		if (lessThan(y, goalY)) {
			y += speed/FPS;
		}
		if (greaterThan(y, goalY)) {
			y -= speed/FPS;
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
		if (!equalTo(x, goalX) || !equalTo(y, goalY)) {
			return false;
		}
		if (dir == Direction.EAST) {
			goalX = x + 1;
		} else if (dir == Direction.SOUTH){
			goalY = y + 1;
		} else if (dir == Direction.WEST) {
			goalX = x - 1;
		} else if (dir == Direction.NORTH){
			goalY = y - 1;
		}
		return true;
	}
}
