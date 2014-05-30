package maze;

import java.awt.Color;

import maze.Maze.Direction;


/**
 * A subclass of <code>MobileObject</code>, this class provides a controllable
 * character which a user can give commands to e.g. via keypresses which trigger
 * the classe's methods to be called.
 *
 */
public class Player extends MobileObject {
	private int vision;
	private Player friend;
	private boolean leavesTrail;
	private double timer;
	private boolean finished;
	private int hintsLeft;
	
	public Player(int startX, int startY, Color color, boolean trail, double timer, int hintsLeft, String basePath, Direction initial) {
		super(color, 5, startX, startY, 3, basePath, initial);
		this.leavesTrail = trail;
		this.timer = timer;
		this.hintsLeft = hintsLeft;
		finished = false;
	}
	
	public int getVision() {
		return vision;
	}
	public void setVision(int vision) {
		this.vision = vision;
	}
	public void setVisionRelative(int vision) {
		this.vision += vision;
	}
	
	/**
	 * Modifies a reference to another <code>Player</code> object which exists
	 * concurrently with this player.
	 * @param friend
	 */
	public void setFriend(Player friend) {
		this.friend = friend;
	}
	
	public Player getFriend() {
		return friend;
	}
	
	/**
	 * Returns true if this player has a reference to another distinct player
	 * (i.e. true if there are two or more players in play).
	 */
	public boolean hasFriend() {
		return friend != null;
	}
	public boolean leavesTrail() {
		return leavesTrail;
	}
	public double getTimer() {
		return timer;
	}
	public void setTimer(double timer) {
		if (!finished) {
			this.timer = timer;
		}
	}
	public void setTimerRelative(double timer) {
		if (!finished) {
			this.timer += timer;
		}
	}
	public int getHints() {
		return hintsLeft;
	}
	public void setHints(int hints) {
		this.hintsLeft = hints;
	}
	public void setHintsRelative(int hints) {
		this.hintsLeft += hints;
	}
	public boolean isFinished() {
		return finished;
	}
	public void setFinished(boolean finished) {
		this.finished = finished;
	}
}
