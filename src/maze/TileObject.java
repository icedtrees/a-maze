package maze;

import java.awt.Graphics;

public interface TileObject {
	
	public boolean interact(Player player, MazeSettings stats);
	
	public void draw(Graphics g, int tileSize);

}
