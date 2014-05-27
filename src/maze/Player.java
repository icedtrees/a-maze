package maze;

import java.awt.Color;

public class Player extends MobileObject {
	private int vision;
	private Player friend;
	
	public Player(int startX, int startY, Color color) {
		super(color, 5, startX, startY);
	}
	
	public int getVision() {
		return vision;
	}
	public void setVision(int vision) {
		this.vision = vision;
	}
	public void setFriend(Player friend) {
		this.friend = friend;
	}
	public Player getFriend() {
		return friend;
	}
}
