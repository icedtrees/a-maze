package maze;

import java.util.Random;

public class FogOfWar implements Modification {
	private int defaultVision;
	private int numTorches;
	
	public FogOfWar(int defaultVision, int numTorches) {
		this.defaultVision = defaultVision;
		this.numTorches = numTorches;
	}

	@Override
	public void apply(Maze maze, Random rand) {
		maze.setFogOfWar(true, defaultVision);
		
		for (int i = 0; i < numTorches; i++) {
			int x;
			int y;
			boolean validSpot = false;
			while (!validSpot) {
				x = rand.nextInt(maze.getMazeWidth());
				y = rand.nextInt(maze.getMazeHeight());
				if (maze.isSpace(x, y) && !maze.hasTileObject(x, y)) {
					validSpot = true;
					maze.setTileObject(x, y, new Torch());
				}
			}
		}
	}

}