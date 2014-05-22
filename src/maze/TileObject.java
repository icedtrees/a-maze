package maze;

import java.awt.Graphics;

public interface TileObject {
	
	public void interact(Player player);
	
	public void draw(Graphics g, int tileSize);

}
