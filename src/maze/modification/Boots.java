package maze.modification;

import java.awt.Color;
import java.awt.Graphics;

import maze.MazeStats;
import maze.Player;
import maze.TileObject;

public class Boots implements TileObject {
	double speedBonus;
	public Boots(double speedBonus) {
		this.speedBonus = speedBonus;
	}
	
	public double getSpeedBonus() {
		return speedBonus;
	}

	@Override
	public boolean interact(Player player, MazeStats stats) {
		Player friend = player.getFriend();
		if (friend != null) {
			friend.setSpeedRelative(speedBonus);
			System.out.println("Player got boots for friend worth " + speedBonus);
		} else {
			player.setSpeedRelative(speedBonus);
			System.out.println("Player got boots worth " + speedBonus);
		}
		
		return true;
	}

	@Override
	public void draw(Graphics g, int tileSize) {
		g.setColor(new Color(165, 42, 42));
		g.fillRect(tileSize/4, tileSize/4, tileSize/4, tileSize/4);
		g.fillRect(tileSize/4, tileSize/2, tileSize/2, tileSize/4);
	}
}
