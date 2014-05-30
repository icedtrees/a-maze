package maze;

import java.awt.Graphics;

public interface TileObject {
	
	public boolean interact(Player player);
	
	public void draw(Graphics g, int tileSize);
	public void nextFrame();

}
