package maze;

import game.Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

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
	private BufferedImage sprite;

	private BufferedImage spriteEast;
	private BufferedImage spriteNorth;
	private BufferedImage spriteWest;
	private BufferedImage spriteSouth;
	
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
		try {
            this.sprite = ImageIO.read(new File("img/sprite-test-south.png"));
            this.spriteEast = ImageIO.read(new File("img/sprite-test-east.png"));
            this.spriteNorth = ImageIO.read(new File("img/sprite-test-north.png"));
            this.spriteWest = ImageIO.read(new File("img/sprite-test-west.png"));
            this.spriteSouth = this.sprite;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
	
	public void nextFrame() {
		if (moving == null) {
			return;
		}
		
		double tilePerFrame = speed / Game.settings.FPS;
		curX += moving.dx() * tilePerFrame;
		curY += moving.dy() * tilePerFrame;
		
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
//		g.setColor(color);
//		g.fillOval(0, 0, tileSize, tileSize);
	    g.drawImage(sprite, 10, 10, null);
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
			return false;
		}
		if (this.isMoving()) {
			return false;
		}
		
		moving = dir;
		if (dir == Direction.EAST) {
		    color = Color.BLUE;
		    sprite = spriteEast;
		} else if (dir == Direction.NORTH) {
		    color = Color.GREEN;
		    sprite = spriteNorth;
		} else if (dir == Direction.SOUTH) {
		    color = Color.PINK;
		    sprite = spriteSouth;
		} else {
		    color = Color.GRAY;
		    sprite = spriteWest;
		}
		return true;
	}
	
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
