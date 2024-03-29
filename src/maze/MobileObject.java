package maze;

import game.Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import maze.Direction;

/**
 * An abstract class which models a movable (mobile) character or item
 * which can be rendered onto the maze. This class provides methods for
 * animating and moving the character.
 */
public abstract class MobileObject {
//	private static final double EPSILON = 0.00000000001;
	
	private int realX;
	private int realY;
	private double curX;
	private double curY;
	private double speed; // Tiles traversed per second
	private Color color;
	
	private Direction curDirection;
	private Direction moving;
	private BufferedImage currentSprite;

	private int numAnimationFrames;
	private BufferedImage[][] sprites;
    private double currentFrame;
	
	public MobileObject(Color color, double speed, int x, int y,
			int numAnimationFrames, String baseSpritePath, Direction initial) {
		this.realX = x;
		this.realY = y;
		this.curX = x;
		this.curY = y;
		this.speed = speed;
		this.color = color;
		this.numAnimationFrames = numAnimationFrames;
		
		this.curDirection = Direction.SOUTH;
		this.moving = null;
		
		sprites = new BufferedImage[4][numAnimationFrames];
		
		try {
	        for (Direction dir : Direction.values()) {
	        	for (int i = 0; i < numAnimationFrames; i++) {
					sprites[dir.intVal()][i] = ImageIO.read(new File(baseSpritePath + dir + i + ".png"));
	        	}
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        this.currentSprite = sprites[initial.intVal()][0];
        this.currentFrame = 0;
	}
	
	/**
	 * Returns the direction that the object is currently moving in. This is
	 * safe since <code>Direction</code> is an enum, and hence immutable.
	 */
	public Direction getDirection() {
	    return moving;
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
	public int getGoalX() {
		if (moving != null) {
			return realX + moving.dx();
		} else {
			return realX;
		}
	}
	public int getGoalY() {
		if (moving != null) {
			return realY + moving.dy();
		} else {
			return realY;
		}
	}
	public boolean isMoving() {
		return moving != null;
	}
	public Color getColor() {
		return color;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public void setSpeedRelative(double speed) {
		this.speed += speed;
	}
	
	/**
	 * When called, renders the next frame of the <code>MobileObject</code> so
	 * that it appears to be animated. 
	 */
	public void nextFrame() {
	    // TODO not entirely sure of the specifics
		if (moving == null) {
			return;
		}
		
		double tilePerFrame = speed / Game.settings.FPS;
		curX += moving.dx() * tilePerFrame;
		curY += moving.dy() * tilePerFrame;
		
		// change the current sprite image for animation
		curDirection = moving;
		currentFrame += numAnimationFrames / (Game.settings.FPS / speed);
		if (currentFrame >= numAnimationFrames) {
			currentFrame = numAnimationFrames - 1;
		}
		currentSprite = sprites[curDirection.intVal()][(int) currentFrame];
		
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
			currentFrame = 0;
			
			synchronized(this) {
				notifyAll();
			}
		}
		return;   
	}
	
	/**
	 * Overridable method: each <code>MobileObject</code> is
	 * allowed to override this based on the current direction of
	 * direction
	 * @param g
	 * @param tileSize
	 */
	public void draw(Graphics g, int tileSize) {
	    int height = currentSprite.getHeight();
        int width = currentSprite.getWidth();
        
        g.drawImage(currentSprite, 0, 0, tileSize, tileSize, 0, 0, width, height, null);
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
	
	/**
	 * Moves the object by one tile in the given direction when called, but only
	 * if it is not currently moving to an adjacent tile.
	 * @param dir the cardinal direction in which to move the object towards
	 * @return false if either an invalid direction was given or the object is
	 * currently moving to a neighbouring tile, otherwise true
	 */
	public boolean move(Direction dir) {
		if (dir == null) {
			return false;
		}
		if (this.isMoving()) {
			return false;
		}
		
		moving = dir;
		return true;
	}
	
	/**
	 * Move in the specified direction - guaranteed. Calling thread will block
	 * until the move happens.
	 * @param dir Direction to move in.
	 */
	public void moveWait(Direction dir) {
		if (dir == null) {
			return;
		}
		
		synchronized(this) {
			while (this.isMoving()) {
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		moving = dir;
	}
}
