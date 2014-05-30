package maze.modification;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import maze.*;

public class Clock implements TileObject {
	private static BufferedImage image;
	private int value;
	
	static {
		try {
			image = ImageIO.read(new File("img/playerSprite/clock.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Clock(int value) {
		this.value = value;
	}

	@Override
	public boolean interact(Player player) {
		Player friend = player.getFriend();
		if (friend != null) {
			player.setTimerRelative(value);
			System.out.println("Player got treasure for friend worth " + value);
		} else {
			player.setTimerRelative(value);
			System.out.println("Player got treasure worth " + value);
		}
		
		return true;
	}

	@Override
	public void draw(Graphics g, int tileSize) {
		g.drawImage(image, 0, 0, tileSize, tileSize,
				0, 0, image.getWidth(), image.getHeight(), null);
	}

}
