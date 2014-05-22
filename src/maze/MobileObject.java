package maze;

import java.awt.Color;
import java.awt.Graphics;

import maze.Maze.Direction;

public abstract class MobileObject {
//	private static final double EPSILON = 0.00000000001;
	
	private int realX;
	private int realY;
	private double curX;
	private double curY;
	private double speed; // Tiles traversed per second
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
		this.realX = x;
		this.realY = y;
		this.curX = x;
		this.curY = y;
		this.speed = speed;
		this.color = color;
		
		this.moving = null;
	}
	
	public int getRealX() {
		return realX;
	}
	public int getRealY() {
		return realY;
	}
	public double getCurX() {
		return curX;
	}
	public double getCurY() {
		return curY;
	}
	public boolean isMoving() {
		return moving != null;
	}
	
	public void nextFrame() {
		if (moving == null) {
			return;
		}
		
		curX += moving.dx() * (speed/Maze.FPS);
		curY += moving.dy() * (speed/Maze.FPS);
		
		boolean finishedMoving = true;
		if (moving == Direction.EAST && curX < realX + 1) {
			finishedMoving = false;
		}
		if (moving == Direction.WEST && curX > realX - 1) {
			finishedMoving = false;
		}
		if (moving == Direction.SOUTH && curY < realY + 1) {
			finishedMoving = false;
		}
		if (moving == Direction.NORTH && curY > realY - 1) {
			finishedMoving = false;
		}
		
		if (finishedMoving) {
			realX += moving.dx();
			realY += moving.dy();
			curX = realX;
			curY = realY;
			moving = null;
		}
		return;
	}
	
	public void draw(Graphics g, int tileSize) {
		g.setColor(color);
		g.fillOval(0, 0, tileSize, tileSize);
	}
	
//	private boolean equalTo(double a, double b) {
//		return Math.abs(a - b) < EPSILON;
//	}
//	private boolean lessThan(double a, double b) {
//		return (a < b) && (!equalTo(a, b));
//	}
//	private boolean greaterThan(double a, double b) {
//		return (a > b) && (!equalTo(a, b));
//	}
	
	public boolean move(Direction dir) {
		if (dir == null) {
			return true;
		}
		if (this.isMoving()) {
			return false;
		}
		moving = dir;
		return true;
	}
}
