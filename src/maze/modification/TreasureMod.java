package maze.modification;

import java.util.Random;

import maze.Maze;

public class TreasureMod implements Modification {
	private int numTreasure;
	
	public TreasureMod(int numTreasure) {
		this.numTreasure = numTreasure;
	}

	@Override
	public void apply(Maze maze, Random rand) {
		for (int i = 0; i < numTreasure; i++) {
			int x;
			int y;
			boolean validSpot = false;
			while (!validSpot) {
				x = rand.nextInt(maze.getMazeWidth());
				y = rand.nextInt(maze.getMazeHeight());
				if (maze.isSpace(x, y) && !maze.hasTileObject(x, y)) {
					validSpot = true;
					maze.setTileObject(x, y, new Treasure(100));
				}
			}
		}
	}

}
