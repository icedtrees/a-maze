package maze;

import java.awt.Color;
import java.awt.Graphics;

public class Treasure implements TileObject {
	private int value;
	
	public Treasure(int value) {
		this.value = value;
	}

	@Override
	public void interact(Player player) {
		System.out.println("Player got treasure worth " + value);
	}

	@Override
	public void draw(Graphics g, int tileSize) {
		g.setColor(Color.YELLOW);
		g.fillOval(tileSize / 4, tileSize / 4, tileSize / 2, tileSize / 2);
		
	}

}
