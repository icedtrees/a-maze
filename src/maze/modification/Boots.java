package maze.modification;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import maze.Player;
import maze.TileObject;

public class Boots implements TileObject {
	double speedBonus;
	private static BufferedImage sprite;
	
	static {
		try {
			sprite = ImageIO.read(new File("img/playerSprite/boots2.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Boots(double speedBonus) {
		this.speedBonus = speedBonus;
	}
	
	public double getSpeedBonus() {
		return speedBonus;
	}

	@Override
	public boolean interact(Player player) {
		Player friend = player.getFriend();
		if (friend != null) {
			friend.setSpeedRelative(speedBonus);
			System.out.println("Player got boots for friend worth " + speedBonus);
		} else {
			player.setSpeedRelative(speedBonus);
			System.out.println("Player got boots worth " + speedBonus);
		}
		
		return true;
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
