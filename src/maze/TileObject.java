package maze;

import java.awt.Graphics;

public interface TileObject {
	
	public boolean interact(Player player, MazeStats stats);
	
	public void draw(Graphics g, int tileSize);

}
