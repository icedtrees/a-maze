package maze.modification;

import java.util.Random;

import maze.Maze;

public class SpeedMod implements Modification {
	private int numBoots;
	
	public SpeedMod(int numBoots) {
		this.numBoots = numBoots;
	}

	@Override
	public void apply(Maze maze, Random rand) {
		for (int i = 0; i < numBoots; i++) {
			int x;
			int y;
			boolean validSpot = false;
			while (!validSpot) {
				x = rand.nextInt(maze.getMazeWidth());
				y = rand.nextInt(maze.getMazeHeight());
				if (maze.isSpace(x, y) && !maze.hasTileObject(x, y)) {
					validSpot = true;
					maze.setTileObject(x, y, new Boots(2));
				}
			}
		}
	}

}
