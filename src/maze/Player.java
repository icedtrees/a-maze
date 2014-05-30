package maze;

import java.awt.Color;

public class Player extends MobileObject {
	private int vision;
	private Player friend;
	private boolean leavesTrail;
	private double timer;
	private boolean finished;
	private int hintsLeft;
	
	public Player(int startX, int startY, Color color, boolean trail, double timer, int hintsLeft) {
		super(color, 5, startX, startY, 3, "img/playerSprite/brownCat");
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
	public void setFriend(Player friend) {
		this.friend = friend;
	}
	public Player getFriend() {
		return friend;
	}
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
		this.timer = timer;
	}
	public void setTimerRelative(double timer) {
		this.timer += timer;
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
