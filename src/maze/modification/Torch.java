package maze.modification;

import java.awt.Color;
import java.awt.Graphics;

import maze.Player;
import maze.TileObject;

public class Torch implements TileObject {
	
	public Torch() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void interact(Player player) {
		System.out.println("Player got torch!");
		player.setVision(player.getVision() + 5);
	}

	@Override
	public void draw(Graphics g, int tileSize) {
		g.setColor(Color.ORANGE);
		g.fillOval(tileSize / 4, 0, tileSize / 2, tileSize);
		
	}
}
