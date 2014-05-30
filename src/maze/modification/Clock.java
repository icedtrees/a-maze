package maze.modification;

import java.awt.*;

import maze.*;

public class Clock implements TileObject {
	private int value;
	
	public Clock(int value) {
		this.value = value;
	}

	@Override
	public boolean interact(Player player) {
		Player friend = player.getFriend();
		if (friend != null) {
			player.setTimerRelative(value);
			System.out.println("Player got treasure for friend worth " + value);
		} else {
			player.setTimerRelative(value);
			System.out.println("Player got treasure worth " + value);
		}
		
		return true;
	}

	@Override
	public void draw(Graphics g, int tileSize) {
		g.setColor(Color.YELLOW);
		g.fillOval(tileSize / 4, tileSize / 4, tileSize / 2, tileSize / 2);
		
	}

}
