package maze.modification;

import java.awt.Color;
import java.awt.Graphics;

import maze.Player;
import maze.TileObject;

public class Torch implements TileObject {
	private int visionBonus;
	
	public Torch(int visionBonus) {
		this.visionBonus = visionBonus;
	}
	
	@Override
	public boolean interact(Player player) {
		Player friend = player.getFriend();
		if (friend != null) {
			friend.setVisionRelative(visionBonus);
			System.out.println("Player got torch for friend!");
		} else {
			player.setVisionRelative(visionBonus);
			System.out.println("Player got torch!");
		}
		
		return true;
	}

	@Override
	public void draw(Graphics g, int tileSize) {
		g.setColor(Color.ORANGE);
		g.fillOval(tileSize / 4, 0, tileSize / 2, tileSize);
	}
}
