package maze.modification;

import game.Game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import maze.Player;
import maze.TileObject;

public class Torch implements TileObject {
	private int visionBonus;
	private static int NUM_FRAMES = 4;
	private static int LOOPS_PER_SECOND = 2;
	private double currentFrame;
	private static BufferedImage image[] = new BufferedImage[NUM_FRAMES];
	
	static {
		try {
			for (int i = 0; i < NUM_FRAMES; i++) {
				image[i] = ImageIO.read(new File("img/playerSprite/torch" + i + ".png"));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Torch(int visionBonus) {
		this.visionBonus = visionBonus;
		currentFrame = 0;
	}
	
	@Override
	public boolean interact(Player player) {
		Player friend = player.getFriend();
		if (friend != null) {
			friend.setVisionRelative(visionBonus);
			System.out.println("Player got torch for friend!");
		} else {
			player.setVisionRelative(visionBonus);
			System.out.println("Player got torch!");
		}
		
		return true;
	}

	@Override
	public void draw(Graphics g, int tileSize) {
		BufferedImage curSprite = image[(int) currentFrame];
		g.drawImage(curSprite, 0, 0, tileSize, tileSize,
				0, 0, curSprite.getWidth(), curSprite.getHeight(), null);
	}

	@Override
	public void nextFrame() {
		currentFrame += (double) LOOPS_PER_SECOND * NUM_FRAMES / Game.settings.FPS;
		if (currentFrame >= NUM_FRAMES) {
			currentFrame = 0;
		}
	}
}
