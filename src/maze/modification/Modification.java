package maze.modification;

import java.util.Random;

import maze.Maze;

public interface Modification {
	public void apply(Maze maze, Random rand);
}
