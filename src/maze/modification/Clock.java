package maze.modification;

import java.awt.*;

import maze.*;

public class Clock implements TileObject {
	private int value;
	
	public Clock(int value) {
		this.value = value;
	}

	@Override
	public boolean interact(Player player, MazeSettings stats) {
		Player friend = player.getFriend();
		if (friend != null) {
			stats.setTimerRelative(friend.getPlayerNum(), value);
			System.out.println("Player got treasure for friend worth " + value);
		} else {
			stats.setTimerRelative(player.getPlayerNum(), value);
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
