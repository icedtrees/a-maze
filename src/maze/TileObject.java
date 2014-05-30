package maze;

import java.awt.Graphics;

public interface TileObject {
	
	/**
	 * Interacts with the player
	 * @param player Player to interact with
	 * @return True if this object should be removed after interaction, false
	 * otherwise.
	 */
	public boolean interact(Player player);
	
	/**
	 * Draw this object
	 * @param g Graphics context
	 * @param tileSize Tile size - also size of graphics context - in pixels
	 */
	public void draw(Graphics g, int tileSize);
	
	/**
	 * Advance this tile object to the next frame in its animation
	 */
	public void nextFrame();

}
