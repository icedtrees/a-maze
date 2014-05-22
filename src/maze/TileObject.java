package maze;

import java.awt.Graphics;

public interface TileObject {
	
	public void interact(PlayerObject player);
	
	public void draw(Graphics g, int tileSize);

}
