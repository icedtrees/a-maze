package maze;

import java.awt.Color;

public class Player extends MobileObject {
	private int playerNum;
	private int vision;
	private Player friend;
	
	public Player(int playerNum, int startX, int startY, Color color) {
		super(color, 5, startX, startY);
		this.playerNum = playerNum;
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
	public int getPlayerNum() {
		return playerNum;
	}
}
