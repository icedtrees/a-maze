package maze.modification;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import maze.Coord;
import maze.Maze;

/**
 * Activates fog of war within the maze
 * @author Leo
 *
 */
public class FogMod implements Modification {
	private int defaultVision;
	private int numTorches;
	
	public FogMod(int defaultVision, int numTorches) {
		this.defaultVision = defaultVision;
		this.numTorches = numTorches;
	}

	@Override
	public void apply(Maze maze, Random rand) {
		maze.setFogOfWar(true, defaultVision);
		
		List<Coord> spaces = maze.getSpaces();
		Collections.shuffle(spaces, rand);
		for (int i = 0; i < numTorches; i++) {
			if (spaces.size() <= 0) {
				break;
			}
			Coord c = spaces.remove(0);
			maze.setTileObject(c.getX(), c.getY(), new Torch(3));
		}
	}

}
