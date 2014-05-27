package maze;

import java.awt.Graphics;
import java.awt.Polygon;

public class GoalFlag implements TileObject {
	private Player expectedPlayer;
	
	public GoalFlag(Player expectedPlayer) {
		this.expectedPlayer = expectedPlayer;
	}
	
	public boolean isExpectedPlayer(Player p) {
		return p == expectedPlayer;
	}

	@Override
	public void interact(Player player) {
		// TODO Auto-generated method stub
		System.out.println("Game finished!");
	}

	@Override
	public void draw(Graphics g, int tileSize) {
		g.setColor(expectedPlayer.getColor());
		Polygon flag = new Polygon();
		flag.addPoint(tileSize/3, tileSize/10);
		flag.addPoint(tileSize*3/4, tileSize/5);
		flag.addPoint(tileSize/3 + 1, tileSize*2/5);
		flag.addPoint(tileSize/3 + 1, tileSize*9/10);
		flag.addPoint(tileSize/3, tileSize*9/10);
		flag.addPoint(tileSize/3, tileSize/10);
		g.fillPolygon(flag);
	}
}