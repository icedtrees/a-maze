package maze.modification;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import maze.Coord;
import maze.Maze;

public class SpeedMod implements Modification {
	private int numBoots;
	
	public SpeedMod(int numBoots) {
		this.numBoots = numBoots;
	}

	@Override
	public void apply(Maze maze, Random rand) {
		List<Coord> spaces = maze.getSpaces();
		Collections.shuffle(spaces, rand);
		for (int i = 0; i < numBoots; i++) {
			if (spaces.size() <= 0) {
				break;
			}
			Coord c = spaces.remove(0);
			maze.setTileObject(c.getX(), c.getY(), new Boots(2));
		}
	}

}
