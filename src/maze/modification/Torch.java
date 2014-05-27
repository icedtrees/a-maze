package maze.modification;

import java.awt.Color;
import java.awt.Graphics;

import maze.MazeOptions;
import maze.Player;
import maze.TileObject;

public class Torch implements TileObject {
	
	public Torch() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean interact(Player player, MazeOptions stats) {
		Player friend = player.getFriend();
		if (friend != null) {
			friend.setVision(friend.getVision() + 5);
			System.out.println("Player got torch for friend!");
		} else {
			player.setVision(player.getVision() + 5);
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
