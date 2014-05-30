package maze;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Goal for the players to walk towards
 * @author Leo
 *
 */
public class GoalFish implements TileObject {
	private Player expectedPlayer;
	private BufferedImage sprite;
	
	public GoalFish(Player expectedPlayer, String fishType) {
		this.expectedPlayer = expectedPlayer;
		try {
			sprite = ImageIO.read(new File("img/fish-" + fishType + ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean isExpectedPlayer(Player p) {
		return p == expectedPlayer;
	}

	@Override
	public boolean interact(Player player) {
		// TODO Auto-generated method stub
		if (player == expectedPlayer) {
			player.setFinished(true);
			System.out.println("Game finished!");
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void draw(Graphics g, int tileSize) {
		g.drawImage(sprite, 0, 0, tileSize, tileSize,
				0, 0, sprite.getWidth(), sprite.getHeight(), null);
	}

	@Override
	public void nextFrame() {
		// TODO Auto-generated method stub
		
	}
}
