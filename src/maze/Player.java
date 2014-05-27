package maze;

import java.awt.Color;

public class Player extends MobileObject {
	private int vision;
	
	public Player(int startX, int startY, Color color) {
		super(color, 5, startX, startY);
	}
	
	public int getVision() {
		return vision;
	}
	public void setVision(int vision) {
		this.vision = vision;
	}
}
